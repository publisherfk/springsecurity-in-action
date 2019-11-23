package com.supcon.greendill.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * BAP-Cloud登录到oauth2认证中心
 *
 * @author fukun
 */
@Component
public class BapCloudFilter implements Filter {

    private static String hostName;
    Logger logger = LoggerFactory.getLogger(BapCloudFilter.class);
    private String bapCloudUrl;
    private String clientId;
    private String clientSecurt;
    private Boolean isBapCloud = true;
    private String redirectUrl = "/oauth/callback";
    private CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (isBapCloud) {
            if (null != request.getSession().getAttribute("accessToken")) {
                String accessToken = request.getSession().getAttribute("accessToken").toString();
                String userName = getUserInfo(accessToken);
                if (!StringUtils.isEmpty(userName)) {
                    request.getSession().setAttribute("bapCloudUserName", userName);
                    chain.doFilter(request, response);
                    return;
                }
            }

            String redirect_uri = URLEncoder.encode(bapCloudUrl + redirectUrl, "utf-8");
            String code = request.getParameter("code");
            if (null != code) {
                String accessTokenUrl = bapCloudUrl + "/oauth/token?grant_type=authorization_code&client_id=" + clientId + "&code=" + code + "&redirect_uri=" + redirect_uri;
                HttpPost accessTokenPostRequest = new HttpPost(accessTokenUrl);
                accessTokenPostRequest.addHeader("Authorization", getHeader());
                HttpResponse accessTokenResponse = client.execute(accessTokenPostRequest);
                HttpEntity entity = (HttpEntity) accessTokenResponse.getEntity();
                Map<String, String> tokenResult = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"), new TypeReference<HashMap<String, String>>() {
                });
                logger.info("tokenResult:{}", tokenResult);
                String accessToken = tokenResult.get("access_token");
                request.getSession().setAttribute("accessToken", accessToken);
                String userName = getUserInfo(accessToken);
                request.getSession().setAttribute("bapCloudUserName", userName);
                chain.doFilter(request, response);
            } else {
                StringBuffer redirectUri = request.getRequestURL();
                if (!StringUtils.isEmpty(request.getQueryString())) {
                    redirectUri.append("?").append(request.getQueryString());
                }
                String encodedRedirectUri = URLEncoder.encode(redirectUri.toString(), "utf-8");
                response.sendRedirect(bapCloudUrl + "/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirect_uri + "&state=" + encodedRedirectUri + "&scope=all");
            }
        } else {
            request.getSession().removeAttribute("bapCloudUserName");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
//        Map casPropertiesMap = (Map) wac.getBean("casPropertiesMap");
//        bapCloudUrl = casPropertiesMap.get("bapCloudUrl") == null ? null : casPropertiesMap.get("bapCloudUrl").toString();
//        clientId = casPropertiesMap.get("clientId") == null ? null : casPropertiesMap.get("clientId").toString();
//        clientSecurt = casPropertiesMap.get("clientSecurt") == null ? null : casPropertiesMap.get("clientSecurt").toString();
//        isBapCloud = casPropertiesMap.get("isBapCloud") == null ? true : Boolean.parseBoolean("isBapCloud");
        bapCloudUrl = "http://192.168.91.43:8082/auth";
        clientId = "cas-server";
        clientSecurt = "123456";
        isBapCloud = true;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    private String getUserInfo(String accessToken) throws ClientProtocolException, IOException {
        HttpGet getUserInfo = new HttpGet(bapCloudUrl + "/oauth/getUserName?accessToken=" + accessToken);
        HttpResponse userInfoResponse = client.execute(getUserInfo);
        HttpEntity entity = (HttpEntity) userInfoResponse.getEntity();
        logger.info("getUserInfo entity:{}", entity);
        logger.info("accessToken:{}", accessToken);
        Map<String, String> userResult = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"), new TypeReference<HashMap<String, String>>() {});
        String userName = null;
        if (null != userResult) {
            userName = userResult.get("user_name");
        }
        return userName;
    }

    /**
     * 构造BasicAuth认证头信息
     *
     * @return
     */
    private String getHeader() {
        String auth = clientId + ":" + clientSecurt;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

}

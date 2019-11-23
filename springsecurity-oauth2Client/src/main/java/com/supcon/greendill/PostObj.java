package com.supcon.greendill;

import io.micrometer.core.instrument.util.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;

/**
 * @author by fukun
 */
public class PostObj {
    private CloseableHttpClient client = HttpClients.createDefault();
    private String bapCloudUrl = "http://192.168.91.43:8082/auth";
//    public PostObj() {
//        HttpHost proxy = new HttpHost("192.168.91.43", 8082, "http");
//        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//        this.client = HttpClients.custom().setRoutePlanner(routePlanner).build();
//
//    }

    public static void main(String[] args) throws Exception {
        String flag = "true";
        System.out.println(Boolean.parseBoolean(flag));
    }

    public int post(String path, String info) throws Exception {
        HttpPost post = new HttpPost(bapCloudUrl + path);
        post.setEntity(new StringEntity(info, ContentType.create("application/json", "utf-8")));
        CloseableHttpResponse res = this.client.execute(post);
        InputStream inputStream = res.getEntity().getContent();

        System.out.println(IOUtils.toString(inputStream));
        return res.getStatusLine().getStatusCode();
    }
}

//package com.heshi.oauth2.server.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * @author by fukun
// */
//@Entity
//@Table(name = OauthClientDetails.TABLE_NAME)
//public class OauthClientDetails {
//    public static final String TABLE_NAME = "OAUTH_CLIENT_DETAILS";
//    @Id
//    @Column(name = "CLIENT_ID")
//    private String clientId;
//    @Column(name = "RESOURCE_IDS")
//    private String resourceIds;
//    @Column(name = "CLIENT_SECRET")
//    private String clientSecret;
//    @Column(name = "SCOPE")
//    private String scope;
//    @Column(name = "AUTHORIZED_GRANT_TYPES")
//    private String grantTypes;
//    @Column(name = "WEB_SERVER_REDIRECT_URI")
//    private String redirectUri;
//    @Column(name = "AUTHORITIES")
//    private String authorities;
//    @Column(name = "ACCESS_TOKEN_VALIDITY")
//    private Long accessTokenValidity;
//    @Column(name = "REFRESH_TOKEN_VALIDITY")
//    private Long refreshTokenValidity;
//    @Column(name = "ADDITIONAL_INFORMATION", length = 4000)
//    private String additional;
//    @Column(name = "AUTOAPPROVE")
//    private String autoApprove;
//
//    public static String getTableName() {
//        return TABLE_NAME;
//    }
//
//    public String getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
//    }
//
//    public String getResourceIds() {
//        return resourceIds;
//    }
//
//    public void setResourceIds(String resourceIds) {
//        this.resourceIds = resourceIds;
//    }
//
//    public String getClientSecret() {
//        return clientSecret;
//    }
//
//    public void setClientSecret(String clientSecret) {
//        this.clientSecret = clientSecret;
//    }
//
//    public String getScope() {
//        return scope;
//    }
//
//    public void setScope(String scope) {
//        this.scope = scope;
//    }
//
//    public String getGrantTypes() {
//        return grantTypes;
//    }
//
//    public void setGrantTypes(String grantTypes) {
//        this.grantTypes = grantTypes;
//    }
//
//    public String getRedirectUri() {
//        return redirectUri;
//    }
//
//    public void setRedirectUri(String redirectUri) {
//        this.redirectUri = redirectUri;
//    }
//
//    public String getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(String authorities) {
//        this.authorities = authorities;
//    }
//
//    public Long getAccessTokenValidity() {
//        return accessTokenValidity;
//    }
//
//    public void setAccessTokenValidity(Long accessTokenValidity) {
//        this.accessTokenValidity = accessTokenValidity;
//    }
//
//    public Long getRefreshTokenValidity() {
//        return refreshTokenValidity;
//    }
//
//    public void setRefreshTokenValidity(Long refreshTokenValidity) {
//        this.refreshTokenValidity = refreshTokenValidity;
//    }
//
//    public String getAdditional() {
//        return additional;
//    }
//
//    public void setAdditional(String additional) {
//        this.additional = additional;
//    }
//
//    public String getAutoApprove() {
//        return autoApprove;
//    }
//
//    public void setAutoApprove(String autoApprove) {
//        this.autoApprove = autoApprove;
//    }
//}

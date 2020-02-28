/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD.
 * All rights reserved.
 */
package com.heshi.oauth2.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * MD5加密 + base64加密
 *
 * @author fukun
 *
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);
    private static String algorithm = "MD5";
    private static int iterations = 1;
    private static boolean encodeHashAsBase64 = true;

    public String encodePassword(String rawPass, Object salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest;

        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }

        for (int i = 1; i < iterations; i++) {
            digest = messageDigest.digest(digest);
        }

        if (getEncodeHashAsBase64()) {
            return new String(Base64.encode(digest));
        } else {
            return new String(Hex.encode(digest));
        }
    }

    protected static String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }

        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    protected final static MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }

    public static boolean getEncodeHashAsBase64() {
        return encodeHashAsBase64;
    }

    private boolean equalsNoEarlyReturn(String a, String b) {
        char[] caa = a.toCharArray();
        char[] cab = b.toCharArray();

        if (caa.length != cab.length) {
            return false;
        }

        byte ret = 0;
        for (int i = 0; i < caa.length; i++) {
            ret |= caa[i] ^ cab[i];
        }
        return ret == 0;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encodePassword(rawPassword.toString(), null);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Empty encoded password");
            return false;
        }
        return equalsNoEarlyReturn(encode(rawPassword), encodedPassword);
    }

    public static void main(String args[]) {
        System.out.println(new CustomPasswordEncoder().encode("123456"));
        System.out.println(new CustomPasswordEncoder().encode("k"));
    }
}

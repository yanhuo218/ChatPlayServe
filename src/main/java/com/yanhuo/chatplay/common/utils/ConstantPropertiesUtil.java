package com.yanhuo.chatplay.common.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
public class ConstantPropertiesUtil implements InitializingBean {
    public static String APPID;
    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String REGION_NAME;
    public static String BUCKET_NAME;
    public static String DEFAULT_URL;

    @Value("${Tencent.appid}")
    private String appid;
    @Value("${Tencent.accessKey}")
    private String accessKey;
    @Value("${Tencent.secretKey}")
    private String SecretKey;
    @Value("${Tencent.regionName}")
    private String regionName;
    @Value("${Tencent.bucketName}")
    private String bucketName;
    @Value("${Tencent.DefaultUrl}")
    private String defaultUrl;

    @Override
    public void afterPropertiesSet() {
        APPID = appid;
        ACCESS_KEY = accessKey;
        SECRET_KEY = SecretKey;
        REGION_NAME = regionName;
        BUCKET_NAME = bucketName;
        DEFAULT_URL = defaultUrl;
    }
}

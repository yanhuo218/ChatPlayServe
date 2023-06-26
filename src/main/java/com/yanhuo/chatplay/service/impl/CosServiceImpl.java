package com.yanhuo.chatplay.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.yanhuo.chatplay.common.ResultCode;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.service.CosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.yanhuo.chatplay.common.utils.ConstantPropertiesUtil.*;

@Slf4j
@Service
public class CosServiceImpl implements CosService {
    @Override
    @Async
    public void updateFileAvatar(File file, String url) {
        try {
            BasicCOSCredentials credentials = new BasicCOSCredentials(APPID, ACCESS_KEY, SECRET_KEY);
            ClientConfig clientConfig = new ClientConfig(new Region(REGION_NAME));
            COSClient client = new COSClient(credentials, clientConfig);
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, url, file);
            client.putObject(putObjectRequest);
            file.delete();
        } catch (Exception e) {
            throw new ChatPlayException(ResultCode.SERVER_ERROR, "上传图片错误:" + e);
        }
    }
}

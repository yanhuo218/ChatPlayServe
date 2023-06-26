package com.yanhuo.chatplay.controller;

import com.yanhuo.chatplay.common.R;
import com.yanhuo.chatplay.common.ResultCode;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.service.CosService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.yanhuo.chatplay.common.utils.ConstantPropertiesUtil.DEFAULT_URL;
import static com.yanhuo.chatplay.common.utils.MultipartFileToFileUtils.multipartFileToFile;

@CrossOrigin //跨域
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final CosService cosService;

    @Autowired
    protected FileUploadController(CosService cosService) {
        this.cosService = cosService;
    }

    @PostMapping("upload")
    public R upload(@RequestParam("img") MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("_", "");
            String date = new DateTime().toString("yyyy/MM/dd");
            String key = "AVATAR/" + date + "/" + uuid + file.getOriginalFilename();
            String Url = DEFAULT_URL + key;
            cosService.updateFileAvatar(multipartFileToFile(file), key);
            return R.ok().message("文件上传成功").data("url", Url);
        } catch (Exception e) {
            throw new ChatPlayException(ResultCode.SERVER_ERROR, "错误");
        }
    }

}

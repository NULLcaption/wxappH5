package com.cxg.weChat.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* @Description:    配置路径
* @Author:         Cheney Master
* @CreateDate:     2018/8/1 15:30
* @Version:        1.0
*/

@Component
public class BootdoConfig {
	//上传路径
	@Value("${bootdo.uploadPath}")
	private String uploadPath;
	//图片路径
	@Value("${bootdo.imagePath}")
	private String imagePath;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}

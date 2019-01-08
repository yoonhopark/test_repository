package com.yoonho.repo.controller;

import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoonho.repo.service.*;

@RestController
public class ImageController {
	@Autowired
	ImageHandler imageHandler;
	
	@RequestMapping("/getMergedImage")
	public void getMergedImage(HttpServletResponse response) throws Exception {
		try {
			response.setContentType("image/jpeg;charset=UTF-8");
			OutputStream output = response.getOutputStream();
			// Service단의 Method에서 BufferedImage를 전달받아 ImageIO를 통해 response에 쓰는 부분
			ImageIO.write(imageHandler.getImage(), "jpeg", output);
		} catch (Exception e) {
			
		}
	}
}

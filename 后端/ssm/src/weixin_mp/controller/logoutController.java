/*
 * 接收前端的Post请求，解析json串，识别sessionId
 * 给前端返回'status:success|failure'json串
 * 
 */


package weixin_mp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import weixin_mp.entity.userInfo;
import weixin_mp.serviceImpl.userServiceImpl;

@RestController
@RequestMapping("/logout/")      //用户注销映射'/logout/'

public class logoutController {
	
	@Autowired               //注入userServiceImpl对象，下文直接使用，不用new对象
	private userServiceImpl userServiceImpl;
	
	//监听Post请求，删除用户信息
	@PostMapping("")
	public String deleUserInfo(String sessionId) throws JSONException {
		String status=userServiceImpl.deleteUserInfo(sessionId);
		JSONObject statusJsonObject=new JSONObject();
		statusJsonObject.put("status", status);
		return statusJsonObject.toString();
	}
}

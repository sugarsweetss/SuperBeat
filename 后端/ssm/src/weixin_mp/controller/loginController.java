package weixin_mp.controller;

import weixin_mp.serviceImpl.userServiceImpl;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *获取前端传的用户code,调用userInfoServiceImpl更新数据库，加入新玩家信息 
 */

@RestController
@RequestMapping("/login/")

public class loginController {
	
	@Autowired
	//引入userServiceImpl接口
	private userServiceImpl userServiceImpl;
	
	@PostMapping("")
	//更新数据库，添加新玩家信息，并返回sessionId和userChoice
	public String insertUserinfo(String code) throws JSONException {
		//userInfo代表如果用户已经注册，要给前端的用户信息
		return userServiceImpl.insertUserInfo(code);
	}
}

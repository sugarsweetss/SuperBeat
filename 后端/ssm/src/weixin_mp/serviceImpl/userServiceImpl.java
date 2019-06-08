package weixin_mp.serviceImpl;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.util.JSON;

import weixin_mp.entity.userInfo;
import weixin_mp.mapper.userInfoMapper;
import weixin_mp.methods.wxlogin;
import weixin_mp.service.userService;
import weixin_mp.methods.sessionIdGenerate;

@Service
public class userServiceImpl implements userService{
	
	//注入userInfo接口类
	@Autowired
	private userInfoMapper userInfoMapper;
	
	@Override
	//删除sessionId的用户记录
	public String deleteUserInfo(String sessionId) {
	    if(userInfoMapper.deleteUserInfo(sessionId)==1)
			return "success";          //删除成功
		else
			return "failure";          //删除失败
	}
	
	@Override
	//设置玩家的阵营
	public void setUserChoice(String sessionId,String userChoice) {
		userInfoMapper.setUserChoice(sessionId, userChoice);
	}
	
	@Override
	//插入新玩家信息，并返回sessionId和userChoice
	public String insertUserInfo(String code) throws JSONException {
		Map<String, String> wxres=wxlogin.getSessionInfo(code);
		//如果成功返回openId,sessionKey
		if(!wxres.isEmpty()) {
			String openId=wxres.get("openid");   //获取openId
			//如果用户已经注册过
			if(userInfoMapper.userHasRegistered(openId)==1) {            
				String sessionId=userInfoMapper.findSessionId(openId);
				String userChoice=userInfoMapper.findUserChoice(openId);
				return userInfoJson(sessionId, userChoice);
			}
			//新用户
			else {
				String sessionKey=wxres.get("session_key");      //获取sessionKey
				String sessionId=sessionIdGenerate.getSessionId();                      //需要利用加密算法得到
				userInfo user=new userInfo(sessionId,openId,sessionKey);
				userInfoMapper.insertUserInfo(user);
				return userInfoJson(sessionId,"white");                      //新用户的userChoice为white
			}
		}
		else {
			System.out.println("未获取到openId和sessionKey!");
			return "false";
		}
	}
	
	@Override
	//把用户的sessionId和userChoice转化为json串
	public String userInfoJson(String sessionId,String userChoice) throws JSONException {
		JSONObject userInfoJsonObject=new JSONObject();           //存用户sessionId和userChoice的json对象
		userInfoJsonObject.put("sessionId",sessionId);
		userInfoJsonObject.put("userChoice",userChoice);
		return userInfoJsonObject.toString();
	}
	
	
	
}

package weixin_mp.methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSON;

/*
 *根据前端的用户code，调用微信接口，得到对应的openId和sessionKey 
 */

public class wxlogin {
	public static Map<String, String> getSessionInfo(String code) {
		@SuppressWarnings("unchecked")
		Map<String, String> wxres = new HashedMap();
		String wxspAppid = "wx98915a1bd99514eb";
		// 小程序的 app secret (在微信小程序管理后台获取)
		String wxspSecret = "c7581199b0f8b041f18490184c36e37d";
		// 授权（必填）
		String grant_type = "authorization_code";
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
				+ grant_type;
		params = "https://api.weixin.qq.com/sns/jscode2session?" + params;
		// 获取json
		System.out.println(params);
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(params);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 转为map
		String res = json.toString();
		@SuppressWarnings("rawtypes")
		Map mapTypes = JSON.parseObject(res);
		System.out.println(mapTypes);
		//如果失败返回空值
		//如果失败返回空值
		if (mapTypes.get("errcode") != null) {		
			System.out.println(wxres+"-------------失败返回空值");
			return wxres;
		}
		//满足UnionID返回条件时，返回的JSON数据包
		if (mapTypes.get("unionid") != null) {	
			wxres.put("openid", (String) mapTypes.get("openid"));	
			wxres.put("session_key", (String) mapTypes.get("session_key"));	
			wxres.put("unionid", (String) mapTypes.get("unionid"));	
			System.out.println(wxres+"-------------满足UnionID返回条件时");
			return wxres;
		}
		//正常返回的JSON数据包
		else {
		wxres.put("openid", (String) mapTypes.get("openid"));	
		wxres.put("session_key", (String) mapTypes.get("session_key"));	
		System.out.println(wxres+"-------------正常返回的JSON数据包");
		return wxres;
		}
	}
}

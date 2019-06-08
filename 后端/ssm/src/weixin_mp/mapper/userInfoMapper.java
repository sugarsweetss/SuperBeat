package weixin_mp.mapper;

import org.apache.ibatis.annotations.Param;

import weixin_mp.entity.userInfo;

//操作用户信息数据库调用的接口
public interface userInfoMapper {
	//删除用户在数据库中对应的记录
	int deleteUserInfo(@Param("sessionId") String sessionId);
	
	//获取sessionId的玩家的userChoice
	String getUserChoice(@Param("sessionId") String sessionId);
	
	//根据前端的sessionId和userChoice信息，找出sessionId玩家，设置他的userChoice
	void setUserChoice(@Param("sessionId") String sessionId,@Param("userChoice") String userChoice);
	
	//存入用户的code,openId,sessionId,sessionKey
	void insertUserInfo(userInfo user);
	
	//判断用户是否已经注册
    int userHasRegistered(@Param("openId") String openId);
    
    //根据openId查找sessionId
    String findSessionId(@Param("openId") String openId);
    
    //根据openId查找userChoice
    String findUserChoice(@Param("openId") String userChoice);
}

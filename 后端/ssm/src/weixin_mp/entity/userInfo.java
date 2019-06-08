package weixin_mp.entity;

public class userInfo {
	private String code;      //前端发来的用户临时身份标识
	private String sessionId;   //前后端交互的用户唯一标识
	private String openId;      //微信根据code发给我的唯一openId
	private String sessionKey;     //和微信间通信的唯一凭证
	private String userChoice;     //用户选择的阵营
	public userInfo() { }
	public userInfo(String code,String sessionId,String openId,String sessionKey,String userChoice) {
		super();
		this.code=code;
		this.openId=openId;
		this.sessionId=sessionId;
		this.sessionKey=sessionKey;
		this.userChoice=userChoice;
	}
	//初始化用户信息，还未选择阵营时默认是white
	public userInfo(String sessionId,String openId,String sessionKey) {
		super();
		this.openId=openId;
		this.sessionId=sessionId;
		this.sessionKey=sessionKey;
		this.userChoice="white";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code=code;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId=openId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId=sessionId;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKry(String sessionKey) {
		this.sessionKey=sessionKey;
	}
	public String getUserChoice() {
		return userChoice;
	}
	public void setUserChoice(String userChoice) {
		this.userChoice=userChoice;
	}
}

package weixin_mp.service;

import java.util.Map;

import weixin_mp.entity.Test;

public interface TestService {

	//插入新对象
	Map<String, Object> newTest(Test test);
	//根据名字获取新对象
	Test getTest(String name);
	
}

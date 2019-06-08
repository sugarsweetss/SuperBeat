package weixin_mp.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weixin_mp.entity.Test;
import weixin_mp.mapper.TestMapper;
import weixin_mp.service.TestService;

/**
 * 业务逻辑都在这个类里处理
 */

//必须要有这个注解
@Service
public class TestServiceImpl implements TestService {

	//注入testMapper接口类
	@Autowired
	private TestMapper testMapper;
	
	@Override
	public Map<String, Object> newTest(Test test) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", 0);
		if(testMapper.newTest(test)==1)
			map.replace("result", 1);
		return map;
	}

	@Override
	public Test getTest(String name) {
		return testMapper.getTest(name);
	}

}

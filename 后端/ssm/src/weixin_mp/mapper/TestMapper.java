package weixin_mp.mapper;

import org.apache.ibatis.annotations.Param;

import weixin_mp.entity.Test;

/**
 * 这里面定义接要操作数据库的接口
 * 具体的sql实现在TestMapper.xml里实现
 */
public interface TestMapper {
	
	//方法名要与xml文件 中对应的id相同
	int newTest(Test test);
	
	//@Param("") 对应SQL语句中需要的参数
	Test getTest(@Param("name") String name);
	
}

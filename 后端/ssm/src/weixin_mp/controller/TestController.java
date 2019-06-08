package weixin_mp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import weixin_mp.entity.Test;
import weixin_mp.service.TestService;

/**
 * 控制层：负责拦截请求，处理前后端参数传递
 * json格式数据
 */
@RestController
@RequestMapping("/test/")   //映射的路径
public class TestController {

	//将testService对象注入进来，下文可以直接使用，不用new对象
	@Autowired
	private TestService testService;
	
	/*
	 * 映射 的路径   与上边的拼起来就是 /test/newTest
	 * 方法中的参数就是前端传给后端的参数，名称要相同
	 * post请求用postmapping注解
	 * get请求用getmapping注解
	 */
	// 请求示例  http://localhost:8080/ssm/test/newTest?name=aaa&number=1
//	@PostMapping("newTest")
	@RequestMapping("newTest") 
	public Map<String, Object> newTest(String name, int number) {
		Test test = new Test(name, number);
		return testService.newTest(test);
	}
	
	// 示例：http://localhost:8080/ssm/test/getTest?name=aaa
	@GetMapping("getTest")
	public Test getTest(String name) {
		return testService.getTest(name);
	}
	
	//单文件上传
	@RequestMapping("/fileUpload")
	public void singleFileUpload(MultipartFile image) {
		String filePath = "文件地址";
		String originalFilename = image.getOriginalFilename();
		File file = new File(filePath+File.separator+originalFilename);
		try {
			image.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

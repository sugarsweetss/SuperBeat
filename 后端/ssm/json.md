> 本接口格式声明  
>【模块功能】：  
> 【服务器地址】：【请求方法】  
> 【请求格式】{ key1 : value1, ...}  
> 【响应格式】{key1 : value1, ...}  

### 用户登录：  
http://localhost:8080/login : GET
```
request:{
    code:res.code
}
response:{
    sessionId: id,
    userChoice: "blue|green"
}
```

### 选择阵营：  
http://localhost:8080/choose : GET  
```
request:{
    sessionId: id,
    userChoice: "blue|green"
}
response:{
    
    
}
```  

### 用户注销：  
http://localhost:8080/logout : GET  
```
request:{
    sessionId: id,
    destroy: "destroy"
}
response:{
    status:"success|failure"
}
```  

### 地图操作：
http://localhost:8080/map : GET
```
request:{
    init : true|false,     //true:初始化地图(只返回经纬度附近的点)|false:更新地图
    sessionId: xxx,
    latitude: xxx,
    longitude: xxx
}

response:{
    markers : [{
              id: id,
              iconPath: "../../image/setpoint_green.png",
              longitude: xxx,
              latitude: xxx,
              callout: {
                content: 'xxx',
                bgColor: 'xxx'
              }
            }...],
     circles : [{
             id: id,
             latitude: xxx,
             longitude: xxx,
             color: 'xxx',
             fillColor: 'xxx',
             radius: xxx
         }...]
```  

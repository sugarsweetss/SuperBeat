package weixin_mp.controller;

import weixin_mp.entity.marker;
import weixin_mp.mapper.markerMapper;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weixin_mp.serviceImpl.mapServiceImpl;

/*
 *接收前端的地图初始化或者更新信息，识别攻击者的经纬度，select或者update数据库记录 
 *给前端返回circle和marker的json串 
 * 
 */

@RestController
@RequestMapping("/map/")       //地图初始化和更新映射'/map/'

public class mapController {
	
	@Autowired         //注入mapServiceImpl对象，下文直接使用
	private mapServiceImpl mapServiceImpl;
	
	//监听Post请求，初始化或更新地图
	@SuppressWarnings("unchecked")
	@PostMapping("")
	/*函数形参
	 * init:初始化或者更新的标记字符串
	 * sessionId:玩家唯一标识
	 * latitude:攻击点经度
	 * longitude:攻击点纬度
	 * nelatitude:显示范围东北角经度
	 * nelongitude:显示范围东北角纬度
	 * swlatitude:显示范围西南角经度
	 * swlatitude:显示范围西南角纬度
	 */
	public String mapOperate(String init,String sessionId,double latitude,double longitude,double nelongitude,double nelatitude,double swlatitude,double swlongitude) throws Exception {
		//init="true"(初始化地图)对应的处理      *注意;sessionId在没有被删除前，是唯一的，且前端传来的sessionId一定存在数据库中，所以不需要判断玩家是否注销
		if(nelatitude<1||swlatitude<1||nelongitude<1||swlongitude<1) {
			nelatitude=latitude+1;
			swlatitude=latitude-1;
			nelongitude=longitude+1;
			swlongitude=longitude-1;
		}
		if(init.equals("true")) {       
			//返回西南角和东北角之间的所有marker信息(json)
			return mapServiceImpl.getNearMarkers(nelatitude,swlatitude,nelongitude,swlongitude);  
		}
		//init="false"(更新地图)对应的处理
		else {
			ArrayList<marker> nearMarkersBeforeCalc=(ArrayList<marker>) mapServiceImpl.getNearMarkersArray(nelatitude, swlatitude, nelongitude, swlongitude).clone();       //存储更新前的攻击点附近的markers
			mapServiceImpl.markerUpdate(mapServiceImpl.calcNearMarkers(sessionId,longitude,nelongitude,swlongitude,latitude,nelatitude,swlatitude));        //更新攻击点附近的markers信息
			//return mapServiceImpl.getUpdateResult(mapServiceImpl.getNearMarkersCalcResult());           //更新后的攻击点附近marker数据传给前端
			return mapServiceImpl.getUpdateResult(mapServiceImpl.getNearMarkersForFrontEnd(nearMarkersBeforeCalc, mapServiceImpl.getNearMarkersCalcResult()));  //把攻击点附近改变的markers信息传给前端
		}
	}
}

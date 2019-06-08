package weixin_mp.mapper;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import weixin_mp.entity.marker;

public interface markerMapper {
	
	//初始化地图，获取所有点的信息(东北角和西南角之间)
	ArrayList<marker> initNearMarkers(@Param("nelatitude") double nelatitude,@Param("swlatitude") double swlatitude,
			@Param("nelongitude") double nelongitude,@Param("swlongitude") double swlongitude);
	
	//更新地图
	void markersUpdate(List<marker> markerList);
	
	//获取地图上所有marker信息
	ArrayList<marker> getAllMarkers();
	
	//选取用户攻击点附近的markers参与地图更新的计算
	ArrayList<marker> getNearMarkersForCalc(@Param("longitude") double longitude,@Param("nelongitude") double nelongitude,
			@Param("swlongitude") double swlongitude,@Param("latitude") double latitude,@Param("nelatitude") double nelatitude,@Param("swlatitude") double swlatitude);
}

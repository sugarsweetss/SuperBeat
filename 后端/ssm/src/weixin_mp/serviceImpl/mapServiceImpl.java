package weixin_mp.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import h.intitem;
import weixin_mp.entity.marker;
import weixin_mp.mapper.markerMapper;
import weixin_mp.mapper.userInfoMapper;
import weixin_mp.service.mapService;
import weixin_mp.methods.Beat;
import weixin_mp.methods.calcMarkers;

@Service
public class mapServiceImpl implements mapService{
	
	private ArrayList<marker> allMarkersCalcResult;         //存储经过地图更新的所有markers信息
	private ArrayList<marker> nearMarkersCalcResult;        //存储经过地图更新的攻击点附近的markers信息
	//注入markerMapper接口类
	@Autowired
	private markerMapper markerMapper;
	@Autowired
	private userInfoMapper userInfoMapper;
	
	//获取所有marker信息,可以被mapController中初始化和更新两种操作复用
	@Override
	public String getAllMarkers() throws JSONException {
		return markerArrayToString(markerMapper.getAllMarkers());
	}
	
	//获取更新后的地图信息，返回给前端并更新数据库
	@Override
	public String getUpdateResult(ArrayList<marker> markerList) throws JSONException {
		return markerArrayToString(markerList);
	}
	
	//处理传入的marker数组，返回markerJsonString
	@Override
	public String markerArrayToString(ArrayList<marker> markerList) throws JSONException {
		int markerCount=markerList.size();      //存marker总个数
		String MarkersJsonString="";     //存markerList中所有marker json串
		MarkersJsonString+="[";
		for(int i=0;i<markerCount;i++) {
			JSONObject markerJsonObject=new JSONObject();
			//key-value插入markerJson对象,并存入json串
			markerJsonObject.put("id", markerList.get(i).getId());
			markerJsonObject.put("longitude", markerList.get(i).getLongitude());
			markerJsonObject.put("latitude", markerList.get(i).getLatitude());
			markerJsonObject.put("content", markerList.get(i).getContent());
			markerJsonObject.put("color", markerList.get(i).getColor());
		    MarkersJsonString+=markerJsonObject.toString()+",";
		}
		if(markerCount!=0)          //如果json串为空，不需去除","
		MarkersJsonString=MarkersJsonString.substring(0, MarkersJsonString.length()-1);     //去除最后一个marker对应json串后面的","
		MarkersJsonString+="]";
		return MarkersJsonString;
	}
	
	//更新数据库的marker信息
	@Override
	public void markerUpdate(ArrayList<marker> markerList) {
		markerMapper.markersUpdate(markerList);
	}
	
	//对应calcMarkers的计算所有marker功能，返回改变后的markers
	@Override
	public ArrayList<marker> calcAllMarkers(double longitude,double latitude,String sessionId){
		String color=getAttackerColor(sessionId);
		allMarkersCalcResult=markerMapper.getAllMarkers();    //存储改变前的marker信息
		calcMarkers.beatMarkers(new Beat(longitude, latitude, color), allMarkersCalcResult);    //更新markers信息
		return allMarkersCalcResult;
	}
	
	//计算攻击点附近的markers变化情况，返回攻击点附近的markers
	@Override
	public ArrayList<marker> calcNearMarkers(String sessionId,double longitude,double nelongitude,double swlongitude,double latitude,double nelatitude,double swlatitude){
		String color=getAttackerColor(sessionId);
		nearMarkersCalcResult=markerMapper.getNearMarkersForCalc(longitude, nelongitude, swlongitude, latitude, nelatitude, swlatitude);
		calcMarkers.beatMarkers(new Beat(longitude, latitude, color), nearMarkersCalcResult);
		return nearMarkersCalcResult;
	}
	
	//返回用户攻击点附近(西南角和东北角之间)区域的marker信息
	@Override
	public String getNearMarkers(double nelatitude,double swlatitude,double nelongitude,double swlongitude) throws JSONException {
		return markerArrayToString(markerMapper.initNearMarkers(nelatitude, swlatitude, nelongitude, swlongitude));
	}
	
	//获取攻击者的阵营
	@Override
	public String getAttackerColor(String sessionId) {
		return userInfoMapper.getUserChoice(sessionId);       //获取攻击者的阵营
	}
	
	//获取私有成员allMarkersCalcResult
	public ArrayList<marker> getAllMarkersCalcResult(){
		return allMarkersCalcResult;
	}
	
	//获取私有成员nearMarkersCalcResult
	public ArrayList<marker> getNearMarkersCalcResult(){
		return nearMarkersCalcResult;
	}
	
	@Override
	public ArrayList<marker> getNearMarkersArray(double nelatitude,double swlatitude,double nelongitude,double swlongitude){
		return markerMapper.initNearMarkers(nelatitude, swlatitude, nelongitude, swlongitude);
	}
	
	//返回需要传给前端的攻击点附近的markers信息
	@Override
	public ArrayList<marker> getNearMarkersForFrontEnd(ArrayList<marker> nearMarkersBeforeCalc,ArrayList<marker> nearMarkersAfterCalc){
		int length=nearMarkersBeforeCalc.size();
		Set<marker> nearMarkesBeforeCalcHashSet=new HashSet<>(nearMarkersBeforeCalc);
		Set<marker> nearMarkerAfterCalcHashSet=new HashSet<>(nearMarkersAfterCalc);
		nearMarkerAfterCalcHashSet.removeAll(nearMarkesBeforeCalcHashSet);
		ArrayList<marker> nearMarkersForFrontEnd=new ArrayList<marker>(nearMarkerAfterCalcHashSet);
		return nearMarkersForFrontEnd;
	}
}
	

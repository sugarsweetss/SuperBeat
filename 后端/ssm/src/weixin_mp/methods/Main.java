package weixin_mp.methods;

import java.util.ArrayList;

import weixin_mp.entity.marker;    //引入entity中的marker实体

public class Main {
    
	//#ffffff代表白色,#00ffc8代表绿色,#00c8ff代表蓝色
    private static void testCode1(){
        marker p1 = new marker("123", 121.822087, 39.086476, "#ffffff", 0); //宿舍
        marker p2 = new marker("456", 121.821105, 39.085464, "#00ffc8" , 1); //篮球场
        marker p3 = new marker("789", 121.818535, 39.086701, "#00c8ff", 0); //教学楼
        ArrayList<marker> markersList = new ArrayList<>(3);
        markersList.add(p1);
        markersList.add(p2);
        markersList.add(p3);
        for (int i = 0; i < 3; i ++){
            markersList.get(i).print();
        }
        System.out.println("--------------创建匿名BLUE对象Beat，宿舍附近----------------");
        calcMarkers.beatMarkers(new Beat(121.821984,39.086485, "#00c8ff"), markersList); //创建匿名对象Beat，宿舍附近
        for (int i = 0; i < 3; i ++){
            markersList.get(i).print();
        }
        System.out.println("--------------创建匿名BLUE对象Beat，篮球场附近----------------");
        calcMarkers.beatMarkers(new Beat(121.821039,39.08549, "#00c8ff"), markersList); //创建匿名对象Beat，篮球场附近
        for (int i = 0; i < 3; i ++){
            markersList.get(i).print();
        }
        System.out.println("--------------创建匿名BLUE对象Beat，教学楼附近----------------");
        calcMarkers.beatMarkers(new Beat(121.81847,39.086714, "#00c8ff"), markersList); //创建匿名对象Beat，教学楼附近
        for (int i = 0; i < 3; i ++){
            markersList.get(i).print();
        }
    }

    private static void testCode2(){
        marker p1 = new marker("789", 121.822087, 39.086476, "#ffffff", 0); //宿舍
        marker p2 = new marker("456", 121.821105, 39.085464, "#ffffff", 0); //篮球场 篮球场标记点距离宿舍140m
        marker p3 = new marker("123", 121.818535, 39.086701, "#ffffff", 0); //教学楼
        ArrayList<marker> markersList = new ArrayList<>(3);
        markersList.add(p1);
        markersList.add(p2);
        markersList.add(p3);
        for (int i = 0; i < 3; i ++){
            markersList.get(i).print();
        }
        for (int j = 0;  j < 10; j ++){
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println(j + "--------------创建匿名BLUE对象Beat，宿舍附近----------------");
            calcMarkers.beatMarkers(new Beat(121.821984,39.086485, "#00c8ff"), markersList); //创建匿名对象Beat，宿舍附近
            markersList.get(0).print();
            markersList.get(1).print();
            markersList.get(2).print();
//            System.out.println("distance: "+ calcMarkers.distance(markersList.get(0), markersList.get(1)));
//            System.out.println("radius1: " + calcMarkers.radius(markersList.get(0)) + ", radius2: " + calcMarkers.radius(markersList.get(1)));
            System.out.println(j + "--------------创建匿名GREEN对象Beat，篮球场附近----------------");
            calcMarkers.beatMarkers(new Beat(121.821039,39.08549, "#00ffc8"), markersList); //创建匿名对象Beat，篮球场附近
            markersList.get(0).print();
            markersList.get(1).print();
            markersList.get(2).print();
//            System.out.println("distance: "+ calcMarkers.distance(markersList.get(0), markersList.get(1)));
//            System.out.println("radius1: " + calcMarkers.radius(markersList.get(0)) + ", radius2: " + calcMarkers.radius(markersList.get(1)));
            System.out.println("--------------创建匿名BLUE对象Beat * 3，教学楼附近----------------");
            calcMarkers.beatMarkers(new Beat(121.81847,39.086714, "#00c8ff"), markersList); //创建匿名对象Beat，教学楼附近
            calcMarkers.beatMarkers(new Beat(121.81847,39.086714, "#00c8ff"), markersList); //创建匿名对象Beat，教学楼附近
            calcMarkers.beatMarkers(new Beat(121.81847,39.086714, "#00c8ff"), markersList); //创建匿名对象Beat，教学楼附近
            markersList.get(0).print();
            markersList.get(1).print();
            markersList.get(2).print();

        }

    }
    public static void main(String[] args) {
        //testCode1();//攻击导致圈变色以及大小变化
        testCode2();//圆相切状态下的攻击
    }
}


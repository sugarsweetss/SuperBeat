package weixin_mp.methods;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import weixin_mp.entity.marker;            //引入entity中的marker实体

/**
 * @Author: ziuno
 * @Date: 2019/4/6 18:31
 */
public class calcMarkers {
    private static double EARTH_RADIUS = 6378.137;
    private static double INIT_RADIUS = 20.0;
    private static double STEP_SIZE = 1.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double distance(marker marker1, marker marker2) {
        double lng1 = marker1.getLongitude();
        double lng2 = marker2.getLongitude();
        double lat1 = marker1.getLatitude();
        double lat2 = marker2.getLatitude();
        return distance(lng1, lat1, lng2, lat2);
    }

    private static double distance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    public static double radius(marker marker) {
        return marker.getContent() * STEP_SIZE + INIT_RADIUS;
    }

    private static void updateTangency(List<marker> allMarkers, boolean[][] tangency) {
        double distance;
        int size = allMarkers.size();
        for (int i = 0; i < size; i++) {
            tangency[i][i] = false;
            for (int j = 0; j < i; j++) {
                distance = distance(allMarkers.get(i), allMarkers.get(j));
                tangency[i][j] = tangency[j][i] = distance <= (radius(allMarkers.get(i)) + radius(allMarkers.get(j)));
            }
        }
    }

    public static void beatMarkers(Beat beat, ArrayList<marker> allMarkers) {
        int size = allMarkers.size();
        boolean[][] tangency = new boolean[size][size];
        double longitude = beat.getLongitude();
        double latitude = beat.getLatitude();
        boolean[] inCircle = new boolean[size];
        long content;
        marker tmpMarker;
        String tmpColor;
        for (int i = 0; i < size; i++) {
            tmpMarker = allMarkers.get(i);
            inCircle[i] = distance(longitude, latitude, tmpMarker.getLongitude(), tmpMarker.getLatitude()) < radius(tmpMarker);
        }
        updateTangency(allMarkers, tangency);
        for (int i = 0; i < size; i++) {
            if (inCircle[i]) {
                tmpMarker = allMarkers.get(i);
                if (allMarkers.get(i).getColor().equals("white")) {
                    content = tmpMarker.getContent() + 1;
                    tmpMarker.setContent(content);;
                    tmpMarker.setColor(beat.getColor());
                    return;
                } else if (beat.getColor().equals(allMarkers.get(i).getColor())) {
                    content = tmpMarker.getContent() + 1;
                    tmpMarker.setContent(content);
                    tmpColor = tmpMarker.getColor();
                    for (int j = 0; j < size; j++) {
                        if (i == j)
                            continue;
                        if (tangency[i][j]) {
                            tmpMarker = allMarkers.get(j);
                            content = tmpMarker.getContent() - 1;
                            tmpMarker.setContent(content);
                            if (tmpMarker.getContent() == 0) {
                                tmpMarker.setColor("white");;
                            } else if (tmpMarker.getContent() == -1){
                                tmpMarker.setContent(0);;
                                tmpMarker.setColor(tmpColor);
                            }
                        }
                    }
                } else{
                    tmpMarker.setContent(tmpMarker.getContent() - 1);
                    if (tmpMarker.getContent() == 0) {
                        tmpMarker.setColor("white");
                    }
                }
                updateTangency(allMarkers, tangency);
            }
        }
    }
}

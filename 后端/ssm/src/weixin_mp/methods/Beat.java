package weixin_mp.methods;

/**
 * @Author: ziuno
 * @Date: 2019/4/6 19:06
 */
public class Beat {
    private double longitude, latitude;         //被攻击点的经纬度

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getColor() {
        return color;
    }

    private String color;        //攻击点的颜色(阵营)

    public Beat(double longitude, double latitude, String color){
        this.longitude = longitude;
        this.latitude = latitude;
        this.color=color;
    }
}


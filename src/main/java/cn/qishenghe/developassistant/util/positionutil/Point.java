package cn.qishenghe.developassistant.util.positionutil;

/**
 * 点实体类
 */
public class Point {

    // 经度
    private double lng;
    // 纬度
    private double lat;
    // 地名
    private String positionName;

    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public Point(double lng, double lat, String positionName) {
        this.lng = lng;
        this.lat = lat;
        this.positionName = positionName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}

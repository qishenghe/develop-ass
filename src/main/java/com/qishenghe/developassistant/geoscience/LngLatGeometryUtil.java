package com.qishenghe.developassistant.geoscience;

import com.qishenghe.developassistant.geometry.Point;

/**
 * 经纬度几何计算工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public class LngLatGeometryUtil {

    /**
     * 地球半径，单位公里
     */
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 【重载】计算两个经纬度坐标间的距离
     *
     * @param point1 point1
     * @param point2 point2
     * @return 距离（单位：米）
     */
    public static double getDistance(Point point1, Point point2) {
        return getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    /**
     * 计算两个经纬度坐标间的距离
     *
     * @param lat1 纬度1
     * @param lng1 经度1
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return 距离（单位:米）
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = rad(lat1) - rad(lat2);
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS * 1000;
        s = Math.round(s);
        return s;
    }

    /**
     * 【子方法】距离计算
     *
     * @param d 经/纬度
     * @return 弧度计算结果
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}

package com.qishenghe.developassistant.geoscience;

import com.qishenghe.developassistant.exception.DevelopAssException;
import com.qishenghe.developassistant.geometry.Point;

/**
 * 经纬度坐标系转换工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public class LngLatTranUtil {

    /**
     * 根据自定义转换要求转换经纬度点
     *
     * @param point  经纬度点
     * @param source 原经纬度坐标系
     * @param target 目标经纬度坐标系
     * @return 转换结果
     */
    public static Point lngLatTrans(Point point, LngLatCoordinateEnum source, LngLatCoordinateEnum target) {

        Point gpsPoint;

        // 根据原坐标系转至通用坐标系
        if (LngLatCoordinateEnum.COORDINATE_GCJ02 == source) {
            gpsPoint = gdToGps(point);
        } else if (LngLatCoordinateEnum.COORDINATE_BD09 == source) {
            gpsPoint = bdToGps(point);
        } else if (LngLatCoordinateEnum.COORDINATE_GPS == source) {
            gpsPoint = point;
        } else {
            throw new DevelopAssException(Point.class.getName() + " : cause : " + "不支持的坐标系");
        }

        Point targetPoint;

        // 将通用坐标系转至目标坐标系
        if (LngLatCoordinateEnum.COORDINATE_GCJ02 == target) {
            targetPoint = gpsToGd(gpsPoint);
        } else if (LngLatCoordinateEnum.COORDINATE_BD09 == target) {
            targetPoint = gpsToBd(gpsPoint);
        } else if (LngLatCoordinateEnum.COORDINATE_GPS == source) {
            targetPoint = gpsPoint;
        } else {
            throw new DevelopAssException(Point.class.getName() + " : cause : " + "不支持的坐标系");
        }

        return targetPoint;

    }

    //==================================================================================================================

    /**
     * 百度 -- Gps
     */
    private static Point bdToGps(Point point) {
        double lat = point.getX();
        double lng = point.getY();
        double[] latLng = bd09ToGps84(lat, lng);

        return new Point(latLng[1], latLng[0]);
    }

    /**
     * 高德 -- Gps
     */
    private static Point gdToGps(Point point) {
        double lat = point.getX();
        double lng = point.getY();
        double[] latLng = gcj02ToGps84(lat, lng);

        return new Point(latLng[1], latLng[0]);
    }

    /**
     * Gps -- 百度
     */
    private static Point gpsToBd(Point point) {
        double lat = point.getX();
        double lng = point.getY();
        double[] latLng = gps84ToBd09(lat, lng);

        return new Point(latLng[1], latLng[0]);
    }

    /**
     * Gps -- 高德
     */
    private static Point gpsToGd(Point point) {
        double lat = point.getX();
        double lng = point.getY();
        double[] latLng = gps84ToGcj02(lat, lng);

        return new Point(latLng[1], latLng[0]);
    }

    // ==================================================================================================================

    /**
     * 常数集
     */
    private static final double PI = 3.1415926535897932384626;
    private static double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    private static double A = 6378245.0;
    private static double EE = 0.00669342162296594323;

    /**
     * (GPS-84) to 火星坐标系 (GCJ-02)
     */
    private static double[] gps84ToGcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 火星坐标系 (GCJ-02) to (GPS-84)
     */
    private static double[] gcj02ToGps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{latitude, lontitude};
    }

    /**
     * 火星坐标系 (GCJ-02) to 百度坐标系 (BD-09)
     */
    private static double[] gcj02ToBd09(double lat, double lon) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * X_PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new double[]{tempLat, tempLon};
    }

    /**
     * 百度坐标系 (BD-09) to 火星坐标系 (GCJ-02)
     */
    private static double[] bd09ToGcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        return new double[]{tempLat, tempLon};
    }

    /**
     * (GPS-84) to 百度坐标系 (BD-09)
     */
    private static double[] gps84ToBd09(double lat, double lon) {
        double[] gcj02 = gps84ToGcj02(lat, lon);
        return gcj02ToBd09(gcj02[0], gcj02[1]);
    }

    /**
     * 百度坐标系 (BD-09) to (GPS-84)
     */
    private static double[] bd09ToGps84(double lat, double lon) {
        double[] gcj02 = bd09ToGcj02(lat, lon);
        // 保留小数点后六位
        // gps84[0] = retain6(gps84[0]);
        // gps84[1] = retain6(gps84[1]);
        return gcj02ToGps84(gcj02[0], gcj02[1]);
    }

    //==================================================================================================================

    // 计算核心

    /**
     * 转换纬度
     */
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 转换经度
     */
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 转换经纬度
     */
    private static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 是否在中国境外
     */
    private static boolean outOfChina(double lat, double lon) {

        // 中国经度最大最小值
        double chinaMinLng = 72.004;
        double chinaMaxLng = 137.8347;
        // 中国纬度最大最小值
        double chinaMinLat = 0.8293;
        double chinaMaxLat = 55.8271;

        boolean flag = false;
        if (lon < chinaMinLng || lon > chinaMaxLng) {
            flag = true;
        }
        if (lat < chinaMinLat || lat > chinaMaxLat) {
            flag = true;
        }
        return flag;
    }

}

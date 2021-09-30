package com.qishenghe.developassistant.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * 基于WKT格式的几何拓扑关系运算工具
 * 注：只能用于运算平面坐标系，在运算曲面坐标系时需先进行投影转换，且距离计算结果无参照意义
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe@bonc.com.cn for init
 */
public class GeometryWktUtil {

    /**
     * 【判断】是否相交 描述：判断两个集合图形是否相交
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 相交：true，不相交：false
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:32
     * @change 2021/1/11 15:32 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static boolean judgeIntersects(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.intersects(geo2);
    }

    /**
     * 【判断】是否包含 描述：判断 图形1 是否包含 图形2
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 包含：true，不包含：false
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:36
     * @change 2021/1/11 15:36 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static boolean judgeContains(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.contains(geo2);
    }

    /**
     * 【判断】是否覆盖 描述：判断 图形1 是否覆盖 图形2
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 覆盖：true，不覆盖：false
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:36
     * @change 2021/1/11 15:36 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static boolean judgeCovers(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.covers(geo2);
    }

    /**
     * 【判断】是否穿透 描述：判断 图形1 是否穿透 图形2
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 穿过：true，不穿过：false
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:36
     * @change 2021/1/11 15:36 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static boolean judgeCrosses(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.crosses(geo2);
    }

    /**
     * 【判断】是否相等 描述：判断 图形1 是否相等 图形2
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 相等：true，不相等：false
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:36
     * @change 2021/1/11 15:36 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static boolean judgeEquals(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.equals(geo2);
    }

    /**
     * 【获取】获取各个点的平面直角坐标系
     *
     * @param wkt 几何图形
     * @return 各点的平面直角坐标系坐标
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:44
     * @change 2021/1/11 15:44 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Coordinate[] getCoordinate(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getCoordinates();
    }

    /**
     * 【获取】获取反转几何
     *
     * @param wkt 几何
     * @return 反转几何对象
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:46
     * @change 2021/1/11 15:46 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry getReverse(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.reverse();
    }

    /**
     * 【获取】获取凸包几何
     *
     * @param wkt 几何
     * @return 凸包几何
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:48
     * @change 2021/1/11 15:48 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry getConvexHull(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.convexHull();
    }

    /**
     * 【获取】获取几何图形质心
     *
     * @param wkt 几何
     * @return 几何质心
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:50
     * @change 2021/1/11 15:50 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Point getCentroid(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getCentroid();
    }

    /**
     * 【获取】获取 WKT 中的几何图形个数
     *
     * @param wkt 几何
     * @return 图形个数
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:51
     * @change 2021/1/11 15:51 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static int getGeometriesNum(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getNumGeometries();
    }

    /**
     * 【获取】获取顶点个数
     *
     * @param wkt 几何
     * @return 定点个数
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:53
     * @change 2021/1/11 15:53 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static int getPointNum(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getNumPoints();
    }

    /**
     * 【获取】获取几何类型
     *
     * @param wkt 几何
     * @return 类型
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:54
     * @change 2021/1/11 15:54 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static String getGeometryType(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getGeometryType();
    }

    /**
     * 【获取】获取面积
     *
     * @param wkt 几何图形
     * @return 面积
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:05
     * @change 2021/1/11 16:05 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static double getArea(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getArea();
    }

    /**
     * 【获取】获取周长
     *
     * @param wkt 几何图形
     * @return 周长
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:05
     * @change 2021/1/11 16:05 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static double getLength(String wkt) throws ParseException {
        Geometry geo = new WKTReader().read(wkt);
        return geo.getLength();
    }

    /**
     * 【计算】交集计算 描述：计算两个几何图形的交集几何
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 交集计算结果
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:56
     * @change 2021/1/11 15:56 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry calIntersection(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.intersection(geo2);
    }

    /**
     * 【计算】并集计算 描述：计算两个几何图形的并集几何
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 并集计算结果
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:02
     * @change 2021/1/11 16:02 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry calUnion(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.union(geo2);
    }

    /**
     * 【计算】差异计算 描述：计算一个几何图形，该几何图形表示此几何图形中包含的、其他几何图形中不包含的点集的闭合
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 差异计算结果
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:56
     * @change 2021/1/11 15:56 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry calDifference(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.difference(geo2);
    }

    /**
     * 【计算】差异计算 描述：计算表示点集闭合的几何图形，该点集是此几何图形中不包含在其他几何图形中的点与此几何图形中不包含的其他几何图形中的点的并集
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 差异计算结果
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 15:56
     * @change 2021/1/11 15:56 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry calSymDifference(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.symDifference(geo2);
    }

    /**
     * 【计算】距离计算
     *
     * @param wkt1 几何图形1
     * @param wkt2 几何图形2
     * @return 两图形之间距离
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:03
     * @change 2021/1/11 16:03 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static double calDistance(String wkt1, String wkt2) throws ParseException {
        Geometry geo1 = new WKTReader().read(wkt1);
        Geometry geo2 = new WKTReader().read(wkt2);
        return geo1.distance(geo2);
    }

    /**
     * 【转换】根据 wkt String 获取几何对象
     *
     * @param wkt 几何 Wkt
     * @return 几何对象
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:09
     * @change 2021/1/11 16:09 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static Geometry transGeometryWkt(String wkt) throws ParseException {
        return new WKTReader().read(wkt);
    }

    /**
     * 【转换】根据 几何对象 获取 wkt String
     *
     * @param geo 集合对象
     * @return wkt String
     * @author qishenghe@bonc.com.cn
     * @date 2021/1/11 16:11
     * @change 2021/1/11 16:11 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public static String transWktGeometry(Geometry geo) {
        return geo.toText();
    }

}

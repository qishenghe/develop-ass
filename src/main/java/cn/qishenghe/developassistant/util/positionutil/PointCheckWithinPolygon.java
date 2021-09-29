package cn.qishenghe.developassistant.util.positionutil;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断点是否在多边形内
 */
public class PointCheckWithinPolygon {

    /**
     * 判断点是否在多边形内
     */
    public static boolean check(Point point, List<Point> points) {
        PointCheckWithinPolygon c = new PointCheckWithinPolygon();
        List<Point2D.Double> pointMap = createMap(points);
        return c.check2(transPoint(point), pointMap);
    }

    /**
     * 转点
     */
    private static Point2D.Double transPoint(Point point) {
        return new Point2D.Double(point.getLng(), point.getLat());
    }

    /**
     * 反转点
     */
    private static Point transPoint(Point2D.Double point) {
        return new Point(point.getX(), point.getY());
    }

    /**
     * 构图
     */
    private static List<Point2D.Double> createMap(List<Point> points) {

        List<Point2D.Double> map = new ArrayList<Point2D.Double>();
        for (Point singlePoint : points) {
            map.add(new Point2D.Double(singlePoint.getLng(), singlePoint.getLat()));
        }
        return map;
    }
    //==================================================================================================================

    /**
     * 返回一个点是否在一个多边形区域内
     */
    private boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        GeneralPath p = new GeneralPath();
        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);
        p.closePath();
        return p.contains(point);
    }

    /**
     * 判断点是否在多边形内
     */
    private boolean check2(Point2D.Double point, List<Point2D.Double> polygon) {
        Polygon p = new Polygon();
        final int TIMES = 1000;
        for (Point2D.Double d : polygon) {
            int x = (int) d.x * TIMES;
            int y = (int) d.y * TIMES;
            p.addPoint(x, y);
        }
        int x = (int) point.x * TIMES;
        int y = (int) point.y * TIMES;
        return p.contains(x, y);
    }

    /**
     * 判断点是否在多边形内
     */
    private static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D.Double p1, p2;//neighbour bound vertices
        Point2D.Double p = point; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    Point2D.Double p3 = pts.get((i + 1) % N); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }

    public static void main(String[] args) {
        // 参考示例
		/*PointCheckWithinPolygon c = new PointCheckWithinPolygon();
    	Point2D.Double p = new Point2D.Double(0,801);
        List<Point2D.Double> polygon = new ArrayList<Point2D.Double>();
        polygon.add(new Point2D.Double(0,800));
        polygon.add(new Point2D.Double(0,0));
        polygon.add(new Point2D.Double(600,0));
        polygon.add(new Point2D.Double(600,800));
        System.out.println(c.check2(p,polygon));*/
    }
}

package com.wx.employment.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * @author wbl
 */
public class DistanceUtil {

    /**
     * 根据两点经纬度计算两点之间的距离
     * @param longitudeFrom
     * @param latitudeFrom
     * @param longitudeTo
     * @param latitudeTo
     * @return
     */
    public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);

        return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
    }

    /**
     * 根据中心经纬度和半径计算经纬度的范围，因为圆在比较时比较麻烦，因此范围用矩形表示
     * 得到的是目标圆形范围的外接矩形
     * @param dis
     * @param longitude
     * @param latitude
     */
    public static double[] getRange(double dis, double longitude, double latitude) {
        try {
            //地球半径千米
            double r = 6371;
            double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
            //角度转为弧度
            dlng = dlng * 180 / Math.PI;
            double dlat = dis / r;
            dlat = dlat * 180 / Math.PI;
            double minLat = latitude - dlat;
            double maxLat = latitude + dlat;
            double minLng = longitude - dlng;
            double maxLng = longitude + dlng;
            return new double[]{minLng, maxLng, minLat, maxLat};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        getRange(100, 118.781696,32.050303);
    }
}
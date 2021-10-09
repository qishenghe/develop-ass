package com.qishenghe.developassistant.geoscience;

/**
 * 经纬度坐标系枚举
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public enum LngLatCoordinateEnum {

    /**
     * WGS-84【GPS坐标】
     */
    COORDINATE_GPS("WGS-84", "WGS-84"),

    /**
     * GCJ-02【中国坐标偏移标准，Google Map、高德、腾讯使用】
     */
    COORDINATE_GCJ02("GCJ-02", "GCJ-02"),

    /**
     * BD-09【百度坐标偏移标准，Baidu Map使用】
     */
    COORDINATE_BD09("BD-09", "BD-09");

    /**
     * 坐标系名称
     */
    private final String name;

    /**
     * 坐标系编码
     */
    private final String code;

    /**
     * 构造函数
     *
     * @param name 坐标系名称
     * @param code 坐标系编码
     */
    LngLatCoordinateEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Get Name
     *
     * @return 坐标系名称
     */
    public String getName() {
        return name;
    }

    /**
     * Get Code
     *
     * @return 坐标系编码
     */
    public String getCode() {
        return code;
    }

}

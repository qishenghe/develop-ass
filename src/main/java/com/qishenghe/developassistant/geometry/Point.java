package com.qishenghe.developassistant.geometry;

import lombok.Data;

import java.io.Serializable;

/**
 * 点
 * 注：通过维度数组存放各个维度的值，预设API支持二维点快速构建与查询
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
@Data
public class Point implements Serializable {

    /**
     * 序列化参数
     */
    private static final long serialVersionUID = 4716135120947817019L;

    /**
     * 二维坐标点X idx
     */
    private static final int DOUBLE_DIMENSION_X_IDX = 0;

    /**
     * 二维坐标点Y idx
     */
    private static final int DOUBLE_DIMENSION_Y_IDX = 1;

    /**
     * 维度数组
     * 注：【idx = 1，value = X】【idx = 2，value = Y】
     * 例：二维坐标系点坐标(1, 5) : 维度数组[1, 5]
     */
    private double[] dimensionArr;

    /**
     * 构造函数【无参】
     */
    public Point() {
        initArr(2);
    }

    /**
     * 构造函数【指定维度容器初始化大小】
     *
     * @param initNum 初始化维度数组容器长度
     */
    public Point(int initNum) {
        initArr(initNum);
    }

    /**
     * 构造函数【二维点】
     *
     * @param x x
     * @param y y
     */
    public Point(double x, double y) {
        initArr(2);
        this.dimensionArr[DOUBLE_DIMENSION_X_IDX] = x;
        this.dimensionArr[DOUBLE_DIMENSION_Y_IDX] = y;
    }

    /**
     * 构造函数【多维点】
     *
     * @param dimensionArr 维度数组
     */
    public Point(double... dimensionArr) {
        initArr(dimensionArr.length);
        System.arraycopy(dimensionArr, 0, this.dimensionArr, 0, dimensionArr.length);
    }

    /**
     * 数组容器初始化
     *
     * @param initNum 初始化容器大小
     */
    private void initArr(int initNum) {
        this.dimensionArr = new double[initNum];
    }

    /**
     * 获取第 N 维度 Value
     *
     * @param dimensionNum 维度序号（从 0 开始）
     * @return value
     * @author qishenghe
     * @date 2021/9/30 11:11
     * @change 2021/9/30 11:11 by qishenghe for init
     * @since 1.0.0
     */
    public double getDimensionValue(int dimensionNum) {
        return this.dimensionArr[dimensionNum];
    }

    /**
     * 设置第 N 维度 Value
     *
     * @param dimensionNum 维度序号（从 0 开始）
     * @return value
     * @author qishenghe
     * @date 2021/9/30 11:11
     * @change 2021/9/30 11:11 by qishenghe for init
     * @since 1.0.0
     */
    public boolean setDimensionValue(int dimensionNum, double value) {
        if (dimensionNum >= this.dimensionArr.length) {
            throw new RuntimeException(Point.class.getName() + " : cause : " + "维度数组越界，暂不支持自动扩容");
        } else {
            this.dimensionArr[dimensionNum] = value;
            return true;
        }
    }

    /**
     * 获取X
     *
     * @return X
     */
    public double getX() {
        return getDimensionValue(DOUBLE_DIMENSION_X_IDX);
    }

    /**
     * 获取Y
     *
     * @return Y
     */
    public double getY() {
        return getDimensionValue(DOUBLE_DIMENSION_Y_IDX);
    }

    /**
     * 设置X
     *
     * @param value value
     * @return success
     */
    public boolean setX(double value) {
        return setDimensionValue(DOUBLE_DIMENSION_X_IDX, value);
    }

    /**
     * 设置Y
     *
     * @param value value
     * @return success
     */
    public boolean setY(double value) {
        return setDimensionValue(DOUBLE_DIMENSION_Y_IDX, value);
    }

}

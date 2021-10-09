package com.qishenghe.developassistant.mathematics;

import java.util.Random;

/**
 * 随机工具
 * 注：所生成随机数遵循左闭右开原则
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public class RandomUtil {

    /**
     * 生成随机数（double）
     *
     * @param min  最小值
     * @param max  最大值
     * @param seed 种子
     * @return 随机数
     * @author qishenghe
     * @date 2021/9/29 15:33
     * @change 2021/9/29 15:33 by qishenghe for init
     * @since 1.0.0
     */
    public static double getRandomNumberDouble(double min, double max, long seed) {
        Random random = new Random(seed);
        return (random.nextDouble() * (max - min)) + min;
    }

    /**
     * 【重载】生成随机数（double）
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static double getRandomNumberDouble(double min, double max) {
        return getRandomNumberDouble(min, max, System.nanoTime());
    }

    /**
     * 【重载】生成随机数（double）
     *
     * @param min 最小值（String）
     * @param max 最大值（String）
     * @return 随机数
     */
    public static double getRandomNumberDouble(String min, String max) {
        return getRandomNumberDouble(Double.parseDouble(min), Double.parseDouble(max));
    }

    /**
     * 生成随机数（int）
     *
     * @param min  最小值
     * @param max  最大值
     * @param seed 种子
     * @return 随机数
     * @author qishenghe
     * @date 2021/9/29 15:39
     * @change 2021/9/29 15:39 by qishenghe for init
     * @since 1.0.0
     */
    public static int getRandomNumberInteger(int min, int max, long seed) {
        Random random = new Random(seed);
        return (int) ((random.nextDouble() * (max - min)) + min);
    }

    /**
     * 【重载】生成随机数（int）
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int getRandomNumberInteger(int min, int max) {
        return getRandomNumberInteger(min, max, System.nanoTime());
    }

    /**
     * 【重载】生成随机数（int）
     *
     * @param min 最小值（String）
     * @param max 最大值（String）
     * @return 随机数
     */
    public static int getRandomNumberInteger(String min, String max) {
        return getRandomNumberInteger(Integer.parseInt(min), Integer.parseInt(max));
    }

}

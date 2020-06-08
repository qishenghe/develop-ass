package cn.qishenghe.developassistant.util.mathutil;

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtil {

    /**
     * 随机数种子
     */
    private static long seed = 0L;

    /**
     * 生成优化伪随机数
     */
    public static double getRandomNumber (double min, double max) {
        seed += (System.currentTimeMillis() % 1000000000) * 100;
        if ((seed + "").length() > 15) {
            seed = 0;
        }
        Random random = new Random();
        random.setSeed(seed);
        double s = random.nextDouble();
        return (s * (max - min)) + min;
    }

    /**
     * 获取随机数 (double)
     * @param min   最小值
     * @param max   最大值
     * @param n     保留位数
     */
    public static String getRandomNumber (double min, double max, int n) {
        return MathUtil.mathKeepValidNum(getRandomNumber(min, max), n);
    }

    /**
     * 获取随机数 (String)
     */
    public static String getRandomNumber (String min, String max, int n) {
        return getRandomNumber(Double.parseDouble(min), Double.parseDouble(max), n);
    }

    public static void main(String[] args) {

        double n = getRandomNumber(0, 2);
        int intN = (int) n;
        System.out.println(n);
        System.out.println(intN);
    }

}

package cn.qishenghe.developassistant.util.mathutil;

import java.text.DecimalFormat;

/**
 * 数学计算工具
 */
public class MathUtil {

    /**
     * 等差数列求和（高斯求和）
     * startNum : 首项        endNum : 末项         tol : 公差
     */
    public static int mathGaussSummation (int startNum, int endNum, int tol) {
        return ((startNum + endNum) * (((endNum - startNum) / tol) + 1)) / 2;
}

    /**
     * 保留有效数字
     * num : 数值             n : 小数点后保留位数
     */
    public static String mathKeepValidNum (String num, int n) {
        return mathKeepValidNum(Double.parseDouble(num), n);
    }

    public static String mathKeepValidNum (double num, int n) {
        StringBuilder par = new StringBuilder(n > 0 ? "0." : "0");
        for (int x = 0; x < n; x ++) {
            par.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(par.toString());
        return decimalFormat.format(num);
    }

}

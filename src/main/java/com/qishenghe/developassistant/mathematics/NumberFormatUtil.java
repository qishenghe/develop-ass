package com.qishenghe.developassistant.mathematics;

import java.text.DecimalFormat;

/**
 * 数字格式化工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public class NumberFormatUtil {

    /**
     * 【重载】保留有效数字
     *
     * @param num 数字
     * @param n   保留小数位个数
     * @return 处理结果
     */
    public static String mathKeepValidNum(String num, int n) {
        return mathKeepValidNum(Double.parseDouble(num), n);
    }

    /**
     * 保留有效数字
     *
     * @param num 数字
     * @param n   保留小数位个数
     * @return 处理结果
     * @author qishenghe
     * @date 2021/9/29 15:51
     * @change 2021/9/29 15:51 by qishenghe for init
     * @since 1.0.0
     */
    public static String mathKeepValidNum(double num, int n) {
        StringBuilder par = new StringBuilder(n > 0 ? "0." : "0");
        for (int x = 0; x < n; x++) {
            par.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(par.toString());
        return decimalFormat.format(num);
    }

}

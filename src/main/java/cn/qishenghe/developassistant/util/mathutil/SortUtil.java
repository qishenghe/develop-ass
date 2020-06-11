package cn.qishenghe.developassistant.util.mathutil;

public class SortUtil {

    // 冒泡排序核心（flag : true[由小到大]     false[由大到小]）
    public static double[] bubbleSortCoreSl (double[] nums, boolean flag) {

        for (int i = 0; i < nums.length; i ++) {
            for (int j = nums.length - 1; j > i; j --) {
                if (flag) {
                    if (nums[j] < nums[j - 1]) {
                        // small to large
                        // change
                        double tmp = nums[j];
                        nums[j] = nums[j - 1];
                        nums[j - 1] = tmp;
                    }
                } else {
                    if (nums[j] > nums[j - 1]) {
                        // large to small
                        // change
                        double tmp = nums[j];
                        nums[j] = nums[j - 1];
                        nums[j - 1] = tmp;
                    }
                }
            }
        }
        return nums;
    }



}

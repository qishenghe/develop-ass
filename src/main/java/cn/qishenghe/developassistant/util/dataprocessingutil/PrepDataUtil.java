package cn.qishenghe.developassistant.util.dataprocessingutil;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 数据预处理工具
 */
public class PrepDataUtil {

    /**
     * 将 Map 中指定 key 的值拼成一行
     * map : 数据集    seq : 列分隔符      keys : 键数组
     */
    public static String getMapToLine (Map map, String seq, String... keys) {

        StringBuilder line = new StringBuilder();
        for (String singleKey : keys) {
            line.append(map.get(singleKey).toString()).append(seq);
        }
        return line.substring(0, line.lastIndexOf(seq));
    }

    /**
     * 将 Json 中指定 key 的值拼成一行
     * map : 数据集    seq : 列分隔符      keys : 键数组
     */
    public static String getJsonToLine (JSONObject jsonObject, String seq, String... keys) {

        StringBuilder line = new StringBuilder();
        for (String singleKey : keys) {
            line.append(jsonObject.get(singleKey).toString()).append(seq);
        }
        return line.substring(0, line.lastIndexOf(seq));
    }

}

package cn.qishenghe.developassistant.util.dataprocessingutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Json 转换工具
 */
public class JsonUtil {

    /**
     * Json 文本转为 Json 对象
     */
    public static JSONObject jsonStrToJsonObject (String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }

    /**
     * Json 文本转为 Json 数组
     */
    public static JSONArray jsonStrToJsonArray (String jsonArr) {
        return JSONArray.parseArray(jsonArr);
    }

    /**
     * Json 数组转为 Json 链表
     */
    public static List<JSONObject> jsonArrayToJsonList (JSONArray jsonArray) {
        return jsonArray.toJavaList(JSONObject.class);
    }

    /**
     * 获取 Json 中的目标 Key 的值，汇成一行，用 seq 作为分隔符
     * 注：行中值的顺序与 colNames 中的顺序相同
     */
    public static String getLineOfJson (JSONObject jsonObject, String seq, String... colNames) {

        StringBuilder line = new StringBuilder();
        for (String singleColName : colNames) {
            line.append(jsonObject.get(singleColName).toString()).append(seq);
        }
        return line.toString().substring(0, line.lastIndexOf(seq));
    }

}

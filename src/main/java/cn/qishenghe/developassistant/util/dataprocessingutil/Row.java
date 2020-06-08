package cn.qishenghe.developassistant.util.dataprocessingutil;

import java.util.List;

public class Row {

    /**
     * 行列值数组
     */
    private String[] rowArr;

    /**
     * 构造函数 : 根据列数初始化（各个列值为空）
     */
    public Row (int cols) {
        this.rowArr = new String[cols];
    }

    /**
     * 构造函数 : 根据数组初始化
     */
    public Row (String[] arr) {
        this.rowArr = arr;
    }

    /**
     * 构造函数 : 根据链表初始化
     */
    public Row (List<String> list) {
        this.rowArr = (String[]) list.toArray();
    }

    /**
     * 行字符串转换为行对象，默认转换规则将"|"作为列分隔符
     */
    public static Row createRow (String line) {
        return createRow(line, "\\|");
    }

    /**
     * 行字符串转换为行对象，列分隔符自定义
     */
    public static Row createRow (String line, String seq) {
        return new Row(line.split(seq, -1));
    }

    /**
     * 设置行中第 N 列的值
     */
    public void set (int n, String value) {
        rowArr[n] = value;
    }

    /**
     * 获得行中第 N 列的值
     */
    public String get (int n) {
        return rowArr[n];
    }

    /**
     * 获取列数
     */
    public int size () {
        return rowArr.length;
    }

    /**
     * 行对象转为行字符串，默认分隔符"|"
     */
    public String toString () {
        return toString("|");
    }

    /**
     * 行对象转为行字符串，分隔符自定义
     */
    public String toString (String seq) {
        StringBuilder row = new StringBuilder();
        for (String singleCol : rowArr) {
            row.append(singleCol).append(seq);
        }
        return row.substring(0, row.lastIndexOf(seq));
    }

    /**
     * 行尾增加一个或多个值
     */
    public void tailAppend (String... strings) {
        String[] newRowArr = new String[this.rowArr.length + strings.length];
        System.arraycopy(rowArr, 0, newRowArr, 0, rowArr.length);
        System.arraycopy(strings, 0, newRowArr, rowArr.length, strings.length);
        rowArr = newRowArr;
    }


}

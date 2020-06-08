package cn.qishenghe.developassistant.util.fileutil;

import java.io.InputStream;
import java.util.List;

public interface FileUtil {

    // 登录
    boolean login();

    // 注销
    boolean logout();

    // 获取文件列表
    List fileList(String path);

    // 读文件（行）
    List<String> readFileLine(String path);

    // 写文件（行）
    boolean writeFileLine(List<String> list, String path);

    // 读文件（流）
    InputStream readFileStream(String path);

    // 写文件（流）
    boolean writeFileStream(InputStream stream, String path);

    // 读文件（字节数组）
    byte[] readFileBytes(String path);

    // 写文件（字节数组）
    boolean writeFileBytes(byte[] bytes, String path);

    // 创建文件夹
    boolean mkdir(String path, boolean recursion);

    // 删除文件夹（recursion = true：递归且强制删除）
    boolean rmdir(String path, boolean recursion);

    // 删除文件
    boolean rmfile(String path);

    // 判断是否是文件夹
    boolean isDir(String path);

    // 判断是否是文件
    boolean isFile(String path);

    // 判断是否存在
    boolean isExist(String path);

}

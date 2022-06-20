package com.qishenghe.developassistant.io;

import lombok.Data;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * IO工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
@Data
public class FileUtil {

    /**
     * 默认编码字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 编码字符集
     */
    private Charset charset;

    /**
     * 无参构造函数
     * 注：默认编码字符集【UTF-8】
     */
    public FileUtil() {
        this.charset = Charset.forName(DEFAULT_CHARSET_NAME);
    }

    /**
     * 构造函数
     *
     * @param charset 编码字符集
     */
    public FileUtil(Charset charset) {
        this.charset = charset;
    }

    /**
     * 【读】行
     *
     * @param path path
     * @return 行
     */
    public List<String> readFileList(String path) {
        List<String> lineList = new ArrayList<>();
        try (InputStreamReader isReader = new InputStreamReader(new FileInputStream(path), this.charset); BufferedReader br = new BufferedReader(isReader)) {
            String tempString;
            while ((tempString = br.readLine()) != null && !"".equals(tempString)) {
                lineList.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(FileUtil.class.getName() + " : cause : " + e.getMessage());
        }
        return lineList;
    }

    /**
     * 【读】流
     *
     * @param path path
     * @return 流
     */
    public InputStream readFileStream(String path) {

        try {
            File file = new File(path);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(FileUtil.class.getName() + " : cause : " + e.getMessage());
        }
    }

    /**
     * 【读】字节
     *
     * @param path path
     * @return 字节
     */
    public byte[] readFileBytes(String path) {

        File file = new File(path);
        byte[] bytes = null;

        FileInputStream fis = null;
        ByteArrayOutputStream baoStream = null;
        try {
            //读取输入的文件====文件输入流
            fis = new FileInputStream(file);
            //字节数组输出流  在内存中创建一个字节数组缓冲区，所有输出流的数据都会保存在字符数组缓冲区中
            baoStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            //将文件读入到字节数组中
            while ((len = fis.read(buffer)) != -1) {
                // 将指定字节数组中从偏移量 off 开始的 len 个字节写入此字节数组输出流。
                baoStream.write(buffer, 0, len);
            }
            //当前输出流的拷贝  拷贝到指定的字节数组中
            bytes = baoStream.toByteArray();

            fis.close();
            baoStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(FileUtil.class.getName() + " : cause : " + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

                if (baoStream != null) {
                    baoStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;

    }

    /**
     * 【写】行
     *
     * @param list 行
     * @param path path
     * @return success
     */
    private boolean writeFileList(List<String> list, String path) {
        boolean flag = false;
        if (list == null || list.size() == 0) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                flag = file.delete();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (String point : list) {
                bw.write(point + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(FileUtil.class.getName() + " : cause : " + e.getMessage());
        }
        return flag;
    }

    /**
     * 【写】流
     *
     * @param stream 流
     * @param path   path
     * @return success
     */
    public boolean writeFileStream(InputStream stream, String path) {

        boolean flag;
        File file = new File(path);
        OutputStream out = null;
        try {
            // 指定要写入文件的缓冲输出字节流
            out = new FileOutputStream(file);
            // 用来存储每次读取到的字节数组
            byte[] bb = new byte[1024];
            int n;// 每次读取到的字节数组的长度
            while ((n = stream.read(bb)) != -1) {
                // 写入到输出流
                out.write(bb, 0, n);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 【写】字节数组
     *
     * @param bytes 字节
     * @param path  path
     * @return success
     */
    public boolean writeFileBytes(byte[] bytes, String path) {

        boolean flag;
        File file = new File(path);
        OutputStream out = null;
        try {
            // 指定要写入文件的缓冲输出字节流
            out = new FileOutputStream(file);
            // int n;// 每次读取到的字节数组的长度
            out.write(bytes);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}

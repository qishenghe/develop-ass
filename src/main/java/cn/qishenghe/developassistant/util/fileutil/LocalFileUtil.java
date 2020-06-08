package cn.qishenghe.developassistant.util.fileutil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 文件读写工具
 */
public class LocalFileUtil implements FileUtil {

    /**
     * 登录
     */
    @Override
    public boolean login() {
        System.out.println("本地文件操作，无需登录");
        return false;
    }

    /**
     * 注销
     */
    @Override
    public boolean logout() {
        System.out.println("本地文件操作，无法注销");
        return false;
    }

    /**
     * 获取文件列表
     */
    @Override
    public List fileList(String path) {
        File file = new File(path);
        return Arrays.asList(Objects.requireNonNull(file.listFiles()));
    }

    /**
     * 读取文（行）
     */
    private List<String> readFileToList(String path, String charsetName) {
        List<String> lineList = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader isReader = null;
        try {
            isReader = new InputStreamReader(new FileInputStream(path), charsetName);
            br = new BufferedReader(isReader);
            String tempString;
            while ((tempString = br.readLine()) != null && !tempString.equals("")) {
                lineList.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {
                }
            }
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return lineList;
    }

    /**
     * 写文件（行）
     */
    private boolean writeFileFromList (List<String> list, String path) {
        boolean flag = false;
        if (list.size() == 0) {
            return false;
        }
        try {
            File file=new File(path);
            if (file.exists())
                flag = file.delete();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (String point : list) {
                bw.write(point + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 读取文（行）
     */
    @Override
    public List<String> readFileLine(String path) {
        return readFileToList(path, "utf-8");
    }

    /**
     * 写文件（行）
     */
    @Override
    public boolean writeFileLine(List<String> list, String path) {
        return writeFileFromList(list, path);
    }

    /**
     * 读文件（流）
     */
    @Override
    public InputStream readFileStream(String path) {

        try {
            File file = new File(path);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写文件（流）
     */
    @Override
    public boolean writeFileStream(InputStream stream, String path) {

        boolean flag;
        File file = new File(path);
        OutputStream out = null;
        try {
            // 指定要写入文件的缓冲输出字节流
            out = new FileOutputStream(file);
            byte[] bb = new byte[1024];// 用来存储每次读取到的字节数组
            int n;// 每次读取到的字节数组的长度
            while ((n = stream.read(bb)) != -1) {
                out.write(bb, 0, n);// 写入到输出流
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
     * 读文件（字节数组）
     */
    @Override
    public byte[] readFileBytes(String path) {

        File file = new File(path);
        byte[] imagebs = null;
        try {
            //读取输入的文件====文件输入流
            FileInputStream fis = new FileInputStream(file);
            //字节数组输出流  在内存中创建一个字节数组缓冲区，所有输出流的数据都会保存在字符数组缓冲区中
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            //将文件读入到字节数组中
            while ((len = fis.read(buffer)) != -1) {
                // 将指定字节数组中从偏移量 off 开始的 len 个字节写入此字节数组输出流。
                baos.write(buffer, 0, len);
            }
            imagebs = baos.toByteArray();//当前输出流的拷贝  拷贝到指定的字节数组中

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagebs;

    }

    /**
     * 读文件（字节数组）
     */
    @Override
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

    /**
     * 创建文件夹（递归可选）
     */
    @Override
    public boolean mkdir(String path, boolean recursion) {

        boolean flag;
        File file = new File(path);
        if (recursion) {
            flag = file.mkdirs();
        } else {
            flag = file.mkdir();
        }
        return flag;
    }

    /**
     * 删除文件夹（递归可选）（禁用）
     */
    @Override
    public boolean rmdir(String path, boolean recursion) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 删除文件
     */
    @Override
    public boolean rmfile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 是文件夹
     */
    @Override
    public boolean isDir(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * 是文件
     */
    @Override
    public boolean isFile(String path) {
        File file = new File(path);
        return file.isFile();
    }

    /**
     * 路径存在
     */
    @Override
    public boolean isExist(String path) {
        File file = new File(path);
        return file.exists();
    }


}

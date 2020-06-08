package cn.qishenghe.developassistant.util.fileutil;

import cn.qishenghe.developassistant.util.dataprocessingutil.StreamUtil;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 类说明 sftp工具类
 */

public class SftpUtil implements FileUtil {

    private ChannelSftp sftp;
    private Session session;
    /** SFTP 登录用户名*/
    private String username;
    /** SFTP 登录密码*/
    private String password;
    /** 私钥 */
    private String privateKey;
    /** SFTP 服务器地址IP地址*/
    private String host;
    /** SFTP 端口*/
    private int port;

    /**
     * 空构造函数
     */
    public SftpUtil () {}

    /**
     * 构造基于密码认证的sftp对象
     */
    public SftpUtil (String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SftpUtil (String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    /**
     * 登录
     */
    @Override
    public boolean login () {
        boolean flag;
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            flag = true;
        } catch (JSchException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 注销
     */
    @Override
    public boolean logout () {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
        return true;
    }

    /**
     * 获取文件列表（LsEntry）
     */
    @Override
    public List fileList(String path) {

        try {
            return sftp.ls(path);
        } catch (SftpException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读文件（行）
     */
    @Override
    public List<String> readFileLine(String path) {

        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(sftp.get(path)));
            list = reader.lines().collect(Collectors.toList());
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 写文件（行）
     */
    @Override
    public boolean writeFileLine(List<String> list, String path) {

        boolean flag;
        try {
            sftp.put(StreamUtil.listToInputStream(list), path);
            flag = true;
        } catch (SftpException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 读文件（流）
     */
    @Override
    public InputStream readFileStream(String path) {
        try {
            return sftp.get(path);
        } catch (SftpException e) {
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
        try {
            sftp.put(stream, path);
            flag = true;
        } catch (SftpException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 读文件（字节数组）
     */
    @Override
    public byte[] readFileBytes(String path) {

        try {
            InputStream inputStream = sftp.get(path);
            return StreamUtil.inputStreamToBytes(inputStream);
        } catch (SftpException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * 写文件（字节数组）
     */
    @Override
    public boolean writeFileBytes(byte[] bytes, String path) {

        boolean flag;
        try {
            sftp.put(StreamUtil.bytesToInputStream(bytes), path);
            flag = true;
        } catch (SftpException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 创建文件夹
     */
    @Override
    public boolean mkdir(String path, boolean recursion) {

        if (!recursion) {
            // 非递归创建
            boolean flag;
            try {
                sftp.mkdir(path);
                flag = true;
            } catch (SftpException e) {
                e.printStackTrace();
                flag = false;
            }
            return flag;
        } else {
            // 递归创建
            boolean flag;
            try {
                mkdirs(path);
                flag = true;
            } catch (SftpException e) {
                e.printStackTrace();
                flag = false;
            }
            return flag;
        }

    }

    // 递归创建文件夹
    private void mkdirs (String path) throws SftpException {
        if (!isExist(getFatherPath(path))) {
            mkdirs(getFatherPath(path));
        }
        sftp.mkdir(path);
    }

    // 获取当前文件夹的上级文件夹
    private static String getFatherPath (String path) {
        // /tmp/test/qishengh/ --> /tmp/test
        return path.substring(0, path.lastIndexOf("/"));
    }

    /**
     * 删除文件夹（递归删除禁用）
     */
    @Override
    public boolean rmdir(String path, boolean recursion) {

        // false
        boolean flag;
        try {
            sftp.rmdir(path);
            flag = true;
        } catch (SftpException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 删除文件
     */
    @Override
    public boolean rmfile(String path) {

        boolean flag;
        try {
            sftp.rm(path);
            flag = true;
        } catch (SftpException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 是文件夹
     */
    @Override
    public boolean isDir(String path) {

        try {
            sftp.ls(path);
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 是文件
     */
    @Override
    public boolean isFile(String path) {

        if (isDir(path)) {
            // 是文件夹
            return false;
        } else {
            try {
                sftp.get(path);
                // 存在，是文件
                return true;
            } catch (SftpException e) {
                // 不存在
                return false;
            }
        }
    }

    /**
     * 存在
     */
    @Override
    public boolean isExist(String path) {

        return isDir(path) || isFile(path);
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径
     * @param directory  上传到该目录
     * @param sftpFileName  sftp端文件名
     * @param input   输入流
     */
    public void upload(String basePath,String directory, String sftpFileName, InputStream input) throws SftpException{
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String [] dirs=directory.split("/");
            String tempPath=basePath;
            for(String dir:dirs){
                if(null== dir || "".equals(dir)) continue;
                tempPath+="/"+dir;
                try{
                    sftp.cd(tempPath);
                }catch(SftpException ex){
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        sftp.put(input, sftpFileName);  //上传文件
    }

    /**
     * 下载文件。
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = IOUtils.toByteArray(is);
        return fileData;
    }

    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException{
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    // 上传文件测试
    public static void main(String[] args) throws SftpException, IOException {
        /*SftpUtil sftp = new SftpUtil("用户名", "密码", "ip地址", 22);
        sftp.login();
        File file = new File("D:\\图片\\t0124dd095ceb042322.jpg");
        InputStream is = new FileInputStream(file);
        sftp.upload("基础路径","文件路径", "test_sftp.jpg", is);
        sftp.logout();*/

    }

}

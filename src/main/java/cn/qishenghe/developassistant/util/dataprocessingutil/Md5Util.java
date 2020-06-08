package cn.qishenghe.developassistant.util.dataprocessingutil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String getMd5 (String str) {
        return getMd5(str, "utf-8", 16);
    }

    // 默认编码 : utf-8     默认进制 : 16
    public static String getMd5 (String str, String charset, int base) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes(charset));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        assert digest != null;
        return new BigInteger(1, digest).toString(base);
    }

}

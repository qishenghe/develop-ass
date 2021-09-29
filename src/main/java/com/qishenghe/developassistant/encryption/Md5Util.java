package com.qishenghe.developassistant.encryption;

import com.qishenghe.developassistant.exception.DevelopAssException;
import lombok.Data;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe@bonc.com.cn for init
 */
@Data
public class Md5Util {

    /**
     * 默认加密方式
     */
    private static final String DEFAULT_MESSAGE_DIGEST_NAME = "md5";

    /**
     * 默认编码字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 默认进制
     */
    private static final int DEFAULT_BASE_NUM = 16;

    /**
     * 编码字符集
     */
    private Charset charset;

    /**
     * 进制
     */
    private int baseNum;

    /**
     * 无参构造函数
     * 注：默认情况编码字符集UTF-8，16进制
     */
    public Md5Util() {
        this.charset = Charset.forName(DEFAULT_CHARSET_NAME);
        this.baseNum = DEFAULT_BASE_NUM;
    }

    /**
     * 构造函数
     *
     * @param charset 编码字符集
     * @param baseNum 进制
     */
    public Md5Util(Charset charset, int baseNum) {
        this.charset = charset;
        this.baseNum = baseNum;
    }

    /**
     * 【重载】加密
     * 注：默认情况编码字符集UTF-8，16进制
     *
     * @param str 待加密字符串
     * @return 加密结果
     * @author qishenghe
     * @date 2021/9/29 18:45
     * @change 2021/9/29 18:45 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public String encryption(String str) {
        return encryption(str, this.charset, this.baseNum);
    }

    /**
     * 加密
     *
     * @param str     待加密字符串
     * @param charset 编码字符集
     * @param base    进制
     * @return 加密结果
     * @author qishenghe
     * @date 2021/9/29 18:41
     * @change 2021/9/29 18:41 by qishenghe@bonc.com.cn for init
     * @since 1.0.0
     */
    public String encryption(String str, Charset charset, int base) {
        byte[] digest;
        try {
            MessageDigest md5 = MessageDigest.getInstance(DEFAULT_MESSAGE_DIGEST_NAME);
            digest = md5.digest(str.getBytes(charset));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new DevelopAssException(Md5Util.class.getName() + " : cause : " + e.getMessage());
        }
        //16是表示转换为16进制数
        assert digest != null;
        return new BigInteger(1, digest).toString(base);
    }

}

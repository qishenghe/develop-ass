package cn.qishenghe.developassistant.util.dataprocessingutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StreamUtil {

    private static List<Byte> arrToList (byte[] bytes) {
        List<Byte> list = new ArrayList<>();
        for (byte singleByte : bytes) {
            list.add(singleByte);
        }
        return list;
    }

    private static byte[] listToArr(List<Byte> list) {

        byte[] bytes = new byte[list.size()];
        int n = 0;
        for (Byte singleByte : list) {
            bytes[n ++] = singleByte;
        }
        return bytes;
    }

    public static InputStream listToInputStream (List<String> list) {
        List<Byte> byteList = new ArrayList<>();
        for (String line : list) {
            byteList.addAll(arrToList(line.getBytes()));
            // 換行
            byteList.addAll(arrToList("\r\n".getBytes()));
        }
        byte[] bytes = listToArr(byteList);

        return new ByteArrayInputStream(bytes);
    }

    /**
     * Byte数组 --> InputStream
     */
    public static InputStream bytesToInputStream (byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * InputStream --> Byte数组
     */
    public static byte[] inputStreamToBytes (InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                byteArrayOutputStream.write(buff, 0, rc);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

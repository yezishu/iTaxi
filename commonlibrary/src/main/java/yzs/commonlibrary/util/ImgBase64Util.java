package yzs.commonlibrary.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Des：获取base64
 * create by Zishu.Ye on 2017/11/8  19:50
 */
public class ImgBase64Util {

    /**
     * @param filePath  f
     * @return 获取base64
     */
    public static String getBase64Str(String filePath){
        byte[] img= getBytesByFile(new File(filePath));
        return Base64.encodeToString(img,Base64.NO_WRAP);
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

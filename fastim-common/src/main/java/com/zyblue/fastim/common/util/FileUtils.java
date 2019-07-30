package com.zyblue.fastim.common.util;

import java.io.*;

/**
 * @创建人：shuzhikang
 * @创建时间：2018/5/18
 * @描述：
 */
public class FileUtils {

    /**
     * 获取文件的行数（每行的字符较多时使用）
     * @param file
     * @return
     */
    public static long getFileLineCount(File file) {
        if(file == null || !file.exists()){
            return -1;
        }

        long cnt = 0;
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(file));
            @SuppressWarnings("unused")
            String lineRead = "";
            while ((lineRead = reader.readLine()) != null) {
            }
            cnt = reader.getLineNumber();
        } catch (Exception ex) {
            cnt = -1;
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    /**
     * 读取文件的行数（每行的字符较少时使用）
     * @param file
     * @return
     */
    public static long getFileLineCounts(File file) {
        if(file == null || !file.exists()){
            return 0;
        }

        long cnt = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] c = new byte[1024];
            int readChars = 0;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++cnt;
                    }
                }
            }
        } catch (Exception ex) {
            cnt = -1;
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }
}

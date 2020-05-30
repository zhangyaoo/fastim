package com.zyblue.fastim.common.algorithm;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class HttpTest {
    public static void main(String[] args) {

        while (true) {
            String sr = HttpTest
                    .sendPost(
                            "https://nikeduo3.xyz/invite?id=5e79836c560973116f0e5bd8",
                            "&id=5e79836c560973116f0e5bd8");//a=2222
            System.out.println(sr);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendPost(String url, String param) {
        //PrintWriter out = null;
        //BufferedReader in = null;
        //String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 随机生成ip
            String ip = randIP();
            System.out.println("ip" + ip);
            //conn.setRequestProperty("X-Forwarded-For", ip);
            conn.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);
            conn.setRequestProperty("HTTP_CLIENT_IP", ip);
            conn.setRequestProperty("REMOTE_ADDR", ip);
            conn.setRequestProperty("Host", "");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Length", "17");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Origin", "ORIGIN");
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty(
                    "Referer",
                    "REFERER");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language",
                    "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,pt;q=0.2");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(8000);
            InputStream in = conn.getInputStream();
            InputStream buffer = new BufferedInputStream(in);
            //将InputStream串链到一个Reader
            Reader reader = new InputStreamReader(buffer);
            int c;
            while ((c = reader.read())!= -1) {
                System.out.print((char)c);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {

        }
        return "result:";
    }

    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

}
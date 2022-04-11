package com.zyblue.fastim.common.mytest.algorithm.enterprise;

/**
 * @author will.zhang
 * @date 2020/6/5 11:19
 *
 * 大宇无限
 * 描述：判断从一个字符串中是否能抓取出helloworld，可以不连续但是一定要保持顺序。
 * 如hhhhelllllllo wwwwwwworld 但不能是helolllllworld。
 *
 * 目前想到的就是利用栈来解决
 */
public class Ques1 {

    /*public static void main(String[] args) {
        System.out.println(isHelloWorld("heloworld","hhhhelllllllo wwwwwwworld"));
    }*/

    public static boolean isHelloWorld(String helloWorld, String text) {
        text = text.replaceAll(" ", "");

        boolean result = true;
        int currentIndex = 0;
        for (int i = 0; i < helloWorld.length(); i++) {
            if(text.charAt(currentIndex) != helloWorld.charAt(i)){
                result = false;
            }

            char c = helloWorld.charAt(i);

            while (currentIndex < text.length()){
                if(text.charAt(currentIndex + 1) != c && text.charAt(currentIndex) == c){
                    break;
                }else {
                    currentIndex++;
                }
            }
        }

        return result;
    }
}

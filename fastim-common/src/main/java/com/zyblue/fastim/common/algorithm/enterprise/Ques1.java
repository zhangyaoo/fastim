package com.zyblue.fastim.common.algorithm.enterprise;

/**
 * @author will.zhang
 * @date 2020/6/5 11:19
 *
 * 大宇无限面试真题
 * 描述：判断从一个字符串中是否能抓取出helloworld，可以不连续但是一定要保持顺序。
 * 如hhhhelllllllo wwwwwwworld 但不能是helolllllworld。
 * 实现程序前请尽可能的描述清楚思路
 */
public class Ques1 {

    public static void main(String[] args) {
        System.out.println(isHelloWorld("hhhhelllllllo wwwwwwworld"));
    }

    public static boolean isHelloWorld(String text) {
        boolean result = false;
        // TODO 有ll这个字符串导致异常
        String original = "helloworld";

        int index = 0;
        while (index < text.length()){
            for(int orgIndex = 0; orgIndex < original.length(); orgIndex++){
                char current = text.charAt(index);
                if(original.charAt(orgIndex) == current){
                    //TODO 溢出
                    while (Character.isSpaceChar(text.charAt(index)) || text.charAt(index) == current){
                        index++;
                    }
                    if(text.charAt(index) == original.charAt(orgIndex + 1)){
                        if(orgIndex + 1 == original.length() - 1){
                            result = true;
                        }
                    }else {
                        break;
                    }
                }else {
                    index++;
                    break;
                }
            }
        }
        return result;
    }
}

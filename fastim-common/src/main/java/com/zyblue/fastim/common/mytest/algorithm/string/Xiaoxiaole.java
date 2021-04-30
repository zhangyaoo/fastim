package com.zyblue.fastim.common.mytest.algorithm.string;

/**
 * 消消乐
 * TODO
 * 例子： abbbeccddcdee ==>  a
 */
public class Xiaoxiaole {

    public static void xiao(String s){
        for (int i=0; i<s.length()&&s.length()>2; i++){
            if (s.charAt(i)==s.charAt(i+1) && s.charAt(i)==s.charAt(i+2)){
                s = s.substring(0,i) + s.substring(i+3);
                i = -1;
            }
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        xiao("accbdeeedc");
    }
}

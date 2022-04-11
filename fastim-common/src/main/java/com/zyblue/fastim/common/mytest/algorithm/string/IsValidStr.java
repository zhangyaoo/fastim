package com.zyblue.fastim.common.mytest.algorithm.string;

/**
 * @author will
 * @date 2022/2/8 16:15
 */
public class IsValidStr {
    public static boolean isValid(String s) {
        while (s.contains("{}") || s.contains("()") || s.contains("[]")){
            s = s.replace("{}", "");
            s = s.replace("[]", "");
            s = s.replace("()", "");
        }
        return "".equals(s);
    }

    public static void main(String[] args) {
        System.out.println(isValid("{}[]}[]()"));
    }
}

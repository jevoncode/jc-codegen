package com.jc.code.factory.utils;

public class CamelCaseUtils {

    public final static String UNDERSCORE = "_";

    /**
     * 将蛇形命名法转为驼峰命名法
     * 注意：首字母大写的
     * @param snake_case 蛇形命名
     * @return 返回驼峰命名
     */
    public static String parseSnakeCase(String snake_case){
        if (snake_case == null) {
            return null;
        }
        if ("".equals(snake_case.trim())) {
            return "";
        }

        StringBuilder sb = new StringBuilder(snake_case.trim());

        sb.replace(0, 1, snake_case.substring(0, 1).toUpperCase());

        while (sb.indexOf(UNDERSCORE) != -1) {
            int index = sb.indexOf(UNDERSCORE);
            sb.replace(index + 1, index + 2, sb.substring(index + 1, index + 2).toUpperCase());
            sb.replace(index, index + 1, "");
        }

        return sb.toString();

    }

    /**
     * 将蛇形命名法转为驼峰命名法
     * 注意：首字母小写的
     * @param snake_case 蛇形命名
     * @return 返回驼峰命名
     */
    public static String parseSnakeCase2LowerCase(String snake_case){
        if (snake_case == null) {
            return null;
        }
        if ("".equals(snake_case.trim())) {
            return "";
        }

        StringBuilder sb = new StringBuilder(snake_case.trim());

        sb.replace(0, 1, snake_case.substring(0, 1).toLowerCase());

        while (sb.indexOf(UNDERSCORE) != -1) {
            int index = sb.indexOf(UNDERSCORE);
            sb.replace(index + 1, index + 2, sb.substring(index + 1, index + 2).toUpperCase());
            sb.replace(index, index + 1, "");
        }

        return sb.toString();
    }
}

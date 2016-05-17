package com.zhjy.iot.mobile.oa.utils;

/**
 * 字符串的处理�?
 * Date: 12-11-22
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    /**
     * 判断是否为null或空�?
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断str1和str2是否相同
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equals(String str1, String str2) {
        return str1 == str2 || str1 != null && str1.equals(str2);
    }

    /**
     * 判断str1和str2是否相同(不区分大小写)
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }

    /**
     * 判断字符串str1是否包含字符串str2
     *
     * @param str1 源字符串
     * @param str2 指定字符�?
     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
     */
    public static boolean contains(String str1, String str2) {
        return str1 != null && str1.contains(str2);
    }

    /**
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符�?
     */
    public static String getString(String str) {
        return str == null ? "" : str;
    }
    /**
     * 在strings.xml中定义字符变量时一些字符需要转义，否则获取到的字符是错误的，常见的需要转义的字符如下：

"        (&#34; 或 &quot;) 
'         (&#39; 或 &apos;) 
&       (&#38; 或 &amp;) 
<       (&#60; 或 &lt;) 
>       (&#62; 或 &gt;) 


下面的字符在 [XML]中被定义为 空白(whitespace)字符： 
空格   (&#x0020;) 
Tab    (&#x0009;) 
回车   (&#x000D;) 
换行   (&#x000A;)
     */
    /**
     * 处理字符串录入的特殊字符
     * @param str
     * @return
     */
    public static String handString(String str){
    	str = str.replaceAll("\"", "&quot;").replaceAll("'", "&apos;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
    			.replaceAll("&", "&amp;");
    	return str;
    }
}


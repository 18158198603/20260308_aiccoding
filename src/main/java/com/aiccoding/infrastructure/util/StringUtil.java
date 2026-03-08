/**
 * 字符串工具类
 * <p>
 * 提供常用的字符串处理方法，包括判空、截取、脱敏、格式化等<br>
 * 遵循阿里巴巴Java开发规范，使用@Slf4j进行日志记录<br>
 * 线程安全，所有方法均为静态方法
 * </p>
 *
 * @author 小王
 * @version 1.0
 * @since 2026-03-08
 */
package com.aiccoding.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 提供各种字符串处理的静态方法
 */
@Slf4j
public final class StringUtil {

    /**
     * 私有构造方法，防止实例化
     */
    private StringUtil() {
        throw new AssertionError("工具类不允许实例化");
    }

    // ============================= 空值判断常量 =============================

    /**
     * 空字符串
     */
    private static final String EMPTY = "";

    /**
     * 空格字符
     */
    private static final String SPACE = " ";

    /**
     * 点号字符
     */
    private static final String DOT = ".";

    /**
     * at符号
     */
    private static final String AT = "@";

    // ============================= 正则表达式常量 =============================

    /**
     * 手机号正则（中国大陆）
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 邮箱正则
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /**
     * 身份证号正则（简单校验）
     */
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("^\\d{17}[\\dXx]|\\d{15}$");

    /**
     * 数字正则
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    // ============================= 空值判断方法 =============================

    /**
     * 判断字符串是否为null或空字符串
     *
     * @param str 待判断的字符串
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为null且非空字符串
     *
     * @param str 待判断的字符串
     * @return true-不为空，false-为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为null、空字符串或仅包含空白字符
     *
     * @param str 待判断的字符串
     * @return true-为空白，false-不为空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否不为null且不只包含空白字符
     *
     * @param str 待判断的字符串
     * @return true-不为空白，false-为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合对象
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Map是否为空
     *
     * @param map Map对象
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param array 数组对象
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    // ============================= 默认值处理 =============================

    /**
     * 如果字符串为null或空，返回默认值
     *
     * @param str          原始字符串
     * @param defaultValue 默认值
     * @return 处理后的字符串
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * 如果字符串为null、空或空白，返回默认值
     *
     * @param str          原始字符串
     * @param defaultValue 默认值
     * @return 处理后的字符串
     */
    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    /**
     * 如果对象为null，返回空字符串
     *
     * @param obj 对象
     * @return 对象的字符串表示或空字符串
     */
    public static String toStringOrEmpty(Object obj) {
        return obj == null ? EMPTY : obj.toString();
    }

    // ============================= 字符串处理方法 =============================

    /**
     * 去除字符串首尾空白字符
     *
     * @param str 原始字符串
     * @return 去除空白后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 去除字符串所有空白字符
     *
     * @param str 原始字符串
     * @return 去除所有空白后的字符串
     */
    public static String removeWhitespace(String str) {
        return str == null ? null : str.replaceAll("\\s+", EMPTY);
    }

    /**
     * 首字母大写
     *
     * @param str 原始字符串
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 原始字符串
     * @return 首字母小写后的字符串
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 截取字符串到指定长度，超出部分用省略号代替
     *
     * @param str    原始字符串
     * @param length 截取长度
     * @return 截取后的字符串
     */
    public static String abbreviate(String str, int length) {
        return abbreviate(str, 0, length);
    }

    /**
     * 截取字符串到指定长度，超出部分用省略号代替
     *
     * @param str    原始字符串
     * @param offset 起始偏移量
     * @param length 截取长度
     * @return 截取后的字符串
     */
    public static String abbreviate(String str, int offset, int length) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() <= length) {
            return str;
        }
        if (offset > str.length()) {
            offset = str.length();
        }
        if ((str.length() - offset) < length) {
            length = str.length() - offset;
        }
        return str.substring(offset, offset + length) + "...";
    }

    // ============================= 字符串拼接方法 =============================

    /**
     * 连接多个字符串
     *
     * @param strings 字符串数组
     * @return 连接后的字符串
     */
    public static String join(String... strings) {
        if (isEmpty(strings)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            if (isNotEmpty(str)) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 使用分隔符连接多个字符串
     *
     * @param separator 分隔符
     * @param strings   字符串数组
     * @return 连接后的字符串
     */
    public static String join(String separator, String... strings) {
        if (isEmpty(strings)) {
            return EMPTY;
        }
        if (isEmpty(separator)) {
            return join(strings);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (isNotEmpty(strings[i])) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(strings[i]);
            }
        }
        return sb.toString();
    }

    // ============================= 验证方法 =============================

    /**
     * 验证手机号格式（中国大陆）
     *
     * @param phone 手机号
     * @return true-格式正确，false-格式错误
     */
    public static boolean isValidPhone(String phone) {
        return isNotBlank(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱地址
     * @return true-格式正确，false-格式错误
     */
    public static boolean isValidEmail(String email) {
        return isNotBlank(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证身份证号格式（简单校验）
     *
     * @param idCard 身份证号
     * @return true-格式正确，false-格式错误
     */
    public static boolean isValidIdCard(String idCard) {
        return isNotBlank(idCard) && ID_CARD_PATTERN.matcher(idCard).matches();
    }

    /**
     * 验证是否为数字（整数或小数）
     *
     * @param str 字符串
     * @return true-是数字，false-不是数字
     */
    public static boolean isNumber(String str) {
        return isNotBlank(str) && NUMBER_PATTERN.matcher(str).matches();
    }

    // ============================= 脱敏处理方法 =============================

    /**
     * 手机号脱敏处理（保留前3后4位）
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String maskPhone(String phone) {
        if (!isValidPhone(phone)) {
            return phone;
        }
        return phone.replaceAll("(?<=\\d{3})\\d(?=\\d{4})", "*");
    }

    /**
     * 邮箱脱敏处理（保留前1后1位）
     *
     * @param email 邮箱地址
     * @return 脱敏后的邮箱
     */
    public static String maskEmail(String email) {
        if (!isValidEmail(email)) {
            return email;
        }
        int atIndex = email.indexOf(AT);
        if (atIndex <= 1) {
            return email;
        }
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        String maskedUsername = username.charAt(0) + "***";
        return maskedUsername + domain;
    }

    /**
     * 姓名脱敏处理（保留姓氏，名字用*代替）
     *
     * @param name 姓名
     * @return 脱敏后的姓名
     */
    public static String maskName(String name) {
        if (isEmpty(name) || name.length() < 2) {
            return name;
        }
        char firstChar = name.charAt(0);
        if (Character.isChinese(firstChar)) {
            // 中文姓名：张**
            return firstChar + repeat("*", name.length() - 1);
        } else {
            // 英文姓名：J***
            return firstChar + repeat("*", name.length() - 1);
        }
    }

    /**
     * 重复字符串指定次数
     *
     * @param str   要重复的字符串
     * @param count 重复次数
     * @return 重复后的字符串
     */
    private static String repeat(String str, int count) {
        if (isEmpty(str) || count <= 0) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    // ============================= 编码解码方法 =============================

    /**
     * 字符串转字节数组（UTF-8编码）
     *
     * @param str 字符串
     * @return 字节数组
     */
    public static byte[] toBytes(String str) {
        return toBytes(str, StandardCharsets.UTF_8);
    }

    /**
     * 字符串转字节数组（指定编码）
     *
     * @param str        字符串
     * @param charsetName 字符集名称
     * @return 字节数组
     */
    public static byte[] toBytes(String str, String charsetName) {
        try {
            return str.getBytes(charsetName);
        } catch (Exception e) {
            log.error("字符串转字节数组失败，charsetName={}", charsetName, e);
            return str.getBytes(StandardCharsets.UTF_8);
        }
    }

    /**
     * 字符串转字节数组（指定字符集）
     *
     * @param str     字符串
     * @param charset 字符集
     * @return 字节数组
     */
    public static byte[] toBytes(String str, java.nio.charset.Charset charset) {
        return str.getBytes(Objects.requireNonNull(charset, "字符集不能为null"));
    }

    /**
     * 字节数组转字符串（UTF-8编码）
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String toString(byte[] bytes) {
        return toString(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 字节数组转字符串（指定编码）
     *
     * @param bytes       字节数组
     * @param charsetName 字符集名称
     * @return 字符串
     */
    public static String toString(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        } catch (Exception e) {
            log.error("字节数组转字符串失败，charsetName={}", charsetName, e);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    /**
     * 字节数组转字符串（指定字符集）
     *
     * @param bytes   字节数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(byte[] bytes, java.nio.charset.Charset charset) {
        return new String(bytes, Objects.requireNonNull(charset, "字符集不能为null"));
    }

    // ============================= 其他工具方法 =============================

    /**
     * 生成指定长度的随机字符串（字母和数字）
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (length <= 0) {
            return EMPTY;
        }
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 隐藏字符串中间部分（用于密码等敏感信息显示）
     *
     * @param str    原始字符串
     * @param prefix 保留前缀长度
     * @param suffix 保留后缀长度
     * @return 隐藏后的字符串
     */
    public static String hideMiddle(String str, int prefix, int suffix) {
        if (isEmpty(str) || str.length() <= prefix + suffix) {
            return str;
        }
        String prefixPart = str.substring(0, prefix);
        String suffixPart = str.substring(str.length() - suffix);
        return prefixPart + repeat("*", str.length() - prefix - suffix) + suffixPart;
    }
}
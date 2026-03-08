/**
 * 日期工具类
 * <p>
 * 提供常用的日期时间处理方法，包括格式化、解析、计算等<br>
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * 日期常量定义
 */
@Slf4j
public final class DateUtil {

    /**
     * 私有构造方法，防止实例化
     */
    private DateUtil() {
        throw new AssertionError("工具类不允许实例化");
    }

    // ============================= 日期格式常量 =============================

    /**
     * 标准日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 年月格式：yyyy-MM
     */
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";

    /**
     * 年月日时分秒毫秒格式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_TIME_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    // ============================= DateTimeFormatter实例 =============================

    /**
     * 标准日期时间格式化器
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /**
     * 标准日期格式化器
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * 标准时间格式化器
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * 年月格式化器
     */
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern(YEAR_MONTH_FORMAT);

    /**
     * 日期时间毫秒格式化器
     */
    private static final DateTimeFormatter DATE_TIME_MS_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_MS_FORMAT);

    // ============================= Date与LocalDateTime转换 =============================

    /**
     * Date转换为LocalDateTime（系统默认时区）
     *
     * @param date 日期对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Objects.requireNonNull(date, "日期参数不能为null");
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date（系统默认时区）
     *
     * @param localDateTime 本地日期时间对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "本地日期时间参数不能为null");
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转换为LocalDate（系统默认时区）
     *
     * @param date 日期对象
     * @return LocalDate对象
     */
    public static LocalDate toLocalDate(Date date) {
        Objects.requireNonNull(date, "日期参数不能为null");
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转换为Date（系统默认时区）
     *
     * @param localDate 本地日期对象
     * @return Date对象
     */
    public static Date toDate(LocalDate localDate) {
        Objects.requireNonNull(localDate, "本地日期参数不能为null");
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // ============================= 格式化方法 =============================

    /**
     * LocalDateTime格式化为字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param localDateTime 本地日期时间对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "本地日期时间参数不能为null");
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * LocalDateTime格式化为指定格式的字符串
     *
     * @param localDateTime 本地日期时间对象
     * @param pattern       格式模式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        Objects.requireNonNull(localDateTime, "本地日期时间参数不能为null");
        Objects.requireNonNull(pattern, "格式模式参数不能为null");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return formatter.format(localDateTime);
        } catch (Exception e) {
            log.error("日期格式化失败，pattern={}", pattern, e);
            throw new IllegalArgumentException("无效的日期格式模式: " + pattern, e);
        }
    }

    /**
     * LocalDate格式化为字符串（标准格式：yyyy-MM-dd）
     *
     * @param localDate 本地日期对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDate localDate) {
        Objects.requireNonNull(localDate, "本地日期参数不能为null");
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * Date格式化为字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     * @return 格式化后的字符串
     */
    public static String format(Date date) {
        Objects.requireNonNull(date, "日期参数不能为null");
        return format(toLocalDateTime(date));
    }

    /**
     * Date格式化为指定格式的字符串
     *
     * @param date    日期对象
     * @param pattern 格式模式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        Objects.requireNonNull(date, "日期参数不能为null");
        Objects.requireNonNull(pattern, "格式模式参数不能为null");
        return format(toLocalDateTime(date), pattern);
    }

    // ============================= 解析方法 =============================

    /**
     * 字符串解析为LocalDateTime（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        Objects.requireNonNull(dateTimeStr, "日期时间字符串参数不能为null");
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            log.error("日期时间字符串解析失败，dateTimeStr={}", dateTimeStr, e);
            throw new IllegalArgumentException("日期时间格式不正确: " + dateTimeStr, e);
        }
    }

    /**
     * 字符串解析为指定格式的LocalDateTime
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern     格式模式
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        Objects.requireNonNull(dateTimeStr, "日期时间字符串参数不能为null");
        Objects.requireNonNull(pattern, "格式模式参数不能为null");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            log.error("日期时间字符串解析失败，dateTimeStr={}, pattern={}", dateTimeStr, pattern, e);
            throw new IllegalArgumentException("日期时间格式不正确: " + dateTimeStr, e);
        }
    }

    /**
     * 字符串解析为LocalDate（标准格式：yyyy-MM-dd）
     *
     * @param dateStr 日期字符串
     * @return LocalDate对象
     */
    public static LocalDate parseDate(String dateStr) {
        Objects.requireNonNull(dateStr, "日期字符串参数不能为null");
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            log.error("日期字符串解析失败，dateStr={}", dateStr, e);
            throw new IllegalArgumentException("日期格式不正确: " + dateStr, e);
        }
    }

    /**
     * 字符串解析为Date（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return Date对象
     */
    public static Date parseToDate(String dateTimeStr) {
        Objects.requireNonNull(dateTimeStr, "日期时间字符串参数不能为null");
        return toDate(parseDateTime(dateTimeStr));
    }

    // ============================= 计算相关方法 =============================

    /**
     * 获取当前时间的LocalDateTime
     *
     * @return 当前时间的LocalDateTime
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前时间的Date
     *
     * @return 当前时间的Date
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 获取当前日期的LocalDate
     *
     * @return 当前日期的LocalDate
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * 计算两个LocalDateTime之间的天数差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 天数差（正数表示end在start之后）
     */
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "开始时间参数不能为null");
        Objects.requireNonNull(end, "结束时间参数不能为null");
        return ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
    }

    /**
     * 计算两个Date之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数差（正数表示endDate在startDate之后）
     */
    public static long daysBetween(Date startDate, Date endDate) {
        Objects.requireNonNull(startDate, "开始日期参数不能为null");
        Objects.requireNonNull(endDate, "结束日期参数不能为null");
        return daysBetween(toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    /**
     * 添加天数
     *
     * @param localDateTime 原始日期时间
     * @param days          要添加的天数（可为负数）
     * @return 新的LocalDateTime
     */
    public static LocalDateTime plusDays(LocalDateTime localDateTime, long days) {
        Objects.requireNonNull(localDateTime, "日期时间参数不能为null");
        return localDateTime.plusDays(days);
    }

    /**
     * 添加月份
     *
     * @param localDateTime 原始日期时间
     * @param months        要添加的月份数（可为负数）
     * @return 新的LocalDateTime
     */
    public static LocalDateTime plusMonths(LocalDateTime localDateTime, long months) {
        Objects.requireNonNull(localDateTime, "日期时间参数不能为null");
        return localDateTime.plusMonths(months);
    }

    /**
     * 获取当月第一天
     *
     * @param localDate 日期
     * @return 当月第一天的LocalDate
     */
    public static LocalDate firstDayOfMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate, "日期参数不能为null");
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取当月最后一天
     *
     * @param localDate 日期
     * @return 当月最后一天的LocalDate
     */
    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        Objects.requireNonNull(localDate, "日期参数不能为null");
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 判断是否为当天
     *
     * @param date 要判断的日期
     * @return true-是当天，false-不是当天
     */
    public static boolean isToday(Date date) {
        Objects.requireNonNull(date, "日期参数不能为null");
        return toLocalDate(date).equals(today());
    }
}
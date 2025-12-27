package com.ashen.petcommon.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtils {

    // ===================== 核心常量 =====================
    /** 系统默认时区（适配 Spring Boot 时区配置） */
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
    /** 常用时间格式 - 年月日时分秒 */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** 常用时间格式 - 年月日 */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** 常用时间格式 - 时分秒 */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // ===================== 替代 new Date() =====================
    /**
     * 获取当前时间（替代 new Date()，底层基于 Instant，线程安全）
     */
    public static Date now() {
        return Date.from(Instant.now());
    }

    // ===================== Date ↔ LocalDateTime 互转 =====================
    /**
     * Date 转 LocalDateTime（系统默认时区）
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 Date（系统默认时区）
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    // ===================== Date ↔ Instant 互转 =====================
    /**
     * Date 转 Instant（UTC 时间，跨时区场景用）
     */
    public static Instant dateToInstant(Date date) {
        return date == null ? null : date.toInstant();
    }

    /**
     * Instant 转 Date（UTC 时间）
     */
    public static Date instantToDate(Instant instant) {
        return instant == null ? null : Date.from(instant);
    }

    // ===================== Date 格式化（替代 SimpleDateFormat） =====================
    /**
     * Date 格式化为字符串（默认格式：yyyy-MM-dd HH:mm:ss）
     */
    public static String format(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * Date 格式化为字符串（自定义格式）
     */
    public static String format(Date date, String pattern) {
        return format(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Date 格式化为字符串（指定 DateTimeFormatter）
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        if (date == null || formatter == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.format(formatter);
    }

    // ===================== 字符串解析为 Date（替代 SimpleDateFormat.parse()） =====================
    /**
     * 字符串解析为 Date（默认格式：yyyy-MM-dd HH:mm:ss）
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DATETIME_FORMAT);
    }

    /**
     * 字符串解析为 Date（自定义格式）
     */
    public static Date parse(String dateStr, String pattern) {
        return parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串解析为 Date（指定 DateTimeFormatter）
     */
    public static Date parse(String dateStr, DateTimeFormatter formatter) {
        if (dateStr == null || formatter == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return localDateTimeToDate(localDateTime);
    }

    // ===================== Date 时间计算（替代 Calendar，更简洁） =====================
    /**
     * Date 加 N 天
     */
    public static Date plusDays(Date date, long days) {
        if (date == null) {
            return null;
        }
        LocalDateTime ldt = dateToLocalDateTime(date);
        return localDateTimeToDate(ldt.plusDays(days));
    }

    /**
     * Date 加 N 小时
     */
    public static Date plusHours(Date date, long hours) {
        if (date == null) {
            return null;
        }
        LocalDateTime ldt = dateToLocalDateTime(date);
        return localDateTimeToDate(ldt.plusHours(hours));
    }

    /**
     * Date 减 N 分钟
     */
    public static Date minusMinutes(Date date, long minutes) {
        if (date == null) {
            return null;
        }
        LocalDateTime ldt = dateToLocalDateTime(date);
        return localDateTimeToDate(ldt.minusMinutes(minutes));
    }

    /**
     * 获取 Date 所在月的第一天
     */
    public static Date firstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime ldt = dateToLocalDateTime(date);
        LocalDateTime firstDay = ldt.with(TemporalAdjusters.firstDayOfMonth());
        return localDateTimeToDate(firstDay);
    }

    // ===================== Date 比较（语义更清晰） =====================
    /**
     * 判断 date1 是否在 date2 之前
     */
    public static boolean isBefore(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toInstant().isBefore(date2.toInstant());
    }

    /**
     * 判断 date1 是否在 date2 之后
     */
    public static boolean isAfter(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toInstant().isAfter(date2.toInstant());
    }

    /**
     * 判断两个 Date 是否相等
     */
    public static boolean isEqual(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toInstant().equals(date2.toInstant());
    }
}
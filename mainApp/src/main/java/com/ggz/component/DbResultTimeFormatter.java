package com.ggz.component;

/**
 * @author ggz on 2026/3/6
 */
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbResultTimeFormatter {

    // 定义格式常量
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    // 预编译 Formatter (线程安全)
    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    private static final DateTimeFormatter D_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter T_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    // 兼容旧版 SimpleDateFormat (用于 java.sql 类型)
    private static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat(DATETIME_PATTERN);
    private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat(DATE_PATTERN);
    private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat(TIME_PATTERN);

    /**
     * 将 List<Map> 中的时间类型字段转换为字符串
     * 支持类型: Timestamp, Date, Time, LocalDateTime, LocalDate, LocalTime, ZonedDateTime
     */
    public static void formatTimeFields(List<HashMap<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        for (HashMap<String, Object> row : dataList) {
            if (row == null) continue;

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    Object formattedValue = convertTimeToObject(value);
                    // 如果转换成功（返回了字符串），则更新 Map 中的值
                    if (formattedValue instanceof String) {
                        row.put(entry.getKey(), formattedValue);
                    }
                }
            }
        }
    }

    /**
     * 判断对象是否为时间类型并转换
     * @return 如果是时间类型返回格式化后的 String，否则返回原对象
     */
    private static Object convertTimeToObject(Object value) {
        // 1. 处理 java.sql 类型 (MySQL/PostgreSQL JDBC 常见返回类型)
        if (value instanceof Timestamp) {
            synchronized (SDF_DATETIME) {
                return SDF_DATETIME.format((Timestamp) value);
            }
        }
        else if (value instanceof Date) {
            // java.sql.Date 只包含日期部分
            synchronized (SDF_DATE) {
                return SDF_DATE.format((Date) value);
            }
        }
        else if (value instanceof Time) {
            synchronized (SDF_TIME) {
                return SDF_TIME.format((Time) value);
            }
        }
        // 2. 处理 Java 8+ Time API 类型 (较新驱动或显式映射)
        else if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DT_FORMATTER);
        }
        else if (value instanceof LocalDate) {
            return ((LocalDate) value).format(D_FORMATTER);
        }
        else if (value instanceof LocalTime) {
            return ((LocalTime) value).format(T_FORMATTER);
        }
        else if (value instanceof ZonedDateTime) {
            // 如果有带时区的时间，通常转为本地时间再格式化，或者直接格式化
            return ((ZonedDateTime) value).format(DT_FORMATTER);
        }

        // 3. 处理 Long 类型的时间戳 (某些驱动或自定义查询可能返回毫秒数)
        // 注意：需要判断数值范围，避免将普通 ID 误判为时间戳
        else if (value instanceof Long) {
            long longVal = (Long) value;
            // 简单 heuristic: 如果大于 10 亿且小于 2100 年的毫秒数，可能是时间戳
            // 1970-01-01 到 2100-01-01 的毫秒范围大致在 0 到 4102444800000L 之间
            if (longVal > 1000000000L && longVal < 4102444800000L) {
                // 假设是毫秒级时间戳
                synchronized (SDF_DATETIME) {
                    return SDF_DATETIME.format(new java.util.Date(longVal));
                }
            }
        }

        // 非时间类型或无法识别，返回原对象
        return value;
    }
}
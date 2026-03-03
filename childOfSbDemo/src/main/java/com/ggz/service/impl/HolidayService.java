package com.ggz.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ggz on 2025/11/17
 */
public class HolidayService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 同步的刷新函数示例
     */
    public void reloadHolidayCache() {
        System.out.println("开始同步刷新节假日缓存...");

        // 同步执行数据库查询
        List<Holiday> holidays = Lists.newArrayList();

        // 同步写入Redis
        redisUtil.set("holidays:2024", "list", holidays);

        System.out.println("节假日缓存刷新完成，数据量: " + holidays.size());
    }

    /**
     * 查询方法
     */
    public List<Holiday> getHolidays() {
        return queryRedisData("holidays:2024", "list", this::reloadHolidayCache);
    }

    /**
     * 最简洁实用的版本
     */
    public <T> List<T> queryRedisData(String key, String item, Runnable refreshAction) {
        // 1. 先查缓存
        List<T> data = redisUtil.get(key, item);
        if (data != null && !data.isEmpty()) {
            return data;
        }

        // 2. 同步执行刷新
        System.out.println("执行同步刷新: " + key);
        try {
            refreshAction.run(); // 同步执行，等待完成
        } catch (Exception e) {
            System.err.println("刷新操作异常: " + e.getMessage());
            return new ArrayList<>();
        }

        // 3. 刷新后再次查询
        data = redisUtil.get(key, item);
        return data != null ? data : new ArrayList<>();
    }
}
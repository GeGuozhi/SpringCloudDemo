package com.psbc.invres.resarchlibrary.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

/**
 * @author ggz on 2026/3/27
 */
public class IndexCompletabFutureUtil {

    /**
     * 异步执行列表中每个元素的转换，默认批次大小 1000
     */
    public static <T, R> List<R> asynActEach(List<T> list, Function<T, R> function, ThreadPoolExecutor poolExecutor) {
        return batchProcess(list, function, poolExecutor, 1000);
    }

    /**
     * 分批次并发执行异步任务：
     * 1. 将 list 按 batchSize 分批（避免一次性提交过多任务导致线程池阻塞）
     * 2. 每批内部将每个元素并行提交到线程池执行
     * 3. 等待所有批次完成后，按原始顺序合并结果
     *
     * @param list        待处理的数据列表
     * @param function    每个元素的转换函数（同步执行）
     * @param poolExecutor 线程池
     * @param batchSize   每批提交的任务数量上限
     * @param <T>         原始元素类型
     * @param <R>         转换后元素类型
     * @return 按原始顺序排列的转换结果列表
     */
    private static <T, R> List<R> batchProcess(List<T> list, Function<T, R> function, ThreadPoolExecutor poolExecutor, int batchSize) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        if (function == null || poolExecutor == null) {
            throw new IllegalArgumentException("function and poolExecutor must not be null");
        }
        if (batchSize <= 0) {
            batchSize = 1000;
        }

        int total = list.size();
        // 1. 分批，每批最多 batchSize 条
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < total; i += batchSize) {
            int end = Math.min(i + batchSize, total);
            batches.add(list.subList(i, end));
        }

        // 2. 并行提交所有元素，按提交顺序收集 future（保持顺序）
        List<CompletableFuture<R>> allItemFutures = new ArrayList<>(total);
        for (List<T> batch : batches) {
            for (T item : batch) {
                CompletableFuture<R> f = CompletableFuture.supplyAsync(() -> function.apply(item), poolExecutor);
                allItemFutures.add(f);
            }
        }

        // 3. 等待所有 future 完成，按原始顺序收集结果
        List<R> finalResult = new ArrayList<>(total);
        for (CompletableFuture<R> f : allItemFutures) {
            finalResult.add(f.join());
        }
        return finalResult;
    }
}

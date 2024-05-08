package com.jyhun.CommunityConnect.utility;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ExecutionTimeUtils {
    public static <T> T measureExecutionTime(Supplier<T> supplier, String taskName) {
        long startTime = System.currentTimeMillis();
        T result = supplier.get();
        long endTime = System.currentTimeMillis();
        log.info("Execution time of {}: {} ms", taskName, endTime - startTime);
        return result;
    }
}
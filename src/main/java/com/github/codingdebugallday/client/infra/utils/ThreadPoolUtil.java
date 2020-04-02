package com.github.codingdebugallday.client.infra.utils;

import java.util.Objects;
import java.util.concurrent.*;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * <p>
 * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明：Executors各个方法的弊端：
 * 1）newFixedThreadPool和newSingleThreadExecutor:
 *   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
 * 2）newCachedThreadPool和newScheduledThreadPool:
 *   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 * </p>
 *
 * @author abigballofmud 2019/11/21 17:37
 * @since 1.0
 */
public class ThreadPoolUtil {

    private static volatile ExecutorService executorService;

    private ThreadPoolUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * guava的方式
     */
    public static ExecutorService getExecutorService() {
        if (Objects.isNull(executorService)) {
            synchronized (ThreadPoolUtil.class) {
                /*
                 * 使用谷歌的guava框架
                 * ThreadPoolExecutor参数解释
                 *   1.corePoolSize 常驻核心线程池大小
                 *   2.maximumPoolSize 线程池最大线程数 必须>=1
                 *   3.keepAliveTime 多余的空闲线程存活时间
                 *   4.TimeUnit 时间单位
                 *   5.BlockingQueue 任务队列
                 *   6.ThreadFactory 线程工厂用于创建线程
                 *   7.RejectedExecutionHandler 队列满了并且工作线程等于最大线程 线程拒绝策略
                 */
                if (Objects.isNull(executorService)) {
                    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("flink-pool-%d").build();
                    executorService = new ThreadPoolExecutor(8,
                            8,
                            1L,
                            TimeUnit.MINUTES,
                            new LinkedBlockingQueue<>(4), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return TtlExecutors.getTtlExecutorService(executorService);
    }

}

package com.cxg.weChat.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
/**
 * @Description 创建换多线程缓存池
 * @Author xg.chen
 * @Date 13:28 2019/10/24
 **/
public class PoolSend {
    private static Logger logger = LoggerFactory.getLogger(PoolSend.class);
    BlockingQueue<Runnable> workQueue;//任务队列
    ExecutorService executorService;//线程池接口

    //构造线程方法
    public PoolSend(){
        workQueue = new LinkedBlockingQueue<Runnable>();//构造无界的任务队列，资源足够，理论可以支持无限个任务。
        executorService = new ThreadPoolExecutor(2, 10,
                30, TimeUnit.SECONDS, workQueue,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    //将任务放到线程池中
    public void send(Runnable task){
        logger.debug("Pool Send sending mail...");
        executorService.execute(task);
    }
    //关闭线程池
    public void close(){
        logger.debug("Pool Send close mail...");
        executorService.shutdown();
    }
}

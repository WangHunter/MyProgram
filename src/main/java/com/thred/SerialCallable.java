package com.thred;

import org.junit.Test;
import com.thred.domain.BaseResult;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: yangzl2008
 * @Date: 2016/1/9 19:13
 */
public class SerialCallable {

    @Test
    public void test01() throws Exception {
        // 两个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        // 开启线程执行
        Future<BaseResult> future1 = executorService.submit(new Task(this, "method1", null));
        // 阻塞，直到程序返回。耗时time1
        BaseResult baseResult1 = future1.get();
        // 开启线程执行
        Future<BaseResult> future2 = executorService.submit(new Task(this, "method1", null));
        // 阻塞，直到程序返回。耗时time2
        BaseResult baseResult2 = future2.get();

        long end = System.currentTimeMillis();

        // 总耗时 time = time1 + time2 + time(线程执行耗时)
        System.out.println("baseResult1 is " + baseResult1 + "\nbaseResult2 is " + baseResult2 + "\ntime cost is " + (end - start));
    }

    public BaseResult method1() {
        BaseResult baseResult = new BaseResult();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        baseResult.setCode(1);
        baseResult.setMsg("method1");
        return baseResult;
    }

    public BaseResult method2() {
        BaseResult baseResult = new BaseResult();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        baseResult.setCode(1);
        baseResult.setMsg("method2");
        return baseResult;
    }

    class Task<T> implements Callable<T> {

        private Object object;

        private Object[] args;

        private String methodName;

        public Task(Object object, String methodName, Object[] args) {
            this.object = object;
            this.args = args;
            this.methodName = methodName;
        }

        @Override
        public T call() throws Exception {
            Method method = object.getClass().getMethod(methodName);
            return (T) method.invoke(object, args);
        }
    }

}

package com.thred;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import org.junit.Test;
import com.thred.domain.BaseResult;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: yangzl2008
 * @Date: 2016/1/9 19:39
 */
public class Parallel {

    @Test
    public void test02() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long start = System.currentTimeMillis();

        // 开启线程执行
        Future<BaseResult> future1 = executorService.submit(new Task(this, "method1", null));
        // 不阻塞，正常执行，baseResult1是cglib的代理类，采用延迟加载，只有在使用的时候才调用方法进行赋值。
        BaseResult baseResult1 = futureGetProxy(future1, BaseResult.class);
        // 开启线程执行
        Future<BaseResult> future2 = executorService.submit(new Task(this, "method2", null));
        // 不阻塞，正常执行，baseResult1是cglib的代理类,采用延迟加载，只有在使用的时候才调用方法进行赋值。
        BaseResult baseResult2 = futureGetProxy(future2, BaseResult.class);
        // 这里要使用baseResult1和baseResult2
        System.out.println("baseResult1 is " + baseResult1 + "\nbaseResult2 is " + baseResult2);
        long end = System.currentTimeMillis();
        // 总耗时time = max(time1,time2)
        System.out.println("time cost is " + (end - start));
    }

    private <T> T futureGetProxy(Future<T> future, Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        return (T) enhancer.create(clazz, new FutureLazyLoader(future));
    }

    /**
     * 延迟加载类
     * @param <T>
     */
    class FutureLazyLoader<T> implements LazyLoader {

        private Future<T> future;

        public FutureLazyLoader(Future<T> future) {
            this.future = future;
        }

        @Override
        public Object loadObject() throws Exception {
            return future.get();
        }
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

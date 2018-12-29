package com.lazy.tcc.dubbo.proxy.javassist;


import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.lazy.tcc.core.BeanFactory;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.interceptor.TransactionInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class TccInvokerInvocationHandler extends InvokerInvocationHandler {

    private Invoker<?> invoker;
    private Object target;

    public TccInvokerInvocationHandler(Invoker<?> handler) {
        super(handler);
    }

    public <T> TccInvokerInvocationHandler(T target, Invoker<T> invoker) {
        super(invoker);
        this.invoker = invoker;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Compensable compensable = method.getAnnotation(Compensable.class);

        if (compensable != null) {

            ProceedingJoinPoint pjp = new MethodProceedingJoinPoint(proxy, invoker, proxy, method, args);
            return BeanFactory.getSingle().getApplicationContext().getBean(TransactionInterceptor.class)
                    .around(pjp, compensable);
        } else {
            return super.invoke(invoker, method, args);
        }
    }


}

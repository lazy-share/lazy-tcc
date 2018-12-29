package com.lazy.tcc.dubbo.proxy.javassist;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.dubbo.DubboInvokerCaller;

import java.lang.reflect.Method;
import java.math.BigDecimal;


public class JavassistProxyFactory extends com.alibaba.dubbo.rpc.proxy.javassist.JavassistProxyFactory {

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        T proxy = (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));

        T finalProxy =  (T) Proxy.getProxy(interfaces).newInstance(new TccInvokerInvocationHandler(proxy, invoker));

        DubboInvokerCaller.putInvoker(invoker.getInterface(), finalProxy);

        return finalProxy;
    }


    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        // TODO Wrapper cannot handle this scenario correctly: the classname contains '$'
        final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName,
                                      Class<?>[] parameterTypes,
                                      Object[] arguments) throws Throwable {
                return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
            }
        };
    }
}


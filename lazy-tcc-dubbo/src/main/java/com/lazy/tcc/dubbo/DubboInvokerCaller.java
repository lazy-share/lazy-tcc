package com.lazy.tcc.dubbo;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.IdempotentContext;
import com.lazy.tcc.core.Invoker;
import com.lazy.tcc.core.InvokerCaller;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.core.exception.SystemException;
import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.propagator.IdempotentContextPropagator;
import com.lazy.tcc.core.propagator.IdempotentContextPropagatorSingleFactory;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/23.
 */
public class DubboInvokerCaller implements InvokerCaller {

    private static final ConcurrentHashMap<Class<?>, Object> INVOKER_HOLDER = new ConcurrentHashMap<>();

    public static void putInvoker(Class<?> interfaceRef, Object proxy) {
        try {
            INVOKER_HOLDER.put(interfaceRef, proxy);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Object caller(Invoker invoker, TransactionContext context) {

        if (invoker == null) {
            throw new SystemException("invoker is null");
        }
        if (context == null) {
            throw new SystemException("context is null");
        }

        Object proxy = INVOKER_HOLDER.get(invoker.getTargetClass());
        if (proxy == null) {
            throw new SystemException("proxy not found");
        }

        if (!invoker.getTxId().equals(context.getTxId())) {
            throw new TransactionManagerException(String.format("invoker [%s] txId not match current transaction context [%s] txId"
                    , JSON.toJSONString(invoker), JSON.toJSONString(context)));
        }

        if (StringUtils.isNotBlank(invoker.getMethodName())) {

            try {

                Method method = proxy.getClass().getMethod(invoker.getMethodName(), invoker.getParameterTypes());

                com.lazy.tcc.core.annotation.Idempotent idempotent = method.getAnnotation(Idempotent.class);

                //handler idempotent
                if (idempotent != null && idempotent.applicationRole().equals(ApplicationRole.CONSUMER)) {

                    IdempotentContextPropagatorSingleFactory.create(idempotent.propagator()).setIdempotentContext(
                            new IdempotentContext().
                                    setPk(new com.lazy.tcc.core.Idempotent.IdempotentPk()
                                            .setAppKey(SpiConfiguration.getInstance().getAppKey())
                                            .setReqSerialNum(invoker.getReqSerialNum()))
                                    .setTxPhase(context.getTxPhase())
                    );
                }

                method.invoke(proxy, invoker.getArgs());
            } catch (Exception e) {

                throw new TransactionManagerException(e);
            }
        }
        return null;
    }
}

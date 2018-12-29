package com.lazy.tcc.dubbo.propagator;


import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.IdempotentContext;
import com.lazy.tcc.core.propagator.support.AbstractIdempotentContextPropagator;

/**
 * <p>
 * DubboIdempotentContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class DubboIdempotentContextPropagator extends AbstractIdempotentContextPropagator {

    public DubboIdempotentContextPropagator() {
        super();
    }

    @Override
    public void setIdempotentContext(IdempotentContext idempotentContext) {

        if (idempotentContext != null) {
            RpcContext.getContext().setAttachment(IDEMPOTENT_PROPAGATOR_KEY, JSON.toJSONString(idempotentContext));
        }
    }

    @Override
    public IdempotentContext getIdempotentContext() {

        String context = RpcContext.getContext().getAttachment(IDEMPOTENT_PROPAGATOR_KEY);
        if (context == null) {
            return null;
        }

        return JSON.parseObject(context, IdempotentContext.class);
    }
}

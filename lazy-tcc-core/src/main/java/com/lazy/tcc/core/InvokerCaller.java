package com.lazy.tcc.core;

/**
 * <p>
 * InvokerCaller Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/23.
 */
public interface InvokerCaller {


    Object caller(Invoker invoker, TransactionContext context);

}

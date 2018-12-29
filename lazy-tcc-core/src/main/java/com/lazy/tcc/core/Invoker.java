package com.lazy.tcc.core;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * Invoker Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
@EqualsAndHashCode
public class Invoker implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -9943454654L;


    private Long txId;

    private Class targetClass;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;

    private String reqSerialNum;

    public String getReqSerialNum() {
        return reqSerialNum;
    }

    public Invoker setReqSerialNum(String reqSerialNum) {
        this.reqSerialNum = reqSerialNum;
        return this;
    }

    public Long getTxId() {
        return txId;
    }

    public Invoker setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Invoker setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Invoker setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Invoker setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public Invoker setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    @SuppressWarnings({"unchecked"})
    public void invoker(TransactionContext context) {
        BeanFactory.getSingle().getApplicationContext().getBean(InvokerCaller.class).caller(this, context);
    }

}

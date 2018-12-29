package com.lazy.tcc.common.enums;

/**
 * REQUIRED: Supports the current transaction. If there is no transaction at present, create a new transaction. This is the most common choice.  
 * REQUIRES_NEW: Create a new transaction and suspend the current transaction if it currently exists.  
 * NOT_SUPPORTED: Performs operations in a non-transactional manner and suspends the current transaction if it exists.  
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public enum Propagation {

    REQUIRED,
    REQUIRES_NEW,
    NOT_SUPPORTED,

}

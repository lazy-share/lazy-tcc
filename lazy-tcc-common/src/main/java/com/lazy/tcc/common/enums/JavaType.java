package com.lazy.tcc.common.enums;

/**
 * <p>
 *     Java 类型枚举
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/6/19.
 */
public enum JavaType {

    String("String"),
    BigDecimal("BigDecimal"),
    Integer("Integer"),
    Date("Date"),
    Time("Time"),
    Double("Double"),
    Float("Float"),
    Long("Long"),
    Timestamp("Timestamp"),
    Boolean("Boolean"),
    Short("Short"),
    Byte("Byte"),
    Character("Character")
    ;

    private String value;

    JavaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

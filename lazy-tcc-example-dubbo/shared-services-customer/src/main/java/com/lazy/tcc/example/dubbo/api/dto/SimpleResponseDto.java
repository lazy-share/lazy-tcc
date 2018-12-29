package com.lazy.tcc.example.dubbo.api.dto;

/**
 * <p>
 * Definition A Simple Response Dto
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public class SimpleResponseDto<V> extends AbstractBasicDto {

    /**
     *
     */
    private static final Long serialVersionUID = 89569569862L;


    /**
     * Basic Response Field
     */
    private String code;
    private String msg;
    private V data;

    public String getCode() {
        return code;
    }

    public SimpleResponseDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public SimpleResponseDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public V getData() {
        return data;
    }

    public SimpleResponseDto setData(V data) {
        this.data = data;
        return this;
    }

}

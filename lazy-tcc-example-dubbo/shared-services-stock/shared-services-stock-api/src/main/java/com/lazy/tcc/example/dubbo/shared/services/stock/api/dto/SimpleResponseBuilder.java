package com.lazy.tcc.example.dubbo.shared.services.stock.api.dto;

/**
 * <p>
 * SimpleResponseBuilder
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public final class SimpleResponseBuilder {

    /**
     * SimpleResponseDto
     *
     * @param data String
     * @return SimpleResponseDto
     */
    public static SimpleResponseDto<String> success(String data) {
        SimpleResponseDto<String> dto = new SimpleResponseDto<>();
        dto.setCode("200");
        dto.setMsg("success");
        dto.setData(data);

        return dto;
    }

}

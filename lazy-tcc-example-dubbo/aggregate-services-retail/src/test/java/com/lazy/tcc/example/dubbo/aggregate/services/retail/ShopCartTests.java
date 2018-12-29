package com.lazy.tcc.example.dubbo.aggregate.services.retail;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@SpringBootTest
@EnableAutoConfiguration
public class ShopCartTests extends BaseTests {

    @Test
    public void testSubmitOrder() {
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.put("/aggregate/services/retail/shopcart/order")
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("success"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("ok"))
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

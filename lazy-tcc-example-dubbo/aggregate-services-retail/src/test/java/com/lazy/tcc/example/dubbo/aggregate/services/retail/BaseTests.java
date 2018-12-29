package com.lazy.tcc.example.dubbo.aggregate.services.retail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author laizhiyuan
 * @since 2018/1/4.
 * <p>测试基础类</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
@EnableAspectJAutoProxy
@Transactional(rollbackFor = {Exception.class, RuntimeException.class},
        isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class BaseTests {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        System.out.println("init mockMvc instance");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 测试结果：PASSED
     * 测试人：LaiZHiYuan
     * <p>
     * 测试覆盖范围:
     * 1、批量保持是否成功
     * 2、批量保存是否缓存
     * 3、是否使用默认key生成器
     * 4、根据批量id查询是否成功
     * 5、根据刚才保存返回的批量Id查是否发起select
     * <p>
     * 测试步骤:
     * 1、先注释Step-2，执行Step-1
     * 2、复制控制台打印的结果作为Step-2的测试数据
     * 2、再注释Step-1, 执行Step-2
     */
    @Test
    public void testReadme() {
        /************** Step-1 *****************/
//        String readme = "这是测试模版说明";
//        System.out.println(readme);

        /************** Step-2 *****************/
        System.out.println("这是测试模版说明");
    }

}

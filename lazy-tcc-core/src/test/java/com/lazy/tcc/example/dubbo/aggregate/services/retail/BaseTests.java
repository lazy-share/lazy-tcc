package com.lazy.tcc.example.dubbo.aggregate.services.retail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author laizhiyuan
 * @since 2018/1/4.
 * <p>测试基础类</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
@EnableAspectJAutoProxy
@SpringBootApplication
public class BaseTests {


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

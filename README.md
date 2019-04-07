
## 介绍
```
1、基于TCC
2、可补偿
3、多次补偿支持幂等性
4、目前只支持dubbo
5、可扩展

```

## 使用

```
1、进入任意目录下执行
git clone https://github.com/lazy-share/lazy-tcc.git

2、你习惯的eclipse或IDEA打开该项目

3、配置maven私服settings.xml

4、在lazy-tcc目录下执行
mvn clean install

5、进入目录lazy-tcc-example-dubbo
该目录有三个模块，分别是：共享服务客户中心，共享服务库存中心，聚合零售服务

6、启动共享服务客户中心，共享服务库存中心，聚合零售服务

7、进入聚合零售服务【aggregate-services-retail】单云测试类进行测试

8、测试思路：

流程:
下零售采购单 -> 保存订单 -> 保存订单明细 -> 扣减资金 -> 扣减库存

分布式事务一致性：
只要任何一个环节报错，所有操作将回滚

```



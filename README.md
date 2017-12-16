# 概述
基于xml, jaxp, jdbc, spring, freemarkerd等技术实现的 代码生成工具jc-codegen

# 目标
## 背景
从事业务开发的，基本都是表结构先行，然后根据表结构写代码。而代码框架都是SSM（SpringMVC+Spring+Mybatis） 
而其中过程，我发现，一些重复劳动的事情：
1. 根据表结构生成entity
2. 然后写mapper（DAO层），实现基础的增删改查
3. 然后写service（service层），实现业务代码，异常情况，事务等
4. 最后通过SpringMVC（control层），实现基于HTTP协议的接口或功能。

并且根据模块化思想（参考spring-framework），我发现1-3可以提取出来，并且自动化生成代码。也获益于JetBrains IDEA的自动刷新代码功能，生成代码后不用刷新Project视图就能看见效果。


## 目标
1. 减少重复劳动
2. 有助于统一代码规范（当然不能完全，要完全还需另外手段）
3. 提高业务开发的效率
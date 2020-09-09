# 微服务配置中心

## EnableConfigServer 注解
1. 注解申明了启用 _**ConfigServerAutoConfiguration**_ 自动配置
2. 自动配置中 Import 一堆支持的配置项
3. EnvironmentController 处理配置项获取请求

### 有哪些配置模式、方法
* 本地模式 => 配置中心 **_profile=native_** => **_NativeEnvironmentProperties_** (其实就是配置 NativeRepositoryConfiguration 中的Bean，不同在于此
Configuration 中标注了该Configuration所有配置的Bean在Profile是native的时候才生效)
* 数据库 JDBC 配置模式
* Git 配置模式 
* SVN 配置模式
* Redis 配置模式
* CredHub 配置模式
* Vault 配置模式
* 阿里 Nacos 配置模式


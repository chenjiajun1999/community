# Community
Community 是牛客网讨论区实战项目，用于巩固 Web 开发所学的知识。
## 技术栈

#### 后端
Java 1.8 + Spring Boot 2.5.6 + MyBatis-Plus 3.3.2 + MySQL 5.7
#### 前端
TypeScript + Vue 3.2 + Pinia + Element Plus


## 技术点
#### 登录与注册
基于 Spring Security 框架实现了用户的登录、登出和访问权限控制；
登录时 ，使用 Redis 进行缓存登录验证码，并设置缓存时间用于判断其是否失效；
注册时，通过 JavaMailSender 设置邮箱发送验证激活链接。
#### 点赞功能
当访问浏览储文章或评论的时候，根据 id 从 Redis 中查询点赞总数，
如果有就直接使用 Redis 的数据进行覆盖，
若没有则将数据库中的点赞数对 Redis 进行缓存填充；
若用户为登录状态也将同理缓存用户的点赞状态，
在 Redis 中通过点赞状态变更情况计算点赞数，保证点赞功能的高可用性。
最后，通过设置 Quartz 定时将 Redis 缓存数据刷回 MySQL 数据库同步数据并清空 Redis 缓存。
#### 其他
使用 Jasypt 对配置文件进行加密，启动项目时候需要，添加 `jasypt.encryptor.password` 值。
查询使用 MyBatis-Plus 提供的 Mapper，分页功能使用 [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)。

## 在线体验
admin/admin

## 演示图
![4256ec4ac8f6a748512c5932a558199.png](https://s2.loli.net/2022/09/13/AxXlZ6dnpGhIWi5.png)
![5f6b14be41f5acf28832afa1f9f98aa.png](https://s2.loli.net/2022/09/13/gjbD1WfelRdSqUG.png)

[TOC]

# SpringBoot

### springboot如何实现热部署

访问spring.io官网，找到所有启动器列表<br/>
https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-starter <br/>

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

intellij idea需要修改一些配置，详见https://blog.csdn.net/hz_940611/article/details/80594788<br/>
ctrl + F9 重新编译之后，重新访问<br/>

### 配置文件profiles

指定配置文件，对于只有一个yml配置文件，可以通过下面这种方式

```
spring:
  profiles:
    active: uat

---
server:
  port: 8081
spring:
  profiles: dev

---
server:
  port: 8082
spring:
  profiles: uat
```

### spring.factories

spring-boot-autoconfigurate-xxx.release.jar<br/>
核心配置文件

---

## 各项目详细介绍

### 1.0 springboot整合log

org.slf4j.Logger

java.util.logging.Logger

org.apache.commons.logging.Log

### 1.1 springboot配置文件

配置文件映射到实体

```java
@Component
@ConfigurationProperties(prefix = "student")
```

指定配置文件路径进行映射

```java
@Configuration
@ConfigurationProperties(prefix = "com.user")
@PropertySource(value = "classpath:test.properties")
```

### 1.2 springboot整合jdbc

添加依赖项

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

添加数据连接配置

```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT
    username: root
    password: 123456
```

使用<mark>JdbcTemplate</mark>模板操作数据库

示例

```java
jdbcTemplate.update("insert into account(name, money) values(?, ?)",
                    account.getName(),account.getMoney());

jdbcTemplate.query("select * from account where id = ?", 
                    new Object[]{id}, 
                    new BeanPropertyRowMapper(Account.class));
```

### 1.3 springboot整合jpa

添加依赖项

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

数据库连接配置

```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT
    username: root
    password: 123456

  jpa:
    hibernate:
      ddl-auto: update  #第一次建表create  后面用update
    show-sql: true
    # 定义数据库引擎为innodb
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
```

定义实体类

```java
@Entity
@Table(name = "t_account")
public class Account implements Serializable{

    private static final long serialVersionUID = 4309084519412872386L;
    @Id
    @GeneratedValue
    private int id ;
    private String name ;
    private double money;
    //省略getter setter方法...
}
```

编写dao，继承JpaRepository接口

```java
public interface AccountDao  extends JpaRepository<Account,Integer> {
}
```

### 1.4 springboot整合mybatis注解形式

druid包含了mybatis的依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.0.29</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

配置数据库连接

略

编写Mapper文件

```java
@Mapper
public interface AccountMapper {

    @Insert("insert into account(name, money) values(#{name}, #{money})")
    int add(@Param("name") String name, @Param("money") double money);

    @Update("update account set name = #{name}, money = #{money} where id = #{id}")
    int update(@Param("name") String name, @Param("money") double money, @Param("id") int id);

    @Delete("delete from account where id = #{id}")
    int delete(int id);
}
```

### 1.5 springboot整合mybatisxml形式

1)添加druid依赖

2)yml配置文件

```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT
    username: root
    password: 123456

mybatis:
  #指明和数据库映射的实体的所在包
  type-aliases-package: com.example.demo.entity
  ##指明mapper的xml文件存放位置
  mapper-locations: classpath:mybatis/*.xml
```

3)Mapper.java

```java
public interface AccountMapper {
    int update( @Param("money") double money, @Param("id") int  id);
}
```

4)Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.mapper.AccountMapper">

    <update id="update">
        UPDATE account set money=#{money} WHERE id=#{id}
    </update>
</mapper>
```

5)启动类扫描mapper包路径

```java
@MapperScan("com.example.demo.mapper")
```

### 1.6.0 springboot整合redis

1）添加redis的依赖

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

2）redis配置

```yml
spring.redis.host=localhost
spring.redis.port=6379
#spring.redis.password=
spring.redis.database=1
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-wait=-1
spring.redis.jedis.pool.max-idle=500
spring.redis..jedis.pool.min-idle=0
spring.redis.timeout=5000
```

3）StringRedisTemplate使用

```java
  @Autowired
    private StringRedisTemplate template;

    public void setKey(String key,String value){
        this.template.opsForValue().set(key, value, 1, TimeUnit.MINUTES);//1分钟过期
    }

    public String getValue(String key){
        return this.template.opsForValue().get(key);
    }
```

### 1.6.1 springboot整合redis实现分布式锁

### 1.7 springboot整合swagger接口文档

```xml
<!--swagger2-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.8.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.8.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>1.8.9</version>
        </dependency>
```

swagger配置

```java
@Configuration
@EnableSwagger2
public class Swagger2 extends WebMvcConfigurationSupport {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger2构建api文档")
                .description("springboot利用swagger2构建api文档")
                .termsOfServiceUrl("https://github.com/hejiancao/springboot")
                .version("1.0")
                .build();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
```

### 1.9 springboot定时任务

创建定时任务

```java
//    @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
//    @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
//    @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
//    @Scheduled(cron=" /5 ") ：通过cron表达式定义规则

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
```

启动类开启定时任务

```java
@EnableScheduling
```

### 2.0 springboot发送邮件

添加邮件依赖

```xml
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
```

yml配置

```properties
spring.mail.host=aicev.icoremail.net
spring.mail.username=sen.shao@ai-ways.com
spring.mail.password=******
spring.mail.port=25
spring.mail.protocol=smtp
spring.mail.default-encoding=UTF-8
```

测试

```java
    @Autowired
    private JavaMailSenderImpl mailSender;


    private static final String SENDER = "sen.shao@ai-ways.com";
    private static final String RECEIVER = "sen.shao@ai-ways.com";

    /**
     * 发送包含简单文本的邮件
     */
    @Test
    public void sendTxtMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(new String[] {RECEIVER});
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setSubject("Spring Boot Mail 邮件测试【文本】");
        simpleMailMessage.setText("这里是一段简单文本。");
        // 发送邮件
        mailSender.send(simpleMailMessage);

        System.out.println("邮件已发送");
    }


    更多....
```

### 2.1 springboot表单

### 2.2 springboot统一异常处理

### 2.3 springboot异步

### 2.4 springboot分页
### 2.5 springboot分页
### 2.6 springboot整合rabbitmq
### 2.7 springboot整合hadoop
### 2.8 springboot实现websocket
### 2.9 springboot整合elasticsearch
### 3.0 springboot整合poi
### 3.1 springboot整合kafka
### 3.2 springboot国际化
# 背景介绍
我们在做单元测试的时候，有没有发现，当项目代码越来越多，功能越来越多的时候，启动
速度会越来越慢，然后我们要测试的功能可能是很简单的业务逻辑，并不需要加载全部的
bean，那么改如何提高我们的单元测试效率呢。


# SpringBootTest
`@SpringBootTest(classes = SpringbootTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
`
## webEnvironment
这个属性决定了要不要启动web环境，说白了就是要不要启动tomcat容器，可选值为

* `MOCK`：启动一个模拟的 Servlet 环境，这是默认值。
* `RANDOM_PORT`：启动一个 Tomcat 容器，并监听一个随机的端口号
* `DEFINED_PORT`：启动一个 Tomcat 容器，并监听配置文件中定义的端口（未定义则默认监听8080）
* `NONE`：不启动 Tomcat 容器

如果你要测试的方法不需要用到 Tomcat 容器，比如：
* 测试一个 DAO 类的增删改查
* 测试一个 Service 类的业务方法
* 测试一个 Util 类的公用方法
* 测试一个配置文件类是否读取到了正确的值

只需要配置`webEnvironment = SpringBootTest.WebEnvironment.NONE`即可达到加速效果，这时测试类启动时就只会初始化 Spring 上下文，不再启动 Tomcat 容器

## classes

classes 属性用来指定运行测试类需要装载的 class 集合，如果不指定，那么启动测试类时需要加载的Bean
的数量和正常启动一次入口类(即有`@SpringBootApplication`注解的类)加载的 Bean 数量是一样的。

如果你的项目中有很多个 Bean， 特别是有以下几种时：
* 有 `CommandLineRunner` 的实现类
* 用 `@PostConstruct` 注解指定了初始化方法的类

这几种类在程序初始化的过程中都会运行自身的业务代码或者初始化代码，从而延后了测试方法的运行。

在这种情况下，我们在编写测试类的时候，如果明确这个测试类会用到哪几个 Bean，则可以在 classes 属性
处指定，之后启动测试类的时候，就只会加载需要的 Bean 到上下文中，从而加快启动速度。

例如下面的单元测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestService.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Test2 {

    //只是为了测试业务方法，不需要启动tomcat，因此WebEnvironment设为NONE，可以加快启动速度
    @Autowired
    private TestService testService;


    @Test
    public void testWorld() {
        testService.test();
    }
}
```




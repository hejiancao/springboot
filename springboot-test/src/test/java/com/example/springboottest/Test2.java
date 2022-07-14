package com.example.springboottest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by shaosen on 2022/7/14
 */
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

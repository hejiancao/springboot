package com.example.springboottest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by shaosen on 2022/7/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Test1 {

    //详细解读，请看readme.md
    @Autowired
    private TestRestTemplate restTemplate;//使用TestRestTemplate需要启动tomcat容器，因此WebEnvironment不能为NONE，因为NONE不会启动tomcat

    @Test
    public void test() {
        ResponseEntity entity = this.restTemplate.getForEntity("/test/hello", String.class);
        Assert.assertTrue(entity.getBody().equals("hello"));
    }


}

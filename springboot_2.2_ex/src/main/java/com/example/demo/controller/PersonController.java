package com.example.demo.controller;

import com.example.demo.entity.PersonForm;
import com.example.demo.utils.RespResult;
import com.example.demo.utils.RespResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author created by shaos on 2019/6/21
 */

@RestController
public class PersonController {


    /** 验证表单，并用统一异常处理
     * @Valid和@Validated区别：
     * 1. Valid可以使用在字段上，而Validated不可以
     * 2. Valid支持嵌套，Validated不支持
     * 3. 具体可百度
     * @param personForm
     * @author shaos
     * @date 2019/6/21 12:05
     */
    @PostMapping("/")
    public RespResult testEx(@RequestBody @Valid PersonForm personForm) {
        return RespResultUtils.success();
    }


}

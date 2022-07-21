package com.example.i18n.web;

import com.example.i18n.utils.I18nUtil;
import com.example.i18n.utils.LocaleMessageSourceUtil;
import com.example.i18n.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhenglian on 2016/10/9.
 */
@Controller
public class LoginController {
    @Autowired
    private LocaleMessageSourceUtil messageSourceUtil;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/chooseLanguage")
    public Map<String, Object> chooseLanguage(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        String i18nProperties = I18nUtil.chooseI18nProperties(lang);
        Map<String, Object> map = PropertiesUtil.readPropAsMap(i18nProperties);
        return map;
    }

}

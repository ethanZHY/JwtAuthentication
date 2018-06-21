package com.demo.appserver.service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    public String build(String template, Map<String, String> contextMap) {
        Context context = new Context();
        contextMap.forEach((k,v) -> {context.setVariable(k, v);});
        return templateEngine.process(template, context);
    }
}
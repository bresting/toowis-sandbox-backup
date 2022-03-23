package com.toowis.simple.controller;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toowis.simple.mapper.DBXSimpleMapper;
import com.toowis.simple.mapper.SimpleMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyController {
    
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);
    
    // primary bean
    @Autowired
    private SqlSession sqlSession;
    
    // named bean
    // lombok @RequiredArgsConstructor사용시 @Qualifier안됨
    @Autowired
    @Qualifier("sessionTemplateDBX")
    private SqlSession dbxSqlSession;
    
    // mapper-annotation으로 디비 커넥션 선택
    private final SimpleMapper simpleMapper;
    private final DBXSimpleMapper dbxSimpleMapper;
    
    @GetMapping("/page01")
    public String getPage01(Model model) {
        {
            // named bean
            String result = dbxSqlSession.selectOne("com.toowis.simple.mapper.DBXSimpleMapper.selectSimple");
            logger.info("{} :: {}", result, dbxSqlSession.hashCode());
        }
        String returnValue = sqlSession.selectOne("com.toowis.simple.mapper.SimpleMapper.selectSimple");
        logger.info("{} :: {}", returnValue, sqlSession.hashCode());
        model.addAttribute("method", "page01");
        model.addAttribute("msg", returnValue);
        return "simple";
    }
    
    @RequestMapping("/page02")
    public String getPage02(Model model) {
        {
            String result = dbxSimpleMapper.selectSimple();
            logger.info("{} :: {}", result, dbxSimpleMapper.hashCode());
        }
        String returnValue = simpleMapper.selectSimple();
        logger.info("{} :: {}", returnValue, simpleMapper.hashCode());
        model.addAttribute("method", "page02");
        model.addAttribute("msg", returnValue);
        return "simple";
    }
}

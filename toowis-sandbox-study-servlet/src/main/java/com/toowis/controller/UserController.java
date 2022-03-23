package com.toowis.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toowis.config.BaseController;

public class UserController implements BaseController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 파라미터 추출
        // 2. 유효성 체크
        // 3. VO 객체에 데이터 바인딩
        // 4. Service 객체의 데이터 바인딩
        // 5. Output View 페이지로 이동
        
        System.out.println(request.getQueryString());
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("<html><body>User infomation OK!!</body></html>");
        response.getWriter().flush();
    }
}

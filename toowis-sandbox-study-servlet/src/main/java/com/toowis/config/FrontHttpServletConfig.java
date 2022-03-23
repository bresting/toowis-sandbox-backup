package com.toowis.config;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toowis.controller.HelloController;
import com.toowis.controller.UserController;

public class FrontHttpServletConfig extends HttpServlet {

    private static final long serialVersionUID = -224360719938184034L;

    // 서브 컨트롤러 저장소
    HashMap<String, BaseController> subControllerMap = null;
    
    @Override
    public void init(ServletConfig sc) throws ServletException {
        
        System.out.println("FrontHttpServletConfig > init");
        
        // 서브 컨트롤러 에 대한 정보를 가지는 객체
        subControllerMap = new HashMap<String, BaseController>();
         
        // 서브 컨트롤러 연결 및 매핑
        subControllerMap.put("/hello.do", new HelloController());
        subControllerMap.put("/user.do" , new UserController ());
    }
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * ContextPath : /dev
         * RequestURI  : /dev/example1.do
         * 
         * path : /example1.do
         */
        
        System.out.println("FrontHttpServletConfig > service");
        
        String contextPath = request.getContextPath();
        String requestURI  = request.getRequestURI();
        String servletPath = requestURI.substring(contextPath.length());
         
        // path에 해당되는 컨트롤러를 실행 - /example1.do
        BaseController subController = subControllerMap.get(servletPath);
        subController.execute(request, response);
    }
}

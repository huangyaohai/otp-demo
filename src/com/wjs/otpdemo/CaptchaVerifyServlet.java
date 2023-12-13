package com.wjs.otpdemo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaVerifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 判断图片验证码是否正确（不区分大小写）
        String captcha = (String) req.getSession().getAttribute("captcha");
        String captcha2 = req.getParameter("captcha");
        if (captcha.equalsIgnoreCase(captcha2)) {
            //返回“valid”字符串
            resp.getWriter().print("valid");
            return;
        }
        System.out.println("图片验证码验证错误");
        resp.getWriter().print("invalid");
    }
}

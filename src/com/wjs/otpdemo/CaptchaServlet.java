package com.wjs.otpdemo;

import cn.hutool.captcha.LineCaptcha;
import com.wjs.captcha.CaptchaUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 方法一
        // genCaptchaByJava(request, response);

        // 方法二，通过hutool生成
        LineCaptcha captcha = CaptchaUtils.getCaptcha();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(captcha.getImage(), "jpg", response.getOutputStream());

        // 验证码写入到session
        request.getSession().setAttribute("captcha", captcha.getCode());
    }

    private void genCaptchaByJava(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set content type to image/jpeg
        response.setContentType("image/jpeg");

        // Create a blank image with the dimensions of the captcha
        int width = 200;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the Graphics2D object to draw on the image
        Graphics2D g2d = image.createGraphics();

        // Generate a random captcha text
        String captchaText = generateCaptchaText();

        // Store the captcha text in the session for verification purposes
        request.getSession().setAttribute("captchaText", captchaText);

        // Set the background color of the captcha image
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set the font for the captcha text
        Font font = new Font("Arial", Font.BOLD, 24);
        g2d.setFont(font);

        // Set the color for the captcha text
        g2d.setColor(Color.BLACK);

        // Draw the captcha text on the image
        g2d.drawString(captchaText, 20, 35);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Write the image to the response output stream
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    private String generateCaptchaText() {
        // Generate a random captcha text
        Random random = new Random();
        int length = 6;
        StringBuilder captchaText = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            captchaText.append(randomNumber);
        }

        return captchaText.toString();
    }
}

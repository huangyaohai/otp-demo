package com.wjs.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

public class Test {

    public static void main(String[] args) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("D:\\OtpQr\\line.png");
        //输出code
        System.out.println(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        boolean verify = lineCaptcha.verify("1234");
        System.out.println(verify);

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("D:\\OtpQr\\line.png");
        //新的验证码
        System.out.println(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        verify = lineCaptcha.verify("1234");
        System.out.println(verify);


        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 6, 20);
        captcha.write("D:\\OtpQr\\line2.png");
        System.out.println(captcha.getCode());

        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha2 = CaptchaUtil.createShearCaptcha(200, 100, 6, 4);
        captcha2.write("D:\\OtpQr\\line3.png");
        System.out.println(captcha2.getCode());

        // 返回图片base64
        System.out.println(captcha2.getImageBase64());
        System.out.println(captcha2.getImageBase64Data());

        test();
    }

    private static void test() {
        String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //自定义生成验证码的组合，这里的组合就是0-9数字加大小写字母，随机取6个
        RandomGenerator randomGenerator = new RandomGenerator(baseStr, 6);
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(240, 100);
        lineCaptcha.setGenerator(randomGenerator);
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("D:\\OtpQr\\line_test.png");
        //输出code
        String code = lineCaptcha.getCode();
        System.out.println(code);
        //验证图形验证码的有效性，返回boolean值
        boolean verify = lineCaptcha.verify("123456");
        System.out.println(verify);

        //验证输入的时候不区分大小写, 真实验证的时候不区分大小写
        String input = "Abcd123";
        String inputlowerCase = input.toLowerCase();
        String codelowerCase = code.toLowerCase();
        System.out.println("input:" + inputlowerCase);
        System.out.println("code:" + codelowerCase);
        System.out.println(inputlowerCase.equals(codelowerCase));


    }
}

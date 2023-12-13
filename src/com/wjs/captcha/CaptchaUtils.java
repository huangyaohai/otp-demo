package com.wjs.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

public class CaptchaUtils {
    public static LineCaptcha getCaptcha() {
        String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //自定义生成验证码的组合，这里的组合就是0-9数字加大小写字母，随机取6个
        RandomGenerator randomGenerator = new RandomGenerator(baseStr, 6);
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(240, 100);
        lineCaptcha.setGenerator(randomGenerator);

        System.out.println("验证码:" + lineCaptcha.getCode());
        return lineCaptcha;
    }
}

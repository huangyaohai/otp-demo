package com.wjs.otpdemo;

import com.wjs.dao.UserDao;
import com.wjs.dao.UserVo;
import com.wjs.email.EmailBaseLogic;
import com.wjs.util.QRUtil;
import com.wjs.util.TotpUtil;

import java.io.File;

public class RegisterTest {

    private static String f_temp = "D://OtpQr";

    public static void main(String[] args) {

        String userName = "test2";
        String email = "395022114@qq.com";

        // 判断用户是否存在
        UserDao userDao = new UserDao();
        UserVo userVo = userDao.getUserByName(userName);
        if (null != userVo) {
            System.out.println("用户已存在: " + userName);
            return;
        }

        UserVo vo = new UserVo();
        vo.setUsername(userName);
        // 创建OTP信息
        String secretBase32 = TotpUtil.getRandomSecretBase32(64);
        vo.setOtpSk(secretBase32);

        // 通知用户
        sendRegisterMail(userName, email, secretBase32);

        // 数据库新增用户
        UserDao dao = new UserDao();
        dao.add(vo);

    }


    private static void sendRegisterMail(final String operCode, final String email, final String secretBase32) {
        String host = "xxxxx@qq.com";

        String totpProtocalString = TotpUtil.generateTotpString(operCode, host, secretBase32);

        String filePath = f_temp;
        // 创建File对象，判断路径存不存在，不存在则创建
        File file = new File(filePath);
        if (!file.exists()) {
            // 打印提示
            System.out.println("创建文件夹:" + filePath);
            // 创建文件夹
            file.mkdirs();
        }

        String fileName = Long.toString(System.currentTimeMillis()) + ".png";

        try {
            QRUtil.generateMatrixPic(totpProtocalString, 150, 150, filePath, fileName);
        } catch (Exception e) {
            throw new RuntimeException("生成二维码图片失败:" + e.getMessage());
        }

//        String content = "用户名：" + operCode + "</br>"
//                + "系统使用密码 + 动态口令双因素认证的方式登录。</br>请按以下方式激活手机动态口令：</br>安卓用户请点击<a href='http://otp.aliyun.com/updates/shenfenbao.apk'>下载</a>，"
//                + "</br>苹果手机在AppStore中搜索Google Authenticator 或 【身份宝】（Alibaba）。下载安装后，通过扫描以下二维码激活动态口令。</br>"
//                + "<img src=\"cid:image\">";
        String content = "用户名：" + operCode + "</br>"
                + "系统使用密码 + 动态口令双因素认证的方式登录。</br>请按以下方式激活手机动态口令："
                + "</br>安卓用户或苹果用户下载Google Authenticator(谷歌身份验证器)。安装后，通过扫描以下二维码激活动态口令。</br>"
                + "<img src=\"cid:image\">";
        EmailBaseLogic emailBaseLogic = new EmailBaseLogic();
//	        String to, String title, String content, String imagePath
        emailBaseLogic.sendWithPic(email, "账户开立通知", content, filePath + "/" + fileName);
    }

}
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function changeCaptcha() {
            // 当用户点击验证码图片时，changeCaptcha()函数被调用。该函数使用Math.random()方法生成一个介于0和1之间的随机数，并将其存储在randomNumber变量中。然后，该随机数被附加为查询参数rand的值到CaptchaServlet的URL中。通过这样做，强制浏览器从服务器获取一个新的验证码图片，从而实现了验证码的更换。每次点击图片时，生成的随机数都不同，因此浏览器会获取一个不同的验证码图片。
            // Generate a random number to add to the CaptchaServlet URL to force a new captcha image
            var randomNumber = Math.random();

            // Get the captcha image element
            var captchaImage = document.getElementById('captchaImage');

            // Update the 'src' attribute of the captcha image with the new URL
            captchaImage.src = "CaptchaServlet?rand=" + randomNumber;
        }

        function checkCaptcha() {
            var captchaInput = document.getElementById('captchaInput');
            var captchaValidation = document.getElementById('captchaValidation');

            if (captchaInput.value.length === 6) {
                // Perform the validation by calling the VerifyServlet
                console.log("Captcha: " + captchaInput.value);
                $.ajax({
                    url: "CaptchaVerifyServlet",
                    type: "POST",
                    data: {captcha: captchaInput.value},
                    success: function (response) {
                        if (response === "valid") {
                            captchaValidation.classList.remove('invalid');
                            captchaValidation.classList.add('valid');
                        } else {
                            captchaValidation.classList.remove('valid');
                            captchaValidation.classList.add('invalid');
                        }
                    },
                    error: function () {
                        console.log("Error occurred during captcha validation.");
                    }
                });
            } else {
                captchaValidation.classList.remove('valid');
                captchaValidation.classList.remove('invalid');
            }
        }
    </script>

    <style>
        .captcha-container {
            display: flex;
            align-items: center;
        }

        .captcha-input {
            height: 26px;
            margin-right: 10px;
        }

        .captcha-image {
            height: 26px;
            cursor: pointer;
        }

        .captcha-validation {
            display: inline-block;
            width: 20px;
            height: 20px;
            margin-left: 5px;
            background-size: contain;
            background-repeat: no-repeat;
        }

        .captcha-validation.invalid {
            background-image: url('images/red_cross.png'); /* Replace with your red cross image */
        }

        .captcha-validation.valid {
            background-image: url('images/green_check.png'); /* Replace with your green check image */
        }
    </style>

</head>
<body>
<h1>登录界面</h1>
<form action="LoginServlet">
    <div>
        <span>请输入用户名</span><input type='text' name='username'></input>
    </div>
    <div>
        <span>请输入校验码</span><input type='text' name='otp'></input>
    </div>
    <div class="captcha-container">
        <span>请输入图片验证码</span>
        <input type="text" name="captcha" id="captchaInput" class="captcha-input" maxlength="6"
               placeholder="Please Enter Captcha" onkeyup="checkCaptcha()">
        <img src="CaptchaServlet" alt="Captcha Image" id="captchaImage" class="captcha-image" onclick="changeCaptcha()">
        <span id="captchaValidation" class="captcha-validation"></span>
    </div>
    <input type="submit" content="提交">
</form>

</body>
</html>
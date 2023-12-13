<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function changeCaptcha() {
            var randomNumber = Math.random();
            var captchaImage = document.getElementById('captchaImage');
            captchaImage.src = "CaptchaServlet?rand=" + randomNumber;
        }

        function checkCaptcha() {
            var captchaInput = document.getElementById('captchaInput');
            var captchaValidation = document.getElementById('captchaValidation');

            if (captchaInput.value.length === 6) {
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
        body {
            font-family: Arial, sans-serif;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            max-width: 400px;
            margin: 0 auto;
        }

        div {
            margin-bottom: 15px;
        }

        span {
            display: block;
            margin-bottom: 5px;
        }

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

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
    </style>
</head>
<body>
<h1>Business Login</h1>
<form action="LoginServlet" method="post">
    <div>
        <span>Username:</span>
        <input type="text" name="username" required>
    </div>
    <div>
        <span>One-Time Password:</span>
        <input type="text" name="otp" required>
    </div>
    <div class="captcha-container">
        <span>Image Verification Code:</span>
        <input type="text" name="captcha" id="captchaInput" class="captcha-input" maxlength="6" placeholder="Enter Captcha" onkeyup="checkCaptcha()" required>
        <img src="CaptchaServlet" alt="Captcha Image" id="captchaImage" class="captcha-image" onclick="changeCaptcha()">
        <span id="captchaValidation" class="captcha-validation"></span>
    </div>
    <input type="submit" value="Submit">
</form>
</body>
</html>
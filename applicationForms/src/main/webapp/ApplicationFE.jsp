<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<meta charset="UTF-8">
<link rel="stylesheet" href="ApplicationCSS.css">
<title>Registration Form.</title>
</head>
<body>
	<div id="content_fg">
	<div id="content_bg"></div>
	<form action="Register" method="post">
		First Name<span class="imp">*</span>: &emsp;&emsp; <input type="text"  name="fname" required="required" placeholder="Enter first name." autofocus="autofocus"/><br>
		Last Name<span class="imp">*</span>: &emsp;&emsp; <input type="text" name="lname" required="required" placeholder="Enter last name."/><br>
		Aadhar Number<span class="imp">*</span>: <input class="noSpinners" type="number" name="uid" required="required" placeholder="Enter aadhar number." maxlength=12/><br>
		<input id="register" type="submit" name="register" value="Submit"/><br>
	</form>
	</div>
</body>
</html>
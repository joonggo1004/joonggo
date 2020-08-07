<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<title>메일 Form</title>
</head>
<body>

    <div>
        <form action="sendMail.jsp" method="post">
            <table>
                <tr><th colspan="2">JSP 메일 보내기</th></tr>
                <tr><td>from</td><td><input type="text" name="from" /></td></tr>
                <tr><td>to</td><td><input type="text" name="to" /></td></tr>
                <tr><td>subject</td><td><input type="text" name="subject" /></td></tr>
                <tr><td>content</td><td><textarea name="content" style="width:170px; height:200px;"></textarea></td></tr>
                <tr><td colspan="2" style="text-align:right;"><input type="submit" value="Transmission"/></td></tr>
            </table>
        </form>
    </div>

</body>
</html>
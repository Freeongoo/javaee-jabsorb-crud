<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Login page</title>
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
    <div class="wrapper">

        <form class="form-signin" action="login" method="post">
            <h2 class="form-signin-heading">Please login</h2>

            <input type="text" class="form-control" name="username" value="${requestScope.username}" placeholder="Login" required="" autofocus="" />
            <input type="password" class="form-control" name="password" placeholder="Password" required=""/>

            <c:if test="${requestScope.error != null}">
                <div class="alert alert-danger fade in alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close" title="close">×</a>
                    <strong>${requestScope.error}</strong>
                </div>
            </c:if>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
        </form>

    </div>
    <script src="js/common.js"></script>
</body>
</html>
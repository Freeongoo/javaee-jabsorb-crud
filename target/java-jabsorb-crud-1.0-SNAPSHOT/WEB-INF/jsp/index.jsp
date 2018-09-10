<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>SPA - user manager</title>
    <link href="css/app.css" rel="stylesheet">
</head>
<body>
    <div id="app">
        <div id="nav-content"></div>
        <div class="container">
            <div id="main-content"></div>
        </div>
    </div>

    <!-- Modal -->
    <div id="modal-content-past"></div>

    <script>
        window.CONTEXT = '<%= request.getContextPath() %>';
    </script>
    <script src="js/app.js"></script>
</body>
</html>

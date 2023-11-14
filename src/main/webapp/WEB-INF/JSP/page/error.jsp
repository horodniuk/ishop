<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
        }

        .container {
            margin: 10% auto;
            padding: 20px;
            max-width: 400px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        }

        h1 {
            color: #f00;
        }

        p {
            font-size: 18px;
        }

        a {
            color: #007BFF;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="alert alert-danger hidden-print" role="alert">
    <h1>Code: ${statusCode }</h1>
    <c:choose>
        <c:when test="${statusCode == 403}">You don't have permissions to view this resource</c:when>
        <c:when test="${statusCode == 404}">

            <div class="container">
                <h1>404 - Page Not Found</h1>
                <p>The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
                <p>Return to the <a href="/">home page</a>.</p>
            </div>

        </c:when>
        <c:otherwise>Can't process this request! Try again later...</c:otherwise>
    </c:choose>
</div>

</body>
</html>
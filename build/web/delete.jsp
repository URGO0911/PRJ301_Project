<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete product</title>
        <style>
            form {
                display: inline-block;
            }
            
            .error-msg {
                color: red;
            }
        </style>
    </head>
    <body>
        <h2>Are you sure to delete product id ${productID}</h2>
        <form action="product-delete" method="POST">
            <input type="hidden" name="pid" value="${productID}">
            <input type="submit" value="Submit delete">
        </form>
        <a href="manage-pro">
            <button type="button">Cancel</button>
        </a>
        <br>
        <p class="error-msg">${errMsg}</p>
    </body>
</html>

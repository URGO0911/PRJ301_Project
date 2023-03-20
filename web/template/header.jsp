<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dal.*" %>
<%@page import="java.util.*" %>
<%@page import="models.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <%
            String path = request.getContextPath();
        %>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <link href="<%=path%>/css/style.css" rel="stylesheet"/>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <a href="<%=path%>/index.jsp"><img src="<%=path%>/img/logo.png"/></a>
                </div>
                <form action="index.jsp">
                    <div class="wrap">
                        <div class="search">
                            <input style=" margin-top: 20px;margin-left: 300px" type="text" class="searchTerm" name="txtSearch" placeholder="Sreach a product ...">
                            <input style="margin-left: 300px" type="submit"  value="SEARCH">                           
                        </div>
                    </div>
                </form>
                <div id="banner">
                    <ul>
                        <c:choose>
                            <c:when test="${sessionScope.CartSession ne null}">
                                <li><a href="<%=path%>/cart.jsp">Cart: <%=((ArrayList<Product>)request.getSession().getAttribute("CartSession")).size()%></a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="<%=path%>/cart.jsp">Cart: 0</a></li>
                                </c:otherwise>
                            </c:choose>
                        <%
                            Account acc = (Account)request.getSession().getAttribute("AccSession");
                            if (acc == null){
                        %>    
                        <li><a href="<%=path%>/account/signin">SignIn</a></li>
                        <li><a href="<%=path%>/account/signup">SignUp</a></li>
                        <%
                            }else{
                        %>
                        <li><a href="customer-info?ID=${AccSession.getCustomerID()}">Profile</a></li>
                        <li><a href="<%=path%>/account/signin">SignOut</a></li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
            <c:if test="${AccSession.getRole() == 1}">
            <c:redirect url="dashboard.jsp"/>
            </c:if>
            <div id="content">
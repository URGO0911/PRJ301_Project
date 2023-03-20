
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<%@page import="java.util.ArrayList" %>
<%@page import="dal.*" %>
<%@page import="controllers.*" %>
<%@page import="models.*" %>
                <div id="content-left">
                    <h3 style="font-weight: normal;">Welcome, ${customer.getContactName()} </h3>
                    <h3>Account Management</h3>
                    <ul>
                        <a href="<%=path%>/customer-info?ID=${AccSession.getCustomerID()}"><li>Personal information</li></a>
                    </ul>
                    <h3>My order</h3>
                    <ul>
                        <a href="<%=path%>/profile1.jsp"><li>All orders</li></a>
                        <a href="<%=path%>/profile2.jsp""><li>Canceled order</li></a>
                    </ul>
                </div>
                <div id="content-right">
                    <div class="path">Personal information</b></div>
                    <div class="content-main">
                        <div id="profile-content">
                            <div class="profile-content-col">
                                <div>Company name: <br/>${customer.getCompanyName()}</div>
                                <div>Contact name: <br/>${customer.getContactName()}</div>
                                 <a style="background-color: sienna;
                                color: #fff;
                                line-height: 40px;
                                border-radius: 5px;
                                border: none;
                                cursor: pointer;
                                margin-top: 70px;
                                width: 40%;
                                text-decoration: none;
                                padding: 9px 42px;" href="editprofile">Edit</a>
                            </div>
                            <div class="profile-content-col">
                                <div>Company title: <br/>${customer.getContactTitle()}</div>
                                <div>Address: <br/>${customer.getAddress()}</div>
                            </div>
                            <div class="profile-content-col">
                                <div>Email: <br/>${AccSession.getEmail()}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                        
            <div id="footer">footer</div>
        </div>
    </body>
</html>

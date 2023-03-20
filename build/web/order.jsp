<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <link href="<%=path%>/css/style.css" rel="stylesheet"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    </head>
    <body>
    <c:if test="${AccSession.getRole() != 1}">
        <c:redirect url="index.jsp"/>
    </c:if>
    <div id="container">
        <div id="header">
            <div id="logo-admin">
                Ecommerce Admin
            </div>
            <div id="banner-admin">
                <ul>
                    <li><a href="<%=path%>/account/signin">SignOut</a></li>
                </ul>
            </div>
        </div>
        <div id="content">
            <div id="content-left">
                <ul>
                    <a href="dashboard.jsp"><li>Dashboard</li></a>
                    <a href="manage-order"><li>Orders</li></a>
                    <a href="manage-pro"><li>Products</li></a>
                    <a href="#"><li>Customers</li></a>
                </ul>
            </div>
            <div id="content-right">
                <div class="path-admin">ORDERS LIST</b></div>
                <div class="content-main">
                    <div id="content-main-dashboard">
                        <div id="order-title">
                            <b>Filter by Order date:</b>
                            <form action="manage-order">
                                From: <input type="date" name="txtStartOrderDate" value="${fromDate}"/>
                                To: <input type="date" name="txtEndOrderDate" value="${toDate}"/>
                                <input type="submit" value="Filter">
                            </form>
                        </div>
                        <div id="order-table">
                            <table id="orders">
                                <tr>
                                    <th>OrderID</th>
                                    <th>OrderDate</th>
                                    <th>RequiredDate</th>
                                    <th>ShippedDate</th>
                                    <th>Employee</th>
                                    <th>Customer</th>
                                    <th>Freight($)</th>
                                    <th>Status</th>
                                </tr>
                                <c:forEach items="${list}" var="item">
                                    <tr>
                                        <td><a href="${path}/order-detail.jsp?id=${item.getOrderID()}">#${item.getOrderID()}</a></td>
                                        <td>${item.getOrderDate()}</td>
                                        <td>${item.getRequiredDate()}</td>
                                        <td>${item.getShippedDate()}</td>
                                        <td>${EmployeeDAO.getEmployee(item.getEmployeeID()).getFullName()}</td>
                                        <td>${CustomerDAO.getCustomer(item.getCustomerID()).getContactName()}</td>
                                        <td>${item.getFreight()}</td>
                                    <c:choose>
                                        <c:when test="${item.getShippedDate() ne null}">
                                            <td style="color: green;">Completed</td>
                                        </c:when>
                                        <c:when test="${item.getShippedDate() eq null and item.getRequiredDate() ne null}">
                                            <td style="color: blue;">Pending | <a href="<%=request.getContextPath()%>/cancel-order?order-id=${item.getOrderID()}&page=${currentPage}&txtStartOrderDate=${fromDate}&txtEndOrderDate=${toDate}">Cancel</a></td>                               
                                        </c:when>
                                        <c:when test="${item.getRequiredDate() eq null}">
                                            <td style="color: red;">Order canceled</td>
                                        </c:when>
                                    </c:choose>
                                    </tr>
                                </c:forEach>           
                            </table>
                        </div>
                        <div id="paging">
                            <div class="pagination">
                                <c:choose>
                                    <c:when test="${numberOfPage ne 0}">
                                        <c:if test="${currentPage ne 1}">
                                            <a href="<%=path%>/manage-order?page=${currentPage-1}&txtStartOrderDate=${fromDate}&txtEndOrderDate=${toDate}"><strong>&xlarr;</strong></a>
                                        </c:if>
                                        <c:forEach begin="1" end="${numberOfPage}" var="i">
                                            <c:choose>
                                                <c:when test="${i eq currentPage}">
                                                    <a href="<%=path%>/manage-order?page=${i}&txtStartOrderDate=${fromDate}&txtEndOrderDate=${toDate}" class="active">${i}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<%=path%>/manage-order?page=${i}&txtStartOrderDate=${fromDate}&txtEndOrderDate=${toDate}">${i}</a>
                                                </c:otherwise>
                                            </c:choose>                                  
                                        </c:forEach>     
                                        <c:if test="${currentPage ne numberOfPage}">
                                            <a href="<%=path%>/manage-order=${currentPage+1}&txtStartOrderDate=${fromDate}&txtEndOrderDate=${toDate}"><strong>&xrarr;</strong></a>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="font-weight: 500; margin-bottom: 8px">No Result <span style='font-size:16px;'>&#128577;</span></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer-admin">footer</div>
    </div>
</body>
</html>
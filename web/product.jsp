<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href="css/style.css" rel="stylesheet"/>
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
                        <li><a href="<%=request.getContextPath()%>/account/signin">SignOut</a></li>
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
                    <div class="path-admin">PRODUCTS LIST</b></div>
                    <div class="content-main">
                        <div id="content-main-dashboard">
                            <div id="product-title-header">
                                <div id="product-title-1" style="width: 25%;">
                                    <b>Filter by Catetory:</b>
                                    <form action="manage-pro" method="get">
                                        <select name="category">
                                            <option value="-1" selected>None</option>
                                            <c:forEach items="${listc.listcate()}" var="c">
                                                <c:choose>
                                                    <c:when test="${c.getCategoryID() == categoryIdFilter}">
                                                        <option selected value="${c.getCategoryID()}">${c.getCategoryName()}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${c.getCategoryID()}">${c.getCategoryName()}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>                                           
                                        </select>
                                        <input type="submit" value="Filter">
                                        <!--</form>-->
                                        </div>
                                        <div id="product-title-2" style="width: 55%;">
                                            <!--<form action="manage" method="get">-->
                                            <input type="text" name="txtSearch" placeholder="Enter product name to search" value="${txtSearch}"/>
                                            <input type="submit" value="Search"/>
                                    </form>
                                </div>
                                <div id="product-title-3" style="width: 20%;">
                                    <a href="product-create">Create a new Product</a>
                                    <form action="">
                                        <label for="upload-file">Import .xls or .xlsx file</label>
                                        <input type="file" name="file" id="upload-file" />
                                    </form>
                                </div>
                            </div>
                            <div id="order-table-admin">
                                <table id="orders">
                                    <tr>
                                        <th>ProductID</th>
                                        <th>ProductName</th>
                                        <th>UnitPrice</th>
                                        <th>Unit</th>
                                        <th>UnitsInStock</th>
                                        <th>Category</th>
                                        <th>Discontinued</th>
                                        <th></th>
                                    </tr>
                                    <c:forEach items="${listpro}" var="p">

                                        <tr>
                                            <td><a href="order-detail.html?id=5"><a>${p.getProductID()}</a></td>
                                            <td>${p.getProductName()}</td>
                                            <td>${p.getUnitPrice()}</td>
                                            <td>${p.getQuantityPerUnit()}</td>
                                            <td>${p.getUnitsInStock()}</td>
                                            <td>${listc.getCateByid(p.getCategoryID()).getCategoryName()}</td>
                                            <td>${p.isDiscontinued()}</td>

                                            <td>
                                                <a href="product-edit?&pid=${p.getProductID()}">Edit</a> &nbsp; | &nbsp; 
                                                <a href="product-delete?&pid=${p.getProductID()}">Delete</a>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                    
                                </table>
                            </div>
                            <div id="paging">
                                <div class="pagination">
                                    <c:choose>
                                        <c:when test="${numberOfPage ne 0}">
                                            <c:if test="${currentPage ne 1}">
                                                <a href="manage-pro?page=${currentPage-1}&category=${categoryIdFilter}&txtSearch=${txtSearch}"><strong>&xlarr;</strong></a>
                                            </c:if>
                                            <c:forEach begin="1" end="${numberOfPage}" var="i">
                                                <c:choose>
                                                    <c:when test="${i eq currentPage}">
                                                        <a href="manage-pro?page=${i}&category=${categoryIdFilter}&txtSearch=${txtSearch}" class="active">${i}</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="manage-pro?page=${i}&category=${categoryIdFilter}&txtSearch=${txtSearch}">${i}</a>
                                                    </c:otherwise>
                                                </c:choose>                                  
                                            </c:forEach>     
                                            <c:if test="${currentPage ne numberOfPage}">
                                                <a href="manage-pro?page=${currentPage+1}&category=${categoryIdFilter}&txtSearch=${txtSearch}"><strong>&xrarr;</strong></a>
                                            </c:if>
                                        </c:when>
                                            <c:otherwise>
                                                <p style="font-weight: 500; margin-bottom: 8px">No Result </p>
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
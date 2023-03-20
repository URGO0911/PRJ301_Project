<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<%@page import="java.util.ArrayList" %>
<%@page import="dal.*" %>
<%@page import="controllers.*" %>
<%@page import="models.*" %>

<div id="content-left">
    <h3>CATEGORY</h3>
    <ul>
        <%
            String search = request.getParameter("txtSearch");
            request.setAttribute("search", search);
            ArrayList<Category> list = new CategoryDAO().listcate();
            ArrayList<Product> productlist = new ProductDAO().getProductListBySqlQuery("select * from Products\n"
                +"where ProductName like '%"+search+"%'");
                request.setAttribute("productlist", productlist);
            for(Category c: list) {
        %> 
        <a href="product?categoryID=<%=c.getCategoryID()%>&indexP=1"><li><%= c.getCategoryName()%></li></a>
                <%
          
           }
                %>
    </ul>

</div>

<div id="content-right">
        <c:choose>
            
            <c:when test="${search ne null && !search.isEmpty()}">
                <div class="content-main">
                    <c:forEach items="${productlist}" var="p">
                        <div class="product" style="width: 21%; margin: 10px 10px">
                            <a href="detail?id=${p.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                            <div class="name" ><a href="detail?id=${p.getProductID()}">${p.getProductName()}</a></div>
                            <div class="price" >$${p.getUnitPrice()}</div>
                            <div >
                                <div><a href="detail?id=${p.getProductID()}">Buy now</a></div>
                            </div>
                        </div>
                    </c:forEach>


                </div>
            </c:when>
            <c:otherwise>
                <%
                        request.removeAttribute("productlist");
                        productlist = new ProductDAO().getProductListBySqlQuery("SELECT TOP 4 Products.* "
                                + "FROM Products INNER JOIN ( SELECT ProductID, ROW_NUMBER() OVER( ORDER BY MAX(Discount) ASC)"
                                + " AS DiscountRank FROM [ORDER Details] GROUP BY ProductID) "
                                + "AS Ranker ON Ranker.ProductID = Products.ProductID WHERE UnitsInStock > 0 ORDER BY DiscountRank DESC");
                        request.setAttribute("productlist", productlist);               
                %>
                <div class="path">Hot</b></div>      
                <div class="content-main">
                    <c:forEach items="${productlist}" var="p">
                        <div class="product" style="width: 21%; margin: 10px 10px">
                            <a href="detail?id=${p.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                            <div class="name" ><a href="detail?id=${p.getProductID()}">${p.getProductName()}</a></div>
                            <div class="price" >$${p.getUnitPrice()}</div>
                            <div >
                                <div><a href="detail?id=${p.getProductID()}">Buy now</a></div>
                            </div>
                        </div>
                    </c:forEach>


                </div>
                <div class="path">Best Sale</b></div>
                <div class="content-main">
                    <%
                        request.removeAttribute("productlist");
                        productlist = new ProductDAO().getProductListBySqlQuery("select top 4 p.ProductID, p.CategoryID, p.ProductName, p.QuantityPerUnit, p.ReorderLevel, p.UnitPrice, p.UnitsInStock, p.UnitsOnOrder, p.Discontinued\n"
                        + "from Products p,\n"
                        + "(select top 4 od.ProductID, count(*) as NumberOfProduct\n"
                        + "from [Order Details] od\n"
                        + "group by od.ProductID\n"
                        + "order by NumberOfProduct desc) as od\n"
                        + "where p.ProductID = od.ProductID");
                        request.setAttribute("productlist", productlist);               
                    %>
                    <c:forEach items="${productlist}" var="p">
                        <div class="product" style="width: 21%; margin: 10px 10px">
                            <a href="detail?id=${p.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                            <div class="name" ><a href="detail?id=${p.getProductID()}">${p.getProductName()}</a></div>
                            <div class="price" >$${p.getUnitPrice()}</div>
                            <div >
                                <div><a href="detail?id=${p.getProductID()}">Buy now</a></div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="path">New Product</b></div>
                <div class="content-main">
                    <%
                        request.removeAttribute("productlist");
                        productlist = new ProductDAO().getProductListBySqlQuery("select top 4 p.ProductID, p.CategoryID, p.ProductName, p.QuantityPerUnit, p.ReorderLevel, p.UnitPrice, p.UnitsInStock, p.UnitsOnOrder, p.Discontinued\n"
                        + "from Products p,\n"
                        + "(select top 4 od.ProductID, count(*) as NumberOfProduct\n"
                        + "from [Order Details] od\n"
                        + "group by od.ProductID\n"
                        + "order by NumberOfProduct desc) as od\n"
                        + "where p.ProductID = od.ProductID");
                        request.setAttribute("productlist", productlist);               
                    %>
                    <c:forEach items="${productlist}" var="p">
                        <div class="product" style="width: 21%; margin: 10px 10px">
                            <a href="detail?id=${p.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                            <div class="name" ><a href="detail?id=${p.getProductID()}">${p.getProductName()}</a></div>
                            <div class="price" >$${p.getUnitPrice()}</div>
                            <div >
                                <div><a href="detail?id=${p.getProductID()}">Buy now</a></div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

<%@include file="template/footer.jsp" %>

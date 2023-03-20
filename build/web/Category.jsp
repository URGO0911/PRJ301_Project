<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.*" %>
<%@page import="java.util.ArrayList" %>
<%@page  import="dal.Category" %>
<%@page  import="dal.Product" %>

<%@include file="template/header.jsp" %>

<div id="content-left">
    <h3>CATEGORY</h3>
    
        <ul>
            <%
        ArrayList<Category> list = new CategoryDAO().listcate();
        
        for(Category ca: list) {
            %> 
            <a href="product?categoryID=<%= ca.getCategoryID()%>&indexP=1" ><li><%= ca.getCategoryName()%></li> </a>
                    <%
          
               }
                    %>
        </ul>
        
</div>
<div id="content-right">
    <div class="path">${Name}</b></div>
    <div class="content-main">
        <c:set value="${listp}" var="listp"/>
        <c:forEach items="${listp}" var="c">
        <div class="product">
            <a href="detail?id=${c.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
            <div class="name"><a href="detail?id=${c.getProductID()}">${c.getProductName()}</a></div>
            <div class="price"> $${c.getUnitPrice()}</div>
            <div><a href="detail?id=${c.getProductID()}">Buy now</a></div>         
        </div>
        </c:forEach>
    </div>
    <div id="paging">
            <div class="pagination">
                <c:if test="${index!=1}">
                    <a href="product?categoryID=${cateID}&indexP=${index-1}">&larr;</a>
                </c:if>
                <c:forEach begin="1" end="${endP}" var="i">
                    <c:if test="${index==i}">
                        <a class="active" href="product?categoryID=${cateID}&indexP=${i}">${i}</a>
                    </c:if>
                    <c:if test="${index!=i}">
                        <a href="product?categoryID=${cateID}&indexP=${i}">${i}</a>
                    </c:if>
                </c:forEach>
                <c:if test="${index!=endP}">
                    <a href="product?categoryID=${cateID}&indexP=${index+1}">&rarr;</a>
                </c:if>
            </div>
        </div>
</div>
<%@include file="template/footer.jsp" %>
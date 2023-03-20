<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../template/header.jsp" %>
<%@page  import="dal.*"%>
<%@page  import="models.*"%>
<%@page  import="java.util.*"%>
<c:if test="${sessionScope.AccSession eq null}">
    <c:redirect url="index.jsp"/>
</c:if>
<%   
    Customer c = new CustomerDAO().getCustomer(((Account)request.getSession().getAttribute("AccSession")).getCustomerID());
    ArrayList<Order> orderList = new OrderDAO().getallorderDeleted("select * from Orders where RequiredDate is null and CustomerID = '"+c.getCustomerID()+"'");
    request.setAttribute("c", c);  
    request.setAttribute("orders", orderList);
    request.setAttribute("oderDetailDAO", new OrderDetailsDAO());
    request.setAttribute("productDAO", new ProductDAO());
%>
<div id="content">
    <div id="content-left">
        <h3 style="font-weight: normal;">Welcome, ${c.getContactName()}</h3>
        <h3>Account Management</h3>
        <ul>
            <a href="<%=path%>/customer-info?ID=${AccSession.getCustomerID()}"><li>Personal information</li></a>
        </ul>
        <h3>My order</h3>
        <ul>
            <a href="profile1.jsp"><li>All orders</li></a>
            <a href="profile2.jsp"><li>Canceled order</li></a>
        </ul>
    </div>
    <div id="content-right">
        <div class="path">LIST ORDERS</b></div>
        <div class="content-main">

            <div id="profile-content-order">

                <c:forEach items="${orders}" var="o">


                    <div>
                        <div onclick="show(this)" class="profile-order-title">
                            <div class="profile-order-title-left">
                                <div>Order creation date: ${o.getOrderDate()}</div>
                                <div>Order: <a style="cursor: pointer; text-decoration: underline; color: cornflowerblue">#${o.getOrderID()}</a></div>
                            </div>


                            <div class="profile-order-title-right">
                                <span style="color: brown;">Canceled</span>
                            </div>



                        </div>
                        <c:set var="orderDetailList" value="${oderDetailDAO.getOrderDetails(o.getOrderID())}"/>
                        <!--<div class="order-detail">-->


                        <div class="profile-order-content" <c:if test="${orders.indexOf(o) ne 0}">style="display: none"</c:if>/>
                        <c:forEach items="${orderDetailList}" var="od"> 
                            <div style="display: flex; align-content: space-around">
                                <div class="profile-order-content-col1">
                                    <a href="detail.html"><img src="<%=path%>/img/2.jpg" width="100%"/></a>
                                </div>
                                <div class="profile-order-content-col2">${productDAO.getProduct(od.getProductID()).getProductName()}</div>
                                <div class="profile-order-content-col3">Quantity: ${od.getQuantity()}</div>
                                <div class="profile-order-content-col4">${od.getUnitPrice()} $</div>
                            </div>
                        </c:forEach>                                                         
                    </div>
                    <!--</div>-->
                </div>

            </c:forEach>    
            
        </div>
    </div>
</div>
</div>
<style>

    .profile-order-content{
        animation: show ease-out 1s;
    }
    @keyframes show {
        from {
            opacity: 0.1;
        }

        to {
            opacity: 1;
        }
    }
</style>
<script>
    function show(o) {
        console.log(o)
        if (o.nextElementSibling.style.display === "flex") {
            o.nextElementSibling.style.display = "none";
        } else {
            o.nextElementSibling.style.display = "flex";
        }
    }
</script>
<%@include file="./template/footer.jsp" %>
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
    ArrayList<Order> orderList = new OrderDAO().getOrders(c.getCustomerID());
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
        
            <div class="path">
                LIST ORDERS</br>
            </div>
        <div class="content-main">

            <div id="profile-content-order">

                <c:forEach items="${orders}" var="o">

                    <c:if test="${o.getRequiredDate() ne null}">
                        <div>
                            <div onclick="show(this)" class="profile-order-title" style="padding: 0;"
>
                                <div class="profile-order-title-left">
                                    <div>Order creation date: ${o.getOrderDate()}</div>
                                    <div>Order: <a style="cursor: pointer; text-decoration: underline; color: cornflowerblue">#${o.getOrderID()}</a></div>
                                </div>

                                <c:if test="${o.getShippedDate() eq null}">
                                    <div class="profile-order-title-right">
                                        <span>Pending</span>
                                    </div>
                                </c:if>

                                <c:if test="${o.getShippedDate() ne null}">
                                    <div class="profile-order-title-right">
                                        <span style="color: blue;">Completed</span>
                                    </div>
                                </c:if>


                            </div>
                            <c:set var="orderDetailList" value="${oderDetailDAO.getOrderDetails(o.getOrderID())}"/>
                            <div class="profile-order-content" <c:if test="${orders.indexOf(o) ne 0}">style="display: none; flex-wrap: wrap"</c:if>>
                            <c:forEach items="${orderDetailList}" var="od"> 
                                <div class style="display: flex ;flex-wrap: wrap; align-content: space-around">
                                    <div class="profile-order-content-col1"><a href="detail.html"><img src="<%=path%>/img/2.jpg" width="100%"/></a></div> 
                                    <div class="profile-order-content-col2">${productDAO.product(od.getProductID()).getProductName()}</div>
                                    <div class="profile-order-content-col3">Quantity: ${od.getQuantity()}</div>
                                    <div class="profile-order-content-col4">${od.getUnitPrice()} $</div>
                                </div>
                                
                            </c:forEach>    
                            <c:if test="${o.getShippedDate() == null}">
                                <a class="cancel-order" onclick="ask(this)"  style="font-size: 18px;
                                   float: right;
                                   width: 100%;
                                   text-align: right;
                                   margin: 10px;
                                   color: blue;
                                   font-weight: bold;">Cancel Order</a> 

                                <div class="success-signup overlay" onclick="hideAsk(this)" style="display: none">
                                    <div class="success-signup-content" style="background-color: brown">
                                        <p><strong>Are you sure to cancel this order?</strong><br/></p>
                                        <button class="visit-shop" style="margin-right: 8px; box-shadow: none; padding:0 8px; text-align: center"><a href="<%=request.getContextPath()%>/cancel-order?order-id=${o.getOrderID()}">Yes</a></button>
                                        <button class="visit-shop no" style="margin-right: 10px; box-shadow: none; padding:0 10px; text-align: center; cursor: pointer" onclick="hideAsk1(this)">No</button>
                                    </div>
                                </div>  
                            </c:if>                                      
                        </div>
                        <!--</div>-->
                    </div>
                </c:if>
            </c:forEach>
            
        </div>
    </div>
        
</div>
</div>
<style>
    /*    .success-signup.overlay{
            background-color: brown;
            width: 40%;
            margin: 16px auto;
            display: none;
        }*/
    .cancel-order:hover{
        opacity: 0.7;
        cursor: pointer;
    }

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

    function hideAsk1(i) {
        i.parentElement.parentElement.style.display = "none";
    }
    function hideAsk(i) {
        i.firstElementChild.onclick = function (e) {
            e.stopPropagation();
        }
        i.style.display = "none";
    }

    function ask(c) {
        if (c.nextElementSibling.style.display === "flex") {
            c.nextElementSibling.style.display = "none";
        } else {
            c.nextElementSibling.style.display = "flex";
        }
    }


//    overlay.addEventListener("click", closeModel);
//    no.addEventListener("click", closeModel);

</script>
<%@include file="./template/footer.jsp" %>
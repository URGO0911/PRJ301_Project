<%@include file="template/header.jsp" %>
<%@page import="dal.*" %>
<%@page import="models.*" %>
<%@page import="java.util.*" %>
<div id="content">
    <div id="cart">
        <div id="cart-title">
            <h3>SHOPPING CART</h3>
        </div>
        <%          
            Customer cus = null;
            try{
                cus = new CustomerDAO().getCustomer(acc.getCustomerID());
            }catch(Exception e){
                cus = null;
            }
            request.setAttribute("cus", cus);
        %>
        <c:set var="cart" value="${sessionScope.CartSession}"/>       
        <c:set var="cus" value="${cus}"/>
        <c:set var="size" value="${sessionScope.CartSession.size()}"/>
        <c:if test="${size != 0  && size != null}">

            <div id="cart-content">
                <c:forEach items="${cart}" var="c">
                    <div class="cart-item">
                        <div class="cart-item-infor">
                            <div class="cart-item-img">
                                <img src="<%=path%>/img/1.jpg"/>
                            </div>
                            <div class="cart-item-name">
                                <a href="detail.html?id=1">${c.getP().getProductName()}</a>
                            </div>
                            <div class="cart-item-price">
                                ${c.getP().getUnitPrice()} $
                            </div>
                            <div class="cart-item-button">
                                <a href="<%=path%>/cart-action?remove=true&productID=${c.getP().getProductID()}">Remove</a>
                            </div>
                        </div>
                        <div class="cart-item-function">
                            <a href="<%=path%>/cart-action?sub=true&productID=${c.getP().getProductID()}">-</a>  
                            <a href="<%=path%>/cart-action?add=true&productID=${c.getP().getProductID()}">+</a>
                            <input type="text" value="${c.getQuantity()}" disabled/>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div id="cart-summary">
                <%
                    double totalAmount = 0;
                    try{
                    for (CartItem item : (ArrayList<CartItem>)(request.getSession().getAttribute("CartSession")) ) {
                        totalAmount += item.getP().getUnitPrice()* item.getQuantity() ;
                    }
                    }catch(Exception e){
                        totalAmount = 0;
                    }
                %>
                <div id="cart-summary-content">Total amount: <span style="color:red"><%=(totalAmount)%> $</span></div>
            </div>
            <form action="cart-action" method="post">
                <div id="customer-info">
                    <div id="customer-info-content">
                        <h3>CUSTOMER INFORMATION:</h3>
                        <div id="customer-info-detail">
                            <div id="customer-info-left">
                                <label for="company-name">Company name *</label><br>
                                <input type="text" name="txtCompanyName" id="company-name" value="<c:out value="${cus eq null ? CompanyName : cus.getCompanyName()}"/>" <c:if test="${cus ne null}">disabled</c:if>/><br/>
                                <c:if test="${emptyCompanyName ne null}"><p style="color: red">${emptyCompanyName}</p></c:if>

                                    <label for="contact-name">Contact name *</label><br>
                                    <input type="text" name="txtContactName" id="contact-name" value="<c:out value="${cus eq null ? ContactName : cus.getContactName()}"/>" <c:if test="${cus ne null}">disabled</c:if>/>
                                <c:if test="${emptyContactName ne null}"><p style="color: red">${emptyContactName}</p></c:if>

                                <c:if test="${cus ne null}">
                                    <input type="button" style="background-color: sienna;
                                           text-align: center;
                                           color: #fff;
                                           /*line-height: 40px;*/
                                           border-radius: 5px;
                                           border: none;
                                           cursor: pointer;
                                           margin-top: 8px;
                                           width: 20%;
                                           text-decoration: none;"
                                           value="Edit">
                                </c:if>

                            </div>

                            <div id="customer-info-right">                              
                                <label for="contact-title">Contact title *</label><br>
                                <input type="text" name="txtContactTitle" id="contact-title" value="<c:out value="${cus eq null ? ContactTitle : cus.getContactTitle()}"/>" <c:if test="${cus ne null}">disabled</c:if>/><br/>
                                <c:if test="${emptyContactTitle ne null}"><p style="color: red">${emptyContactTitle}</p></c:if>

                                    <label for="address">Address *</label><br>
                                    <input type="text" name="txtAddress" id="address" value="<c:out value="${Address ne null and Address ne ' ' ? Address : cus.getAddress()}"/>" <c:if test="${cus ne null and (Address eq null or Address eq ' ')}">disabled</c:if>/><br/>
                                <c:if test="${emptyAddress ne null}"><p style="color: red">${emptyAddress}</p></c:if>

                                    <label for="required-date">Required Date *</label><br>
                                    <input id="required-date" type="date" name="txtRequiredDate" value="<c:out value="${RequiredDate}"/>"/><br/>
                                <c:if test="${emptyRequiredDate ne null}"><p style="color: red">${emptyRequiredDate}</p></c:if>
                                <c:if test="${invalidRequiredDate ne null}"><p style="color: red">${invalidRequiredDate}</p></c:if>
                                </div>                                              
                            </div>
                        </div>
                    </div>
                    <div id="customer-info">
                        <div id="customer-info-content">
                            <h3>PAYMENT METHODS:</h3>
                            <div id="customer-info-payment">
                                <div>
                                    <input type="radio" name="rbPaymentMethod" checked/>
                                    Payment C.O.D - Payment on delivery
                                </div>
                                <div>
                                    <input type="radio" name="rbPaymentMethod" disabled/>
                                    Payment via online payment gateway
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="cart-order">
                        <input type="submit" value="ORDER"/>
                    </div>
                </form>

            </div> 
    </c:if>   
    <c:if test="${size == 0 || size == null}">
        <div class="container-fluid  mt-100">
            <div class="row">

                <div class="col-md-12">

                    <div class="card">
                        
                        <div class="card-body cart">
                            <div class="col-sm-12 empty-cart-cls text-center">
                                <img src="https://i.imgur.com/dCdflKN.png" width="130" height="130" class="img-fluid mb-4 mr-3">
                                <h3><strong>Your Cart is Empty</strong></h3>
                                <h4>Add something to make me happy :)</h4>
                                <a href="index.jsp" class="btn btn-primary cart-btn-transform m-3" data-abc="true">continue shopping</a>


                            </div>
                        </div>
                    </div>


                </div>

            </div>

        </div>
    </c:if>
</div>
<script>
    var editBtn = document.querySelector("input[type=button]");
    var address = document.querySelector("#address");
    editBtn.onclick = function () {

        address.removeAttribute("disabled");

    }
</script>

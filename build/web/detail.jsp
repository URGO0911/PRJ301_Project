<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="dal.*" %>
<%@page import="controllers.*" %>
<%@page import="models.*" %>
<%@page import="java.util.ArrayList" %>
<%@include file="template/header.jsp" %>
    <%
        Product p = null;
        if(request.getAttribute("add-cart-status") != null){
            p = (Product)request.getAttribute("p");
            request.setAttribute("p", (Product)request.getAttribute("p"));
        }else{
            p = new ProductDAO().product(request.getParameter("id"));
            request.setAttribute("p", p);
        }
    %>
        <div id="content">
        
            <div id="content-detail">
                <div id="content-title">
                    <a href="index.jsp">Home</a> >
                    <a href="product?categoryID=${p.getCategoryID()}&indexP=1">Category ${p.getCategoryID()}</a> >
                    Model: SP1
                </div>
                <div id="product">
                    <div id="product-name">
                        
                        <h2>${p.getProductName()}</h2>
                        <div id="product-detail">
                            <div id="product-detail-left">
                                <div id="product-img">
                                    <img src="img/1.jpg"/>
                                </div>
                                <div id="product-img-items">
                                    <div><a href="#"><img src="img/1.jpg"/></a></div>
                                    <div><a href="#"><img src="img/1.jpg"/></a></div>
                                    <div><a href="#"><img src="img/1.jpg"/></a></div>
                                    <div><a href="#"><img src="img/1.jpg"/></a></div>
                                </div>
                            </div>
                            <div id="product-detail-right">
                        <form >
                            <div id="product-detail-right-content">
                                <div id="product-price">$${p.getUnitPrice()}</div>
                                <div id="product-status">In stock</div>
                                <div id="product-detail-buttons">
                                    <div id="product-detail-button">
                                        <a style="background-color: brown;
                                           color: #fff;
                                           margin-bottom: 15px;
                                           line-height: 40px;
                                           border-radius: 5px;
                                           border: none;
                                           cursor: pointer;
                                           padding: 10px 80px;
                                           text-decoration: none" href="<%=path%>/cart?productID=${p.getProductID()}&buy=true">BY NOW</a>
                                        <a style="background-color: #fff;
                                           color: red;
                                           border: 1px solid gray;
                                           width: 49%;
                                           font-weight: bold;
                                           text-decoration: none;
                                           border-radius: 5px;
                                           padding: 9px 45px;" href="<%=path%>/cart?productID=${p.getProductID()}">ADD TO CART</a>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                        </div>
                    </div>
                </div>
                <div id="info-detail">
                    <div id="info-detail-title">
                        <h2>Information deltail</h2>
                        <div style="margin:10px auto;">
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum, debitis. Asperiores soluta eveniet eos accusantium doloremque cum suscipit ducimus enim at sapiente mollitia consequuntur dicta quaerat, sunt voluptates autem. Quam!
                            Lorem ipsum dolor, sit amet consectetur adipisicing elit. Rem illum autem veritatis maxime corporis quod quibusdam nostrum eaque laborum numquam quos unde eveniet aut, exercitationem voluptatum veniam fugiat, debitis esse?
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio eligendi ratione vitae nobis numquam dolorum assumenda saepe enim cumque blanditiis, deleniti neque voluptate vel ducimus in omnis harum aut nisi.
                        </div>
                    </div>
                </div>
            </div>
        </div>
<%@include file="template/footer.jsp" %>
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

    <div id="container">
        <div id="header">
            <div id="logo-admin">
                Ecommerce Admin
            </div>
            <div id="banner-admin">
                <ul>
                    <li><a href="#">SignOut</a></li>
                </ul>
            </div>
        </div>
        <div id="content">
            <div id="content-left">
                <ul>
                    <a href="dashboard.jsp"><li>Dashboard</li></a>
                    <a href="order.jsp"><li>Orders</li></a>
                    <a href="product.jsp"><li>Products</li></a>
                    <a href="#"><li>Customers</li></a>
                </ul>
            </div>
            <div id="content-right">
                <div class="path-admin">CREATE A NEW PRODUCT</b></div>
                <div class="content-main">
                    <form action="product-create" method="post" id="content-main-product">
                        <div class="content-main-1">
                            <label>Product name (*):</label><br/>
                            <input type="text" name="ProductName" value="${product.productName}" />
                            
                            <label>Unit price:</label><br/>
                            <input type="text" name="UnitPrice" value="${product.unitPrice}" />
                            <label>Quantity per unit:</label><br/>
                            <input type="text" name="QuantityPerUnit" value="${product.quantityPerUnit}" />
                            <label>Units in stock (*):</label><br/>
                            <input type="text" name="UnitsInStock" value="${product.unitsInStock}" />
                            
                        </div>
                        <div class="content-main-1">
                            <label>Category (*):</label><br/>
                            <select name="CategoryID" >
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.categoryID}" <c:if test="${product.categoryID == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                            </c:forEach>
                            </select>
                            <br/>
                            
                            <label>Reorder level:</label><br/>
                            <input type="text" name="ReorderLevel" value="${product.reorderLevel}" />
                            <label>Units on order:</label><br/>
                            <input type="text" name="UnitsOnOrder" value="${product.unitsOnOrder}" disabled/>
                            <label>Discontinued:</label>
                            <input type="checkbox" name="Discontinued" ${product.discontinued ? "checked" : ""}/>
                            <br/>
                            <input type="submit" value="Create" />
                            <a href="manage-pro">
                            <button type="button">Cancel</button>
                            </a>
                        </div>
                    </form>
                    <p class="error-msg">${errMsg}</p>
                </div>
            </div>
        </div>
        <div id="footer-admin">footer</div>
    </div>
</body>
</html>
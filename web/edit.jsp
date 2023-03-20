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
        <style>
            .error-msg {
                color: red;
            }
        </style>
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
                        <a href="manage-pro"><li>Products</li></a>
                        <a href="#"><li>Customers</li></a>
                    </ul>
                </div>
                <div id="content-right">
                    
                        <h2>Editing product id ${product.productID}</h2>
                    
                    
                    <form action="product-edit" method="POST">
                        <input type="hidden" name="ProductID" value="${product.productID}">
                        <table>
                            <tr>
                                <td>Product Name</td>
                                <td><input type="text" name="ProductName" value="${product.productName}" /></td>
                            </tr>
                            <tr>
                                <td>Category</td>
                                <td>
                                    <select name="CategoryID" >
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category.categoryID}" <c:if test="${product.categoryID == category.categoryID}">selected</c:if>>${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Quantity Per Unit</td>
                                <td><input type="text" name="QuantityPerUnit" value="${product.quantityPerUnit}" /></td>
                            </tr>
                            <tr>
                                <td>Unit Price</td>
                                <td><input type="text" name="UnitPrice" value="${product.unitPrice}" /></td>
                            </tr>
                            <tr>
                                <td>Units In Stock</td>
                                <td><input type="number" name="UnitsInStock" value="${product.unitsInStock}" /></td>
                            </tr>
                            <tr>
                                <td>Units On Order</td>
                                <td><input type="number" name="UnitsOnOrder" value="${product.unitsOnOrder}" /></td>
                            </tr>
                            <tr>
                                <td>Reorder Level</td>
                                <td><input type="number" name="ReorderLevel" value="${product.reorderLevel}" /></td>
                            </tr>
                            <tr>
                                <td>Discontinued</td>
                                <td><input type="checkbox" name="Discontinued" ${product.discontinued ? "checked" : ""}/></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="submit" value="Submit edit" />
                                    <a href="manage-pro">
                                        <button type="button">Cancel</button>
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <p class="error-msg">${errMsg}</p>
                </div>
                <div id="footer-admin">footer</div>
            </div>
    </body>
</html>
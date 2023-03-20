<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.ArrayList" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="models.*" %>
<%@page import="dal.*" %>
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
    <%
    request.setAttribute("cD", new CustomerDAO());
    request.setAttribute("accD", new AccountDAO());
    request.setAttribute("oD", new OrderDAO());
    ArrayList<OrderDetails> odt = new OrderDetailsDAO().getWeeklySaleByOrdDet();
    double sum =0;
    for(OrderDetails o: odt) {
        sum += (o.getUnitPrice() * o.getQuantity());
    }
    DecimalFormat df = new DecimalFormat("$#0.00"); 
    ArrayList<OrderDetails> odtAll = new OrderDetailsDAO().getAllOrder_Details();
    double sumAll = 0;
    for(OrderDetails oA: odtAll) {
        sumAll += (oA.getUnitPrice() * oA.getQuantity());
    }
%>
<div id="content-right">
    <div class="path-admin">DASHBOARD</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div id="dashboard-1">
                <div id="dashboard-1-container">
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Weekly Sales</div>
                        <div class="dashboard-item-content"><%=df.format(sum)%></div>


                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Orders</div>
                        <div class="dashboard-item-content"><%=df.format(sumAll)%></div>
                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Customers</div>
                        <div class="dashboard-item-content">${cD.getCountAllCus()}</div>
                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Guest</div>
                        <div class="dashboard-item-content">${cD.getCountAllCus() - accD.getCountOfAllAccount()}</div>
                    </div>
                </div>
            </div>
            <div id="dashboard-2">
                <div id="chart" style="text-align: center;">
                    <div id="chart1">
                        <h3>Statistic Orders All Time (Month)</h3>
                        <canvas id="myChart1" style="width: 100%;"></canvas>
                    </div>
                    <div id="chart2">
                        <canvas id="myChart2" style="width: 80%;"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    
</div>
<div id="footer-admin">footer</div>
    
<script>
    function OrdersChart() {
        var xValues = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
        new Chart("myChart1", {
            type: "line",
            data: {
                labels: xValues,
                datasets: [{
                        data: [${oD.countOrderByMonth(1)}, ${oD.countOrderByMonth(2)}, ${oD.countOrderByMonth(3)},
                            ${oD.countOrderByMonth(4)}, ${oD.countOrderByMonth(5)}, ${oD.countOrderByMonth(6)},
                            ${oD.countOrderByMonth(7)}, ${oD.countOrderByMonth(8)}, ${oD.countOrderByMonth(9)},
                            ${oD.countOrderByMonth(10)}, ${oD.countOrderByMonth(11)}, ${oD.countOrderByMonth(12)}],
                        borderColor: "sienna",
                        fill: true
                    }]
            },
            options: {
                legend: {display: false}
            }
        });
    }
    function CustomersChart() {
        var xValues = ["Total", "New customer"];
        var yValues = [${cD.getCountAllCus()}, ${cD.getCountNewCus()}];
        var barColors = ["green", "red"];
        new Chart("myChart2", {
            type: "bar",
            data: {
                labels: xValues,
                datasets: [{
                        backgroundColor: barColors,
                        data: yValues
                    }]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: "New Customers (30 daily Avg)"
                }
            }
        });
    }
    OrdersChart();
    CustomersChart();
</script>
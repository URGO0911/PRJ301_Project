package models;

import dal.DBContext;
import dal.OrderDetails;
import dal.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDetailsDAO extends DBContext {
    public ArrayList<OrderDetails> getWeeklySaleByOrdDet() {
        ArrayList<OrderDetails> ord = new ArrayList<>();
        LocalDate curD = java.time.LocalDate.now();
        String date = curD.toString();
        // Create : PrepareStatement
        try {
            String sql = "select od.OrderID, od.ProductID, od.UnitPrice, od.Quantity, od.Discount from [Order Details]  od\n"
                    + "join Orders o\n"
                    + "on o.OrderID = od.OrderID\n"
                    + "where ? - o.OrderDate < 7";
            PreparedStatement ps = connection.prepareStatement(sql);

            //Set value
            ps.setString(1, date);

            // Implement statement
            ResultSet rs = ps.executeQuery();

            // Progress result returned
            while (rs.next()) {
                // Take data from 'rs' to biencucbo
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                double UnitPrice = rs.getDouble("UnitPrice");
                int Quantity = rs.getInt("Quantity");
                double Discount = rs.getDouble("Discount");
                // Intinilizial object 
                OrderDetails ordt = new OrderDetails(OrderID, ProductID, UnitPrice, Quantity, Discount);
                // Add p to arraylist
                ord.add(ordt);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return ord;
    }
    public ArrayList<OrderDetails> getAllOrder_Details() {
        ArrayList<OrderDetails> ordetail = new ArrayList<>();

        // Create : PrepareStatement
        try {
            String sql = "Select * from [Order Details]";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            // Implement statement........
            ResultSet rs = ps.executeQuery();

            // Progress result returned
            while (rs.next()) {
                // Take data from 'rs' to biencucbo
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                double UnitPrice = rs.getDouble("UnitPrice");
                int Quantity = rs.getInt("Quantity");
                double Discount = rs.getDouble("Discount");
                // Intinilizial object 
                OrderDetails ordt = new OrderDetails(OrderID, ProductID, UnitPrice, Quantity, Discount);
                // Add p to arraylist
                ordetail.add(ordt);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return ordetail;
    }
    public ArrayList<OrderDetails> getOrderDetailsByPID(int pid) {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Order Details] WHERE ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("OrderID");
                int productID = rs.getInt("ProductID");
                double unitPrice = rs.getDouble("UnitPrice");
                int quantity = rs.getInt("Quantity");
                double discount = rs.getDouble("Discount");
                orderDetailsList.add(new OrderDetails(1, 1, 1, 1, 1));
            }
        } catch (SQLException e) {
        }
        return orderDetailsList;
    }
    
    public static void main(String[] args) {
        System.out.println(new OrderDetailsDAO().getOrderDetailsByPID(76));
    }
    
    public ArrayList<OrderDetails> getOrderDetails(int orderID) {
        ArrayList<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
        try {
            String sql = "select * from [Order Details]\n"
                    + "where OrderID = ?";

            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(orderID));
            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                double UnitPrice = rs.getDouble("UnitPrice");
                int Quantity = rs.getInt("Quantity");
                double Discount = rs.getDouble("Discount");

                OrderDetails od = new OrderDetails(OrderID, ProductID, UnitPrice, Quantity, Discount);
                orderDetailList.add(od);
            }
        } catch (SQLException e) {
        }
        return orderDetailList;
    }
    
    public int addOrderDetail(OrderDetails od) {
        int status = 0;
        try {
            String sql = "insert into [Order Details]\n"
                    + "values(?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(od.getOrderID()));
            ps.setString(2, String.valueOf(od.getProductID()));
            ps.setString(3, String.valueOf(od.getUnitPrice()));
            ps.setString(4, String.valueOf(od.getQuantity()));
            ps.setString(5, String.valueOf(od.getDiscount()));

            status = ps.executeUpdate();

        } catch (Exception e) {
        }
        return status;
    }
}

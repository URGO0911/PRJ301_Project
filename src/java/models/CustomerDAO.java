
package models;

import dal.Customer;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class CustomerDAO extends DBContext {
    public int getCountAllCus() {
        // Create : PrepareStatement
        try {
            String sql = "Select count(*) from Customers";
            PreparedStatement ps = connection.prepareStatement(sql);

            // Implement statement
            ResultSet rs = ps.executeQuery();

            // Progress result returned
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return 0;
    }public int getCountNewCus() {
        // Create : PrepareStatement
        LocalDate curD = java.time.LocalDate.now();
        String date = curD.toString();
        try {
            String sql = "select count(*) from Customers\n"
                    + "where ? - Date < 30";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, date);
            // Implement statement
            ResultSet rs = ps.executeQuery();

            // Progress result returned
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return 0;
    }
    public Customer getCustomer(String customerID) {
        Customer c = null;
        try {
            String sql = "select * from Customers\n"
                    + "where CustomerID =?";
            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            //Set giá trị cho các tham số của 'sql'
            ps.setString(1, customerID);
            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                String CustomerID = rs.getString("CustomerID");
                String CompanyName = rs.getString("CompanyName");
                String ContactName = rs.getString("ContactName");
                String ContactTitle = rs.getString("ContactTitle");
                String Address = rs.getString("Address");

                c = new Customer(CustomerID, CompanyName, ContactName, ContactTitle, Address);
            }
        } catch (SQLException e) {
        }
        return c;
    }

    public int addCustomer(Customer c) {
        int i = 0;
        try {
            String sql = "insert into Customers\n"
                    + "values(?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCustomerID());
            ps.setString(2, c.getCompanyName());
            ps.setString(3, c.getContactName());
            ps.setString(4, c.getContactTitle());
            ps.setString(5, c.getAddress());
            i = ps.executeUpdate();
        } catch (Exception e) {
        }
        return i;
    }

    public int updateCustomer(Customer c) {
        int i = 0;
        try {
            String sql = "update Customers\n"
                    + "set CompanyName =?, ContactName =?, ContactTitle =?, Address =?\n"
                    + "where CustomerID =?;";
            PreparedStatement ps = connection.prepareStatement(sql);          
            ps.setString(1, c.getCompanyName());
            ps.setString(2, c.getContactName());
            ps.setString(3, c.getContactTitle());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getCustomerID());
            i = ps.executeUpdate();
        } catch (Exception e) {
        }
        return i;
    }

    public static void main(String[] args) {
        Customer c = new CustomerDAO().getCustomer("FAMIA");
        System.out.println(c);
    }
}

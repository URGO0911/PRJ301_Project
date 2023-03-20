package models;

import dal.Account;
import dal.Customer;
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO extends DBContext {
    public int getCountOfAllAccount() {
        

        // Create : PrepareStatement
        try {
            String sql = "Select count(*) from Accounts";
            PreparedStatement ps = connection.prepareStatement(sql);

            //Set value
            // Implement statement
            ResultSet rs = ps.executeQuery();

            // Progress result returned
            while (rs.next()) {
                // Take data from 'rs' to biencucbo
               return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return 0;
    }
    public Account getAccount(String email, String pass) {
        Account acc = null;
        try {
            String sql = "select * from Accounts where Email=? and Password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int AccountID = rs.getInt("AccountID");
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                int Role = rs.getInt("Role");
                acc = new Account(AccountID, Email, Password, CustomerID, EmployeeID, Role);
            }
        } catch (Exception e) {
        }
        return acc;
    }

    private String randomString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public int AddAccount(Account acc, Customer cust) {
        int result1 = 0, result2 = 0;
        try {
            String sql1 = "insert into Customers(CustomerID, CompanyName, ContactName, ContactTitle, Address) values(?,?,?,?,?);";
            String sql2 = "insert into Accounts(Email, Password, CustomerID,Role) values(?,?,?,?);";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setString(1, cust.getCustomerID());
            ps1.setString(2, cust.getCompanyName());
            ps1.setString(3, cust.getContactName());
            ps1.setString(4, cust.getContactTitle());
            ps1.setString(5, cust.getAddress());
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setString(1, acc.getEmail());
            ps2.setString(2, acc.getPassword());
            ps2.setString(3, acc.getCustomerID());
            ps2.setInt(4, 2);
            result1 = ps1.executeUpdate();
            result2 = ps2.executeUpdate();
        } catch (SQLException e) {

        }
        if (result1 > 0 && result2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }
   
    public ArrayList<String> getListEmail() {
        ArrayList<String> listEmail = new ArrayList<>();
        try{
            String sql = "select Email from Accounts";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                listEmail.add(rs.getString("Email"));
            }
        }catch(Exception e){
            
        }
        
        return listEmail;
    }
    public static void main(String[] args) {
        System.out.println(new AccountDAO().getAccount("cust1@gmail.com", "123").getRole());
    }
}

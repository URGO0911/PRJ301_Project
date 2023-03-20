package models;

import dal.Category;
import dal.DBContext;
import jakarta.servlet.http.HttpServlet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO extends DBContext {

    public ArrayList<Category> listcate() {
        ArrayList<Category> categories = new ArrayList<>();

        try {
            String sql = "Select * from Categories";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
            int CategoryID = rs.getInt("CategoryID");
            String CategoryName = rs.getString("CategoryName");
            String Description = rs.getString("Description");
            String Picture = rs.getString("Picture");
            categories.add(new Category(CategoryID, CategoryName, Description, Picture));
            }

            } catch (SQLException e) {
//            System.out.println(e.getMessage());
            }
        return categories;
    }
    public Category getCateByid(int id) {
        

        try {
            String sql = "select * from Categories where CategoryID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
            int CategoryID = rs.getInt("CategoryID");
            String CategoryName = rs.getString("CategoryName");
            String Description = rs.getString("Description");
            String Picture = rs.getString("Picture");
            return new Category(CategoryID, CategoryName, Description, Picture);
            }

            } catch (SQLException e) {
//            System.out.println(e.getMessage());
            }
        return null;
    }
    public static void main(String[] args) {
        ArrayList<Category> list = new CategoryDAO().listcate();
        for (Category category : list) {
            System.out.println(category);
        }
    }
}


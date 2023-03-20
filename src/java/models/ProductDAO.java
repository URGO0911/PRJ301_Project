
package models;

import dal.DBContext;
import dal.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductDAO extends DBContext{
    public ArrayList<Product> listpro(int idc){
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "Select * from Products where CategoryID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                products.add(new Product(
                         rs.getInt(1)
                        , rs.getString(2)
                        , rs.getInt(3)
                        , rs.getString(4)
                        , rs.getDouble(5)
                        , rs.getInt(6)
                        , rs.getInt(7)
                        , rs.getInt(8)
                        , rs.getBoolean(9)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
    public ArrayList<Product> Allpro(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "Select * from Products";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                products.add(new Product(
                         rs.getInt(1)
                        , rs.getString(2)
                        , rs.getInt(3)
                        , rs.getString(4)
                        , rs.getDouble(5)
                        , rs.getInt(6)
                        , rs.getInt(7)
                        , rs.getInt(8)
                        , rs.getBoolean(9)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
    public ArrayList<Product> getProductListBySqlQuery(String sql) {
        ArrayList<Product> list = new ArrayList<Product>();

        try {
            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);

            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");

                //Khởi tạo đối tượng kiểu Product
                Product p = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);

                //Bổ sung 'p' vào 'products'
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public int createProduct(Product product) {
        try {
            String sql = "INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getCategoryID());
            ps.setString(3, product.getQuantityPerUnit());
            ps.setDouble(4, product.getUnitPrice());
            ps.setInt(5, product.getUnitsInStock());
            ps.setInt(6, product.getUnitsOnOrder());
            ps.setInt(7, product.getReorderLevel());
            ps.setBoolean(8, product.isDiscontinued());
            return ps.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
    public ArrayList<Product> getProductsAtPage(int pageNumber, int itemsPerPage) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Products ORDER BY ProductID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (pageNumber - 1) * itemsPerPage);
            ps.setInt(2, itemsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                products.add(new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));
            }
        } catch (SQLException e) {
        }
        return products;
    }
    public ArrayList<Product> getProductsByKeyword(String keyword) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Products WHERE ProductName LIKE ? ORDER BY ProductID DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                products.add(new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));
            }
        } catch (SQLException e) {
        }
        return products;
    }
    public Product product(String idc){
        
        try {
            String sql = "select * from Products where ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, idc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                return new Product(
                         rs.getInt(1)
                        , rs.getString(2)
                        , rs.getInt(3)
                        , rs.getString(4)
                        , rs.getDouble(5)
                        , rs.getInt(6)
                        , rs.getInt(7)
                        , rs.getInt(8)
                        , rs.getBoolean(9));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Product getProduct(int productID) {
        ArrayList<Product> list = new ProductDAO().Allpro();
        for (Product item : list) {
            if (item.getProductID() == productID) {
                return item;
            }
        }
        return null;
    }
    public boolean isProductExist(int id) {
        return getProductById(id) != null;
    }
    public int deleteProduct(int id) {
        try {
            String sql = "DELETE FROM Products WHERE ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
    public ArrayList<Product> getProductsByPageWithProductNameAndIDCate(int pageTh, int sizePerPage, String text, int categoryID) {
        ArrayList<Product> list = new ArrayList<Product>();
        int offset = pageTh * sizePerPage - sizePerPage;

        try {
            String sql = "SELECT * FROM Products\n"
                    + "where ProductName LIKE ? and CategoryID = ?\n"
                    + "Order by ProductID\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
//            System.out.println(sql);
            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, text);
            ps.setString(2, String.valueOf(categoryID));
            ps.setInt(3, offset);
            ps.setInt(4, sizePerPage);
            System.out.println(text);

            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");

                //Khởi tạo đối tượng kiểu Product
                Product p = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);

                //Bổ sung 'p' vào 'products'
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public ArrayList<Product> getProductsByPageWithProductName(int pageTh, int sizePerPage, String text) {
        ArrayList<Product> list = new ArrayList<Product>();
        int offset = pageTh * sizePerPage - sizePerPage;

        try {
            String sql = "SELECT * FROM Products\n"
                    + "where ProductName LIKE ?\n"
                    + "Order by ProductID\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
//            System.out.println(sql);
            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, text);
            ps.setInt(2, offset);
            ps.setInt(3, sizePerPage);
            System.out.println(text);

            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");

                //Khởi tạo đối tượng kiểu Product
                Product p = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);

                //Bổ sung 'p' vào 'products'
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public ArrayList<Product> getProductsByPageWithCategoryID(int pageTh, int sizePerPage, int categoryID) {
        ArrayList<Product> list = new ArrayList<Product>();
        int offset = pageTh * sizePerPage - sizePerPage;

        try {
            String sql = "SELECT * FROM Products\n"
                    + "where CategoryID = ?\n"
                    + "Order by ProductID\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";

            //Buoc 2: Tạo đối tượng PrepareStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(categoryID));
            ps.setInt(2, offset);
            ps.setInt(3, sizePerPage);

            //Buoc 3: Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            //Buoc 4: Xử lý kết quả trả về
            while (rs.next()) {
                //Lấy dữ liệu từ ResultSet gán cho các biến cục bộ
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");

                //Khởi tạo đối tượng kiểu Product
                Product p = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);

                //Bổ sung 'p' vào 'products'
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public Product getProductById(int id) {
        try {
            String sql = "SELECT * FROM Products WHERE ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                return new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
            }
        } catch (SQLException e) {
        }
        return null;
    }
    public int updateProduct(Product product) {
        try {
            String sql = "UPDATE Products SET ProductName = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, UnitsInStock = ?, UnitsOnOrder = ?, ReorderLevel = ?, Discontinued = ? WHERE ProductID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getCategoryID());
            ps.setString(3, product.getQuantityPerUnit());
            ps.setDouble(4, product.getUnitPrice());
            ps.setInt(5, product.getUnitsInStock());
            ps.setInt(6, product.getUnitsOnOrder());
            ps.setInt(7, product.getReorderLevel());
            ps.setBoolean(8, product.isDiscontinued());
            ps.setInt(9, product.getProductID());
            return ps.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
    public int getTotalProduct(int cateID) {
        try {
            String sql = "select count(*) from Products where CategoryID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cateID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }
    public static void main(String[] args) {
        ArrayList<Product> list = new ProductDAO().Allpro();
        
            
        
    }
}

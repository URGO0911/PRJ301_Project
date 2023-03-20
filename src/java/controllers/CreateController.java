package controllers;

import dal.Category;
import dal.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import models.CategoryDAO;
import models.ProductDAO;

public class CreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Category> categories = new CategoryDAO().listcate();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("create-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String ProductName = req.getParameter("ProductName");
            int CategoryID = Integer.parseInt(req.getParameter("CategoryID"));
            String QuantityPerUnit = req.getParameter("QuantityPerUnit");
            double UnitPrice = Double.parseDouble(req.getParameter("UnitPrice"));
            int UnitsInStock = Integer.parseInt(req.getParameter("UnitsInStock"));
            int UnitsOnOrder = 0;
            int ReorderLevel = Integer.parseInt(req.getParameter("ReorderLevel"));
            boolean Discontinued = "on".equalsIgnoreCase(req.getParameter("Discontinued"));
            
            if (ProductName.isEmpty() ) {
                throw new Exception();
                
            }

            Product product = new Product(0, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
            int result = new ProductDAO().createProduct(product);
            if (result>0) {
                resp.sendRedirect(req.getContextPath()+"/manage-pro");
            }else{
                throw new Exception();
            }
            
            
            
        } catch (Exception e) {
            ArrayList<Category> categories = new CategoryDAO().listcate();
            req.setAttribute("categories", categories);
            req.setAttribute("errMsg", "Create product failed!");
            req.getRequestDispatcher("create-product.jsp").forward(req, resp);
        }
    }
    
    public static void main(String[] args) {
        Product p = new Product(0, "chailosfd", 1, "Ád", 0, 0, 0, 0, true);
        int result = new ProductDAO().createProduct(p);
        System.out.println(result);
    }
    
}

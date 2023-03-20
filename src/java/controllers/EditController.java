package controllers;

import dal.Category;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import models.CategoryDAO;
import models.ProductDAO;

public class EditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("pid"));
            Product product = new ProductDAO().getProductById(id);
            ArrayList<Category> categories = new CategoryDAO().listcate();
            if (product != null) {
                req.setAttribute("product", product);
                req.setAttribute("categories", categories);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("manage-pro");
            }
        } catch (Exception e) {
            resp.sendRedirect("manage-pro");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int ProductID = Integer.parseInt(req.getParameter("ProductID"));
            String ProductName = req.getParameter("ProductName");
            int CategoryID = Integer.parseInt(req.getParameter("CategoryID"));
            String QuantityPerUnit = req.getParameter("QuantityPerUnit");
            double UnitPrice = Double.parseDouble(req.getParameter("UnitPrice"));
            int UnitsInStock = Integer.parseInt(req.getParameter("UnitsInStock"));
            int UnitsOnOrder = Integer.parseInt(req.getParameter("UnitsOnOrder"));
            int ReorderLevel = Integer.parseInt(req.getParameter("ReorderLevel"));
            boolean Discontinued = "on".equalsIgnoreCase(req.getParameter("Discontinued"));
            
            if (ProductName.isEmpty() || QuantityPerUnit.isEmpty() || UnitPrice < 0 || UnitsInStock < 0 || UnitsOnOrder < 0 || ReorderLevel < 0) {
                throw new ServletException();
            }

            Product product = new Product(ProductID, ProductName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
            int result = new ProductDAO().updateProduct(product);
            if (result > 0) {
                resp.sendRedirect("manage-pro");
            } else {
                throw new SQLException();
            }
        } catch (Exception e) {
            int ProductID = Integer.parseInt(req.getParameter("ProductID"));
            Product productOld = new ProductDAO().getProductById(ProductID);
            ArrayList<Category> categories = new CategoryDAO().listcate();
            req.setAttribute("product", productOld);
            req.setAttribute("categories", categories);
            req.setAttribute("errMsg", "Edit product failed!");
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        }
    }

}

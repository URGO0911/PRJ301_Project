package controllers;

import dal.Category;
import dal.OrderDetails;
import dal.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import models.OrderDetailsDAO;
import models.ProductDAO;

public class DeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("pid"));
            req.setAttribute("productID", id);
            
            if (new ProductDAO().isProductExist(id)) {
                req.getRequestDispatcher("delete.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("manage-pro");
            }
        } catch (Exception e) {
            resp.sendRedirect("manage-pro");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "";
        try {
            int id = Integer.parseInt(req.getParameter("pid"));   
            
            ArrayList<OrderDetails> orderDetailsList = new OrderDetailsDAO().getOrderDetailsByPID(id);
            if (orderDetailsList.size() > 0) {
                msg = "This product is in " + orderDetailsList.size() + " order, cannot delete!";
                throw new SQLException();
            }  
            
            int result = new ProductDAO().deleteProduct(id);
            if (result == 0) {
                msg = "Delete product failed!";
                throw new SQLException();
            }
            
            
            resp.sendRedirect("manage-pro");
        } catch (Exception e) {
            int id = Integer.parseInt(req.getParameter("pid"));
            req.setAttribute("productID", id);
            req.setAttribute("errMsg", msg);
            req.getRequestDispatcher("delete.jsp").forward(req, resp);
        }
    }
    
    
    
}

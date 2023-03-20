
package controllers;

import dal.Category;
import dal.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.CategoryDAO;
import models.ProductDAO;


public class ListProduct extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("categoryID"));
        
        int pageTh = Integer.parseInt(req.getParameter("indexP"));
        
        req.setAttribute("index", pageTh);
        int numberOfProduct = 4;
        ArrayList<Product> listPPaging = new ProductDAO().getProductsByPageWithCategoryID(pageTh , numberOfProduct , id);
        ArrayList<Category> listCate = new CategoryDAO().listcate();
        String cateName = getCategoryName(id, listCate);
        req.setAttribute("Name", cateName);
        req.setAttribute("cateID", id);
        
        req.setAttribute("listp", listPPaging);
        int count = new ProductDAO().getTotalProduct(id);
        int endPage = count / numberOfProduct;
        if (count % numberOfProduct != 0) {
            endPage++;
        }
        req.setAttribute("endP", endPage);
        req.getRequestDispatcher("Category.jsp").forward(req, resp);
    }
    public String getCategoryName(int cateID, ArrayList<Category> list) {
        String cateName = "";
        for (Category c : list) {
            if (c.getCategoryID() == cateID) {
                cateName = c.getCategoryName();
            }
        }
        return cateName;
    }

    
    
}

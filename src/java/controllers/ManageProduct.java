/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


public class ManageProduct extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int categoryIdFilter =0;
        try {
            categoryIdFilter = Integer.parseInt(req.getParameter("category"));
        } catch (Exception e) {
            categoryIdFilter = -1;
        }
        String txtSearch;
        boolean isEmtySearch = true;
        try {
            txtSearch = req.getParameter("txtSearch");
            isEmtySearch = txtSearch.isEmpty();
        } catch (Exception e) {
            txtSearch = "";
        }
        int pageTh;
        try {
            pageTh = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) {
            pageTh = 1;
        }
        
        final int sizePerPage = 6;
        int numberOfPage = 0;
        int listSize = 0;
        
        ArrayList<Category> listc = new CategoryDAO().listcate();
        ArrayList<Product> listpro = null;
        listpro =  new ProductDAO().Allpro();
        listSize = new ProductDAO().Allpro().size();
        listpro = new ProductDAO().getProductsAtPage(pageTh, sizePerPage);
        if (categoryIdFilter != -1) {
            listpro = new ProductDAO().getProductsByPageWithCategoryID(pageTh, sizePerPage, categoryIdFilter);
            listSize = getListSizeByCategoryID(categoryIdFilter);
        }

        if (!isEmtySearch) {
            listpro = new ProductDAO().getProductsByPageWithProductName(pageTh, sizePerPage, "%" + txtSearch + "%");
            listSize = getListSizeByProductName(txtSearch);
        }

        if (!isEmtySearch && categoryIdFilter != -1) {
            listpro = new ProductDAO().getProductsByPageWithProductNameAndIDCate(pageTh, sizePerPage, "%" + txtSearch + "%", categoryIdFilter);
            listSize = getListSizeByProductNameAndIdCate(txtSearch, categoryIdFilter);
        }
        numberOfPage = numberOfPage(sizePerPage, listSize);
        req.setAttribute("listc", new CategoryDAO());
        req.setAttribute("categoryIdFilter", categoryIdFilter);
        req.setAttribute("txtSearch", txtSearch);
        req.setAttribute("listpro", listpro);
        req.setAttribute("currentPage", pageTh);
        req.setAttribute("txtSearch", txtSearch);
        req.setAttribute("numberOfPage", numberOfPage);
        req.getRequestDispatcher("/product.jsp").forward(req, resp);
    }
    private int numberOfPage(int numberOfItem, int listSize) {
        if (listSize % numberOfItem == 0) {
            return listSize / numberOfItem;
        } else {
            return listSize / numberOfItem + 1;
        }
    }

    

    private int getListSizeByCategoryID(int categoryFilter) {
        int count = 0;
        ArrayList<Product> list = new ProductDAO().Allpro();
        ArrayList<Product> listpro = new ArrayList<Product>();
        for (Product item : list) {
            if (item.getCategoryID() == categoryFilter) {
                count++;
            }
        }
        return count;
    }

    private int getListSizeByProductName(String txtSearch) {
        int count = 0;
        ArrayList<Product> list = new ProductDAO().Allpro();
        ArrayList<Product> listpro = new ArrayList<Product>();
        for (Product item : list) {
            if (item.getProductName().toLowerCase().contains(txtSearch.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    private int getListSizeByProductNameAndIdCate(String txtSearch, int categoryFilter) {
        int count = 0;
        ArrayList<Product> list = new ProductDAO().Allpro();
        ArrayList<Product> listpro = new ArrayList<Product>();
        for (Product item : list) {
            if (item.getProductName().toLowerCase().contains(txtSearch.toLowerCase()) && item.getCategoryID() == categoryFilter) {
                count++;
            }
        }
        return count;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dal.Account;
import dal.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.CustomerDAO;

/**
 *
 * @author Admin
 */
public class EditProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        id =((Account) req.getSession().getAttribute("AccSession")).getCustomerID();
        Customer c = new CustomerDAO().getCustomer(id);
        req.setAttribute("customer", c);
        req.getRequestDispatcher( "editProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyName = req.getParameter("company-name");
        String contactName = req.getParameter("contact-name");
        String contactTitle = req.getParameter("contact-title");
        String address = req.getParameter("address");
        Customer c = new Customer(((Account) req.getSession().getAttribute("AccSession")).getCustomerID(), companyName, contactName, contactTitle, address);
        if (new CustomerDAO().updateCustomer(c) != 0) {
            resp.sendRedirect(req.getContextPath() + "/customer-info?ID="+c.getCustomerID());
        } else {
            resp.getWriter().write("Fail to update!");
        }
    }
}

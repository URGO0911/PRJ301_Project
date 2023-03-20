
package controllers;

import dal.Account;
import dal.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import models.AccountDAO;


public class AccountSignUp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("AccSession") == null) {
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("../index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CustomerID = RandomString(5);
        String CompanyName = req.getParameter("txtCopName");
        String ContactName = req.getParameter("txtName");
        String ContactTitle = req.getParameter("txtTitle");
        String Address = req.getParameter("txtAddress");
        String Email = req.getParameter("txtEmail");
        String Pass = req.getParameter("txtPass");
        String RePass = req.getParameter("txtRePass");

        //
        req.setAttribute("CompanyName", CompanyName);
        req.setAttribute("ContactName", ContactName);
        req.setAttribute("ContactTitle", ContactTitle);
        req.setAttribute("Address", Address);
        req.setAttribute("Email", Email);
        req.setAttribute("Password", Pass);
        req.setAttribute("Re-Password", RePass);

        //
        String msgCPN = "";
        String msgCN = "";
        String msgCT = "";
        String msgA = "";
        String msgE = "";
        String msgP = "";
        String msgRP = "";
        if (CompanyName.equals("")) {
            msgCPN = "Company name is required";
            req.setAttribute("msgCPN", msgCPN);
        }
        if (ContactName.equals("")) {
            msgCN = "Contact name is required";
            req.setAttribute("msgCN", msgCN);
        }
        if (ContactTitle.equals("")) {
            msgCT = "contact title is required";
            req.setAttribute("msgCT", msgCT);
        }
        if (Address.equals("")) {
            msgA = "Address is required";
            req.setAttribute("msgA", msgA);
        }
        if (Email.equals("")) {
            msgE = "Email is required";
            req.setAttribute("msgE", msgE);
        }
        if (checkEmail(Email) == false) {
            msgE = "Email is exist";
            req.setAttribute("msgE", msgE);
        }

        if (Pass.equals("")) {
            msgP = "Password is required";
            req.setAttribute("msgP", msgP);
        }
        if (RePass.equals("")) {
            msgRP = "Re-Password is required";
            req.setAttribute("msgRP", msgRP);
        }
        if (!RePass.equals(Pass)) {
            msgRP = "Re-Password is wrong";
            req.setAttribute("msgRP", msgRP);
        }
        if (!msgE.equals("") || !msgP.equals("") || !msgRP.equals("") || !msgA.equals("") || !msgCT.equals("") || !msgCN.equals("") || !msgCPN.equals("")) {
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        } else {
            Customer cust = new Customer(CustomerID, CompanyName, ContactName, ContactTitle, Address);
            Account acc = new Account(0, Email, Pass, CustomerID, 0, 2);
            int success = new AccountDAO().AddAccount(acc, cust);
            if (success != 0) {
                req.getSession().setAttribute("AccSession", acc);
                resp.sendRedirect("../index.jsp");
            } else {
                req.setAttribute("Err", "Error");
                req.getRequestDispatcher("../signup.jsp").forward(req, resp);
            }

        }
    }

    private static String RandomString(int length) {
        String reg = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String str = "";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int k;
            k = rand.nextInt(reg.length());
            str += reg.charAt(k);
        }
        return str;
    }

   
    private static Boolean checkEmail(String email) {
        ArrayList<String> list = new AccountDAO().getListEmail();
        boolean flag = true;
        for (String item : list) {
            if (email.equals(item)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}

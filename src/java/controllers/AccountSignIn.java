
package controllers;

import dal.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.AccountDAO;

public class AccountSignIn extends HttpServlet{

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("AccSession")!=null) {
            req.getSession().removeAttribute("AccSession");
            req.getSession().removeAttribute("CartSession");
             resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }else{
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Nhan du lieu tu login.jsp
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPass");
        String msgEmail = "", msgPass="";
        
        if (email.equals("")) {
            msgEmail = "Email is required";
            req.setAttribute("msgEmail", msgEmail);
        }
        if(pass.equals("")){
            msgPass = "Password is required";
            req.setAttribute("msgPass", msgPass);
        }
        
        if(!msgEmail.equals("") || !msgPass.equals("")){
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        }else{
            
            Account acc = new AccountDAO().getAccount(email, pass);
            
            if (acc!=null) {
                // Cap session cho account
                int role = acc.getRole();
                req.getSession().setAttribute("AccSession", acc);
                req.getSession().removeAttribute("CartSession");
                if (role==2) {
                    resp.sendRedirect(req.getContextPath()+"/");
                }
                else{
                    resp.sendRedirect(req.getContextPath()+"/manage-pro");
                }
                
            }else{
                req.setAttribute("msg", "This account does not exist");
                req.getRequestDispatcher("../signin.jsp").forward(req, resp);
            }
        }
    }
    
}

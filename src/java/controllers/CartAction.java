
package controllers;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CustomerDAO;
import models.OrderDAO;
import models.OrderDetailsDAO;

/**
 *
 * @author Admin
 */
public class CartAction extends HttpServlet {

    boolean add;
    boolean sub;
    boolean remove;
    boolean order;
    ArrayList<CartItem> cart = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("CartSession") != null) {
            cart = (ArrayList<CartItem>) req.getSession().getAttribute("CartSession");
            add = getBooleanValue(req.getParameter("add"));
            sub = getBooleanValue(req.getParameter("sub"));
            remove = getBooleanValue(req.getParameter("remove"));
            order = getBooleanValue(req.getParameter("order"));

            if (add || sub) {
                changeQuantity(req, resp);
            }

            if (remove) {
                removeItem(req, resp);
            }

            if (order) {
                try {
                    submitOrder(req, resp);
                } catch (Exception ex) {
                    Logger.getLogger(CartAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            resp.sendRedirect("/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("CartSession") != null) {
            submitOrder(req, resp);
        } else {
            resp.sendRedirect("/index.jsp");
        }
    }

    boolean getBooleanValue(String value) {
        boolean flag = false;
        try {
            flag = Boolean.parseBoolean(value);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private void changeQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pid = Integer.parseInt(req.getParameter("productID"));
        for (CartItem item : cart) {
            if (item.getP().getProductID() == pid) {
                if (add) {
                    item.setQuantity(item.getQuantity() + 1);
                    break;
                }

                if (sub) {
                    item.setQuantity(item.getQuantity() - 1);
                    if (item.getQuantity() == 0) {
                        removeItem(pid);
                        break;
                    }
                }
            }
        }

        req.getSession().setAttribute("CartSession", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private void removeItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pid = Integer.parseInt(req.getParameter("productID"));
        for (CartItem item : cart) {
            if (item.getP().getProductID() == pid) {
                cart.remove(cart.indexOf(item));
                break;
            }
        }
        req.getSession().setAttribute("CartSession", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private void removeItem(int pid) {
        for (CartItem item : cart) {
            if (item.getP().getProductID() == pid) {
                cart.remove(cart.indexOf(item));
                return;
            }
        }
    }

    private void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req = validateInputData(req, resp);
        cart = (ArrayList<CartItem>) req.getSession().getAttribute("CartSession");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String CompanyName = ((String) req.getAttribute("CompanyName")).trim();
        String ContactName = ((String) req.getAttribute("ContactName")).trim();
        String ContactTitle = ((String) req.getAttribute("ContactTitle")).trim();
        String Address = ((String) req.getAttribute("Address")).trim();
        String RequiredDate = ((String) req.getAttribute("RequiredDate")).trim();

        Account acc = (Account) req.getSession().getAttribute("AccSession");
        Order o = null;
        Customer c = null;

        if (acc != null) {
            if (!Address.isEmpty()) {
                o = new Order(0, acc.getCustomerID(), Date.valueOf(LocalDate.now()), Date.valueOf(RequiredDate), Address);
            }else{
                c = new CustomerDAO().getCustomer(acc.getCustomerID());
                o = new Order(0, acc.getCustomerID(), Date.valueOf(LocalDate.now()), Date.valueOf(RequiredDate), c.getAddress());
            }
      
        } else {

                do {
                    c = new Customer(getRandomString(0), CompanyName, ContactName, ContactTitle, Address);
                } while (new CustomerDAO().addCustomer(c) == 0);
                o = new Order(0, c.getCustomerID(), Date.valueOf(LocalDate.now()), Date.valueOf(RequiredDate), Address);
        }

        boolean success = false;
        o = new OrderDAO().addOrder(o);
        if (o != null) {

            ArrayList<OrderDetails> OrderDetailList = new ArrayList<OrderDetails>();
            for (CartItem item : cart) {
                OrderDetails od = new OrderDetails(o.getOrderID(), item.getP().getProductID(), item.getP().getUnitPrice(), item.getQuantity(), 0);
                item.setUnitPrice(od.getQuantity() * od.getUnitPrice() - od.getQuantity() * od.getUnitPrice() * od.getDiscount());
                OrderDetailList.add(od);
            }

            for (OrderDetails item : OrderDetailList) {
                success = addOrderDetail(item);
            }
        }

        if (success) {
            req.getSession().removeAttribute("CartSession");
            
            if (acc != null) {
                req.getRequestDispatcher("/cart.jsp").forward(req, resp);
            }else{
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }

    public String getRandomString(int length) {
        String text = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String output = "";
        for (int i = 1; i <= 5; i++) {
            int random = ThreadLocalRandom.current().nextInt(0, text.length() - 1);
            String n = String.valueOf(text.charAt(random));
            output += n;
        }

        return output;
    }

    private boolean addOrderDetail(OrderDetails od) {
        int i = 0;
        i = new OrderDetailsDAO().addOrderDetail(od);

        if (i != 0) {
            return true;
        } else {
            return false;
        }
    }

    private double getTotalAmountOrder(ArrayList<OrderDetails> odList) {
        double total = 0;
        for (OrderDetails item : odList) {
            total += (item.getQuantity() * item.getUnitPrice()) - (item.getQuantity() * item.getUnitPrice() * item.getDiscount());
        }
        return total;
    }

    String getString(String value) {
        String s = "";
        try {
            s = value;
        } catch (Exception e) {
            s = "";
        }
        return s;
    }

    private HttpServletRequest validateInputData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CompanyName;
        String ContactName;
        String ContactTitle;
        String Address;
        String RequiredDate;

        try {
            CompanyName = req.getParameter("txtCompanyName").trim();

        } catch (Exception e) {
            CompanyName = " ";

        }

        try {

            ContactName = req.getParameter("txtContactName").trim();

        } catch (Exception e) {

            ContactName = " ";

        }
        try {

            ContactTitle = req.getParameter("txtContactTitle").trim();

        } catch (Exception e) {

            ContactTitle = " ";

        }
        try {

            Address = req.getParameter("txtAddress").trim();
        } catch (Exception e) {

            Address = " ";
        }

        try {
            RequiredDate = req.getParameter("txtRequiredDate").trim();
        } catch (Exception e) {
            RequiredDate = "";
        }

        req.setAttribute("CompanyName", CompanyName);
        req.setAttribute("ContactName", ContactName);
        req.setAttribute("ContactTitle", ContactTitle);
        req.setAttribute("Address", Address);
        req.setAttribute("RequiredDate", RequiredDate);

        if (CompanyName.isEmpty() || ContactName.isEmpty() || ContactTitle.isEmpty() || Address.isEmpty()
                || RequiredDate.isEmpty() || !Date.valueOf(RequiredDate).after(Date.valueOf(LocalDate.now()))) {
            if (CompanyName.isEmpty()) {
                req.setAttribute("emptyCompanyName", "CompanyName required");
            }

            if (ContactName.isEmpty()) {
                req.setAttribute("emptyContactName", "ContactName required");
            }

            if (ContactTitle.isEmpty()) {
                req.setAttribute("emptyContactTitle", "ContactTitle required");
            }

            if (Address.isEmpty()) {
                req.setAttribute("emptyAddress", "Address required");
            }

            if (RequiredDate.isEmpty()) {
                req.setAttribute("emptyRequiredDate", "RequiredDate required");
            }

            if (!RequiredDate.isEmpty()) {
                if (!Date.valueOf(RequiredDate).after(Date.valueOf(LocalDate.now()))) {
                    req.setAttribute("invalidRequiredDate", "Invalid RequiredDate");
                }
            }

            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
        return req;
    }

}

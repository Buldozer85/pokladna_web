package Servlets;

import java.io.IOException;

import Model.Polozka;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name = "polozky", urlPatterns = {"/"})
public class PolozkaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("polozky", Polozka.getAll());
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        return;
        
    }
    
}

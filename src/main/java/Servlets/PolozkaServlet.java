package Servlets;

import java.io.IOException;

import Model.Objednavka;
import Model.Polozka;
import Model.Pridavek;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name = "index", urlPatterns = {"/objednavka"})
public class PolozkaServlet extends HttpServlet {
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("ID_polozka") == null) {
        req.setAttribute("polozkyList", Polozka.getAll());
        getServletContext().getRequestDispatcher("/objednavka.jsp").forward(req, resp);
        return;
        } else{
           
            int id = Integer.parseInt(req.getParameter("ID_polozka"));

            Polozka p = new Polozka(id);

            Objednavka.getObjednavka().add(p);
            req.setAttribute("pridavkyList", Pridavek.getAll());
            req.setAttribute("ID_polozka", req.getParameter("ID_polozka"));


            getServletContext().getRequestDispatcher("/objednavka.pridavek.jsp").forward(req, resp);
           

        }

        if(req.getParameter("ID_polozka") != null && req.getParameter("ID_pridavek") != null){
            
        }
    }
    
}

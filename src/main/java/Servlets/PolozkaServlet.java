package Servlets;

import java.io.IOException;

import Model.Objednavka;
import Model.Polozka;
import Model.Pridavek;
import Model.Tiskarna;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "index", urlPatterns = { "/objednavka" })
public class PolozkaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("ID_polozka") == null) {
            req.setAttribute("polozkyList", Polozka.getAll());
            if(Objednavka.getObjednavka().isEmpty()){
                req.setAttribute("prazdna", true);
            } else{
                  req.setAttribute("objednavka", Objednavka.getObjednavka());
                  req.setAttribute("prazdna", false);
            }
            getServletContext().getRequestDispatcher("/objednavka.jsp").forward(req, resp);
            return;
        }
        if (req.getParameter("ID_polozka") != null && req.getParameter("ID_pridavek") == null) {

            int id = Integer.parseInt(req.getParameter("ID_polozka"));
            System.out.println(id + "ID");
            Polozka p = new Polozka(id);

            Objednavka.getObjednavka().add(p);
            System.out.println(Objednavka.getObjednavka() + "objednavka");
            req.setAttribute("pridavkyList", Pridavek.getAll());
            req.setAttribute("ID_polozka", req.getParameter("ID_polozka"));

            getServletContext().getRequestDispatcher("/objednavka.pridavek.jsp").forward(req, resp);

            return;
        }

        if (req.getParameter("ID_polozka") != null && req.getParameter("ID_pridavek") != null) {
            req.setAttribute("pridavkyList", Pridavek.getAll());
            req.setAttribute("ID_polozka", req.getParameter("ID_polozka"));

            getServletContext().getRequestDispatcher("/objednavka.pridavek.jsp").forward(req, resp);
            int id = Integer.parseInt(req.getParameter("ID_pridavek"));
            Pridavek pridavek = new Pridavek(id);

            Objednavka.getObjednavka().getLast().getPridavky().add(pridavek);
            System.out.println(Objednavka.getObjednavka().getLast().getPridavky());

        }

        if(req.getParameter("dokoncit") != null){
            //TODO: odesílání na panel přípravy, zápis do databáze
            Tiskarna t = Tiskarna.getITiskarna();
            Objednavka o = new Objednavka();
            o.setPolozky(Objednavka.getObjednavka());
           //TODO:  o.setCasObjednavky()
           t.Tiskni(o);

        }
    }

}

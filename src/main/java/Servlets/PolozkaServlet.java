package Servlets;

import java.io.IOException;

import Model.Objednavka;
import Model.Polozka;
import Model.Pridavek;
import Model.Tiskarna;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "index", urlPatterns = { "/objednavka" })
public class PolozkaServlet extends HttpServlet {
    private Tiskarna t = Tiskarna.getITiskarna();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cena", Objednavka.sum());
        if (req.getParameter("dokoncit") != null) {
            Objednavka o = new Objednavka();
            o.setPolozky(Objednavka.getObjednavka());
            o.setCena(Objednavka.sum());

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            o.setCasObjednavky(currentTime);
            t.Tiskni(o);
            o.save();
            Objednavka.getObjednavka().clear();
            resp.sendRedirect("/dvorinald/index.jsp");
            return;

        }

        if (req.getParameter("odstran") != null) {
            Objednavka.getObjednavka().remove(Integer.parseInt(req.getParameter("odstran")));

            req.setAttribute("cena", Objednavka.sum());

        }

        if (req.getParameter("odPolozky") != null && req.getParameter("pridavek") != null) {
            Objednavka.getObjednavka().get(Integer.parseInt(req.getParameter("odPolozky"))).getPridavky()
                    .remove(Integer.parseInt(req.getParameter("pridavek")));
            req.setAttribute("cena", Objednavka.sum());
        }
        if (req.getParameter("kPolozce") != null) {
            req.setAttribute("pridavkyList", Pridavek.getAll());
            req.setAttribute("kPolozce", req.getParameter("kPolozce"));

            getServletContext().getRequestDispatcher("/objednavka.pridavek.pridat.jsp").forward(req, resp);

        }

        if (req.getParameter("kPolozce") != null && req.getParameter("ID_pridavek") != null) {
            Pridavek p = new Pridavek(Integer.parseInt(req.getParameter("ID_pridavek")));
            Objednavka.getObjednavka().get(Integer.parseInt(req.getParameter("kPolozce"))).getPridavky().add(p);
            req.setAttribute("cena", Objednavka.sum());
        }

        if (req.getParameter("ID_polozka") == null) {
            req.setAttribute("polozkyList", Polozka.getAll());
            if (Objednavka.getObjednavka().isEmpty()) {
                req.setAttribute("prazdna", true);
            } else {
                req.setAttribute("objednavka", Objednavka.getObjednavka());
                req.setAttribute("prazdna", false);
            }
            getServletContext().getRequestDispatcher("/objednavka.jsp").forward(req, resp);
            return;
        }
        if (req.getParameter("ID_polozka") != null && req.getParameter("ID_pridavek") == null) {

            int id = Integer.parseInt(req.getParameter("ID_polozka"));

            Polozka p = new Polozka(id);

            Objednavka.getObjednavka().add(p);

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
            return;

        }

    }

}

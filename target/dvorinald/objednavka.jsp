<%@ page contentType="text/html; charset=UTF-8" pageEncoding= "UTF-8"%>
    <%@ page import="Model.Polozka"%>
        <%@ page import="Model.Objednavka"%>
            <%@ page import="Model.Pridavek"%>
                <%@ page import = "java.util.List"%>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset='utf-8'>
                        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
                        <title>Objednávací panel</title>
                        <meta name='viewport' content='width=device-width, initial-scale=1'>
                        <link rel='stylesheet' type='text/css' media='screen' href='style/scss/main.css'>



                    </head>

                    <body>

                        <h1>Dvořinald</h1>
                        <div id="obal">
                            <div>
                                <% for(Polozka p : (List<Polozka>)request.getAttribute("polozkyList")){%>
                                    <article>
                                        <h2><b><%= p.getNazev()%></b></h2>
                                        <h2><b>Cena:</b>
                                            <%= p.getCena()%> Kč</h2>
                                        <h3>
                                            <%= p.getDruh()%>
                                        </h3>

                                        <a href="?ID_polozka=<%= p.getId()%>">Přidat</a>
                                    </article>
                                    <%}%>
                            </div>

                            <div id="objednavka">
                                <h1>Objednávka</h1>
                                <% if ((Boolean)request.getAttribute("prazdna") == true){%>

                                    <%} else{
                                        int index = 0;
                                            for (Polozka pol : (List<Polozka>)request.getAttribute("objednavka")){%>
                                        <h3>
                                            <%= pol.getNazev()%>
                                        </h3>
                                        <h3>
                                            <%= pol.getCena()%> Kč
                                        </h3>

                                        <% for (Pridavek prid : pol.getPridavky()){ 
                                            int indexPridavku = 0;
                                            %>
                                            <h4>
                                                +
                                                <%=prid.getNazev()%>
                                                    <%=prid.getCena()%> Kč

                                            </h4>
                                            <a href="?odPolozky=<%=index%>&pridavek=<%=indexPridavku%>">Odstranit přídavek</a>
                                            <% indexPridavku++;}%>
                                                <a href="?odstran=<%=index%>">Odstranit</a>
                                                <a href="?kPolozce=<%=index%>">Přidat přídavek</a>

                                                <% index++;
                                            }%>
                                                    <%}%>
                                                        <h4>Celková cena:
                                                            <%=request.getAttribute("cena")%>&nbsp;Kč
                                                        </h4>
                                                        <a id="a" href="?dokoncit=1">Dokončit</a>

                            </div>

                        </div>
                        <script type="text/javascript" src="js/websocket.js">
                        </script>
                    </body>

                    </html>
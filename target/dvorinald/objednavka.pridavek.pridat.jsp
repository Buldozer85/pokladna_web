<%@ page contentType="text/html; charset=UTF-8" pageEncoding= "UTF-8"%>
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
                <div>
                    <div>
                        <% for(Pridavek p : (List<Pridavek>)request.getAttribute("pridavkyList")){%>
                            <article>
                                <h2><b><%= p.getNazev()%></b></h2>
                                <h2><b>Cena:</b>
                                    <%= p.getCena()%> Kč</h2>


                                <a href='?kPolozce=<%=request.getAttribute("kPolozce")%>&ID_pridavek=<%= p.getId()%>' '>Přidat</a>
                            </article>
                            <%}%>
                    </div>

                    <div>
<a href="/dvorinald/objednavka">OK</a>
                    </div>

                </div>

            </body>

            </html>
var ws;

ws = new WebSocket("ws://localhost:8080/dvorinald/endpoint");


ws.onerror = (event) => {
    console.log(event.data);
}
ws.onmessage = onMessage;

function onMessage(event) {
    let objednavka = JSON.parse(event.data);

    if (objednavka.action == "add") {

        printObjednavkaPriprava(objednavka);
    }
    if (objednavka.action == "remove") {
        document.getElementById(objednavka.id).remove();

    }
    if (objednavka.action == "vydej") {

        printObjednavkaVydej(objednavka);
    }
    if (objednavka.action == "vyzvednuta") {

        document.getElementById(objednavka.id).remove();

    }


}


function addObjednavka() {
    let objednavkaAction = {
        action: "add"


    };
    ws.send(JSON.stringify(objednavkaAction));
}

function removeObjednavka(element) {
    let id = element;
    let objednavkaAction = {
        action: "remove",
        id: id

    }
    ws.send(JSON.stringify(objednavkaAction));
}

function VydejObjednavka(element) {
    let id = element;
    let objednavkaAction = {
        action: "vyzvednuta",
        id: id

    }

    ws.send(JSON.stringify(objednavkaAction));
}



function printObjednavkaPriprava(objednavka) {
    if (!window.location.pathname.includes("objednavka")) {
        console.log(objednavka + "obj");
        let content = document.querySelector("#content");

        let objednavkaDiv = document.createElement("div");

        objednavkaDiv.setAttribute("id", objednavka.id);

        content.appendChild(objednavkaDiv);

        let objednavkaID = document.createElement('span');
        objednavkaID.setAttribute("class", "objednavkaID");
        objednavkaID.innerHTML = "<b>Objednávka číslo:</b> " + objednavka.id;

        objednavkaDiv.appendChild(objednavkaID);

        let objednavkaCena = document.createElement("span");
        objednavkaCena.innerHTML = "<b>cena:</b>" + objednavka.cena + "&nbspKč";
        objednavkaDiv.appendChild(objednavkaCena);


        if (window.location.pathname.includes("priprava")) {
            let vydejObjednavku = document.createElement("span");
            vydejObjednavku.setAttribute("class", "vydejObjednavku");
            vydejObjednavku.innerHTML = "<a  OnClick=removeObjednavka(" + objednavka.id + ")>Vydej objednavku</a>";
            objednavkaDiv.appendChild(vydejObjednavku);
        }


    }


}

function printObjednavkaVydej(objednavka) {
    if (!window.location.pathname.includes("objednavka") && !window.location.pathname.includes("priprava")) {
        console.log(objednavka + "obj");
        let content = document.querySelector("#vydejContent");

        let objednavkaDiv = document.createElement("div");

        objednavkaDiv.setAttribute("id", objednavka.id);

        content.appendChild(objednavkaDiv);

        let objednavkaID = document.createElement('span');
        objednavkaID.setAttribute("class", "objednavkaID");
        objednavkaID.innerHTML = "<b>Objednávka číslo:&nbsp;</b>" + objednavka.id;

        objednavkaDiv.appendChild(objednavkaID);

        let objednavkaCena = document.createElement("span");
        objednavkaCena.innerHTML = "<b>cena:&nbsp;</b>" + objednavka.cena + "&nbspKč";
        objednavkaDiv.appendChild(objednavkaCena);

        let vydejObjednavku = document.createElement("span");
        vydejObjednavku.setAttribute("class", "vydejObjednavku");
        vydejObjednavku.innerHTML = "<a  OnClick=VydejObjednavka(" + objednavka.id + ")>Vyzvednuta</a>";
        objednavkaDiv.appendChild(vydejObjednavku);

    }


}
if (window.location.pathname.includes("objednavka")) {
    let a = document.querySelector("#a");
    a.onclick = () => {

        addObjednavka()
    }
}
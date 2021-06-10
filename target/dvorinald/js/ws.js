let ws;

window.onload = function connect() {


    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://localhost:8080/dvorinald/endpoint");

    ws.onmessage = function(event) {
        if (!window.location.pathname.includes("objednavka") && !window.location.pathname.includes("vydej")) {
            console.log(event.data);
            var message = event.data
            let div = document.createElement("div");
            document.body.appendChild(div);
            let h1 = document.createElement("h1");
            h1.innerHTML = "Objednávka č: " + message;
            div.appendChild(h1);
            let a = document.createElement("a");
            a.innerHTML = "výdej";
            div.appendChild(a);
            a.onclick = () => { send(h1.nodeValue) }


        }
    };
}

function send(message) {
    ws.send(message);

}
function connect() {
    var socket = new SockJS('/adminpanel');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/orderspage/orders', function (order) {
            console.log(JSON.stringify(order));
            var table = document.getElementById('orderstable');
            var tbody = document.createElement('tbody');
            var tr = document.createElement('tr');
            var th = document.createElement('th');
            th.setAttribute('scope', 'row');
            th.appendChild(document.createTextNode(JSON.parse(order.body).id));
            tr.appendChild(th);
            var td1 = document.createElement('td');
            td1.appendChild(document.createTextNode(JSON.parse(order.body).ordererName));
            tr.appendChild(td1);
            var td2 = document.createElement('td');
            td2.appendChild(document.createTextNode(JSON.parse(order.body).orderStatus));
            tr.appendChild(td2);
            var td3 = document.createElement('td');
            td3.appendChild(document.createTextNode(JSON.parse(order.body).paymentMethod));
            tr.appendChild(td3);
            var td4 = document.createElement('td');
            td4.appendChild(document.createTextNode(JSON.parse(order.body).dateTime));
            tr.appendChild(td4);
            tr.setAttribute('data-href', '/o/' + JSON.parse(order.body).id);
            tbody.appendChild(tr);
            table.insertBefore(tbody, table.firstChild);
            console.log(tbody);
            table.removeChild(table.lastElementChild);
            var audio = new Audio('../sounds/newOrder.ogg');
            audio.play();
            rowlink();
        });
    });
}
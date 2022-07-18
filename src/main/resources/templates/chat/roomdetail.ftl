<!doctype html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div>
        <h2>{{room.name}}</h2>
    </div>

    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage">준비</button>
            <button class="btn btn-primary" type="button" @click="sendReady">준비</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
            <A> {{message.message}}</a>
        </li>
    </ul>
    <div></div>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    //alert(document.title);
    // websocket & stomp initialize
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            sender: '',
            message: '',
            messages: []
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.sender = localStorage.getItem('wschat.sender');
            //this.findRoom();
        },
        methods: {
            findRoom: function () {
                axios.get('/game/room/' + this.roomId + "?langIdx=0&levelIdx=0").then(response => {
                    this.room = response.data;
                    if (!localStorage.hasOwnProperty("wschat.roomId") && !localStorage.hasOwnProperty("wschat.server")) {
                        let data = response.data;
                        localStorage.setItem("wschat.roomId", data.roomId);
                        localStorage.setItem("wschat.server", data.server);
                    }
                });
            },
            sendMessage: function () {

                ws.send("/app/game/codeMessage", {}, JSON.stringify({
                    roomId: localStorage.getItem("wschat.roomId"),
                    sender: "정찬",
                    message: this.message
                }));
                this.message = '';
            },
            sendReady: function () {
                ws.send("/app/game/ready", {}, JSON.stringify({
                    server: localStorage.getItem("wschat.server"),
                    roomId: localStorage.getItem("wschat.roomId"),
                    sender: "정찬"
                }));
                this.message = '';
            },
            recvMessage: function (recv) {
                console.log(recv)
                this.messages.unshift({
                    "type": recv.type,
                    "sender": recv.type == 'ENTER' ? '[알림]' : recv.sender,
                    "message": recv.message
                })
            }
        }
    });

    function findRoom() {
        axios.get('/game/room/' + this.roomId + "?langIdx=0&levelIdx=0").then(response => {
            this.room = response.data;
            if (!localStorage.hasOwnProperty("wschat.roomId") && !localStorage.hasOwnProperty("wschat.server")) {
                let data = response.data;
                localStorage.setItem("wschat.roomId", data.roomId);
                localStorage.setItem("wschat.server", data.server);
            }
        });

    }

    function connect() {
        // pub/sub event
        ws.connect({}, function (frame) {
            ws.subscribe("/topic/game/room/" + vm.$data.roomId, function (message) {
                var recv = JSON.parse(message.body);
                console.log(recv)
                vm.recvMessage(recv);
            });

            ws.subscribe("/user/queue/game/codeMessage", function (message) {
                var recv = JSON.parse(message.body);
                console.log(recv)
                vm.recvMessage(recv);
            });
        }, function (error) {
            if (reconnect++ < 5) {
                setTimeout(function () {
                    // console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                }, 10 * 1000);
            }
        });
    }

    connect();


</script>
</body>
</html>
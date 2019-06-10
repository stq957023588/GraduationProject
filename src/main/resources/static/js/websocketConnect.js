

//websocket 连接
function websocketConnect(account) {
    var websocket=null;
    if('WebSocket' in window){
        websocket=new WebSocket("ws://127.0.0.1:8888/websocket/"+account);
    }else {
        return "Error";
    }
    window.onbeforeunload=function () {
        closeWebSocket();
    };
    websocket.onerror=function (ev) {
        console.log(ev);
    };
    websocket.onopen=function () {
        console.log('Connection Success');
    };
    return websocket;
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload=function () {
    closeWebSocket();
};
//关闭wesocket连接
function closeWebSocket () {
    websocket.close();
}

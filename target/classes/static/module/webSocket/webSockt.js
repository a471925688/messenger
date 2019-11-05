$(document).ready(function(){

})
var WebSocketUtil;
var websockt=function () {

}
websockt.prototype = {
    socket:null,
    config:{
        openUrl:"",
        cid:'',
        eroorMsg:'webScoket鏈接錯誤!!'
    },
    init:function (config) {
        this.copyConfig(config);
        // this.config = config?config:this.config;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            this.socket = new WebSocket(this.config.openUrl+this.config.cid);
            //打开事件
            this.socket.onopen = this.onopen;
            // //获得消息事件
            // this.socket.onmessage = this.onmessage;
            //关闭事件
            this.socket.onclose = this.onclose;
            //发生了错误事件
            this.socket.onerror = this.onerror;
        }
    },
    onopen:function(){
        console.log("Socket 已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    },
    onclose:function(){
        console.log("Socket已关闭");
        WebSocketUtil.socket = new WebSocket(WebSocketUtil.config.openUrl+WebSocketUtil.config.cid);
        // layer.alert('鏈接超時!', {
        //     skin: 'layui-layer-molv' //样式类名
        //     ,closeBtn: 0
        //     ,anim: 6 //动画类型
        // }, function(){
        //     location.reload()
        // });
    },
    onerror:function () {
        var index= layer.alert(WebSocketUtil.config.eroorMsg, {
            skin: 'layui-layer-molv' //样式类名
            ,closeBtn: 0
            ,anim: 6 //动画类型
        }, function(){
            layer.close(index)
        });
    },
    send:function(data){
        console.log("发送消息");
        //發送消息
        this.socket.send(data);
    },
    onmessage:function (fun) {
        this.socket.onmessage = fun;
    },
    copyConfig:function (obj) {
        if (obj === null) return null
        if (typeof obj !== 'object') return obj;
        if (obj.constructor === Date) return new Date(obj);
        if (obj.constructor === RegExp) return new RegExp(obj);
        for (var key in obj) {
            if (obj.hasOwnProperty(key)) {   //不遍历其原型链上的属性
                var val = obj[key];
                this.config[key] = typeof val === 'object' ? arguments.callee(val) : val; // 使用arguments.callee解除与函数名的耦合
            }
        }
    },
}
WebSocketUtil = new websockt();

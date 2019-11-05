var card = {
    downX:'',
    downY:'',
    cardTop:'',
    cardLeft:'',
    t:{},
    config:{
        id:'id',
        nickName:'user.nickName',
        headerImg:'user.avatar',
        info:'info',
        createTime:'createTime',
        isOther:'isMember',
        avatar:'user.avatar',
        sendBack:{
            url:"order/orderReply/add",
            callBack:function (data) {
                $.post(card.config.sendBack.url,data,function (obj) {
                    if(obj.code)
                        window.layer.msg(obj.msg);
                })
            }
        }
    },
    datas:{},
    user:{
        nickName:"管理員",
        avatar:"",
    },
    param:{
        def_avatar:'assets/images/head.png',
        orderId:'',
    },
    temHtml:{
        title:"<div class=\"card-title\" style=\"cursor: move;\">​</div>"
        ,toolbar:"<span class=\"layui-layer-setwin\"><a class=\"layui-layer-ico layui-layer-max\" href=\"javascript:;\"></a><a  onclick='card.close()' class=\"layui-layer-ico layui-layer-close layui-layer-close1\" href=\"javascript:;\"></a></span>"
        ,main:"<div class=\"card-body\">\n" +
            "        <div class=\"card-body-main\">\n" +
            "            <ul></ul>" +
            "       </div>" +
            "        <div class=\"card-body_send_div_info\">\n" +
            "            <textarea class='card_textarea' ></textarea>\n" +
            "        </div>"+
            "        <div class=\"card-body_send_div\">\n" +
            "               <button class=\"layui-btn layui-btn-lg card-body_send_div_btn\">發送</button>\n" +
            "               <button class=\"layui-btn layui-btn-lg card-body_send_div_btn\">關閉</button>\n" +
            "        </div>"+
            "</div>"
        ,info_other:'<li>\n' +
            '                    <div >\n' +
            '                        <img class="card-body-main-header-img float-right" src="{{avatar}}">\n' +
            '                        <cite class="right"><i>{{time}}</i>{{nickName}}</cite>\n' +
            '                    </div>\n' +
            '                    <div class="card-body-main-text">\n' +
            '                        <div class="card-body-main-text-content float-right card-body-main-text-background-color">{{contentInfo}}'+
            '                            <div class="card-body-main-text-triangle"></div>\n' +
            '                        </div>\n' +
            '\n' +
            '                    </div>\n' +
            '\n' +
            '                </li>'
        ,info_our:'  <li>\n' +
            '                    <div >\n' +
            '                        <img class="card-body-main-header-img" src="{{avatar}}">\n' +
            '                        <cite>{{nickName}}<i>{{time}}</i></cite>\n' +
            '                    </div>\n' +
            '                    <div class="card-body-main-text">\n' +
            '                        <div class="card-body-main-text-content">{{contentInfo}}' +
            '                            <div class="card-body-main-text-triangle card-body-main-text-triangle-left"></div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '\n' +
            '                </li>'
    },
    open:function(param,user){
        // var doc = $(param[id]);
        // if(!doc.length){
        //
        // }
        // console.log($('.layui-body'))
        // $('.layui-body').append("<div class='.order_card'></div>");


         var datas = param.orderReplys?param.orderReplys:[]
        card.user = user;

        $('.order_card').html("")
        card.param.orderId = param.orderId;
        var doc = $('.order_card').show()
        doc.append(this.temHtml.title)
            .append(this.temHtml.toolbar)
            .append(this.temHtml.main)
        for (var i=0;i<datas.length;i++){
            this.addInfo(datas[i],doc)
        }

        $(".order_card .card-title").mousedown(card.titleMouseDown)
        $(".order_card .card-title").mouseup(card.titleMouseup)
        // $(".order_card .card-title").mousemove(card.cardMoven);
        $(document).mousemove(card.cardMoven);
        $(".order_card .card-body_send_div button").eq(1).click(this.close)
        $(".order_card .card-body_send_div button").eq(0).click(function () {
            var info = doc.find("textarea").val();
            if(!info)
                return ;
            var data = {
                id:'1',
                orderId:card.param.orderId,
                info:info,
                createTime:'2018/12/1 12:29:12',
                isMember:false,
            }
            card.config.sendBack.callBack(data)
            data.user =  card.user;
            card.addInfo(data);
            card.datas.push(data);
            $(".card-body-main").scrollTop($(".card-body-main")[0].scrollHeight)
        });
        $(".card-body-main").scrollTop($(".card-body-main")[0].scrollHeight)

        card.datas = datas;
    },
    cardMoven:function (x) {
        if(card.downX) {
            console.log('移动着')
            var left = card.cardLeft - (card.downX - x.pageX );
            var top = card.cardTop - (card.downY - x.pageY);
            $(".order_card").css("left",left>0?left:0 + "px");
            $(".order_card").css("top", top>0?top:0 + "px");
        }

    },
    titleMouseDown:function (x) {
        card.downX = x.pageX;
        card.downY = x.pageY;
        card.cardTop = parseInt($(".order_card").css("top"));
        card.cardLeft = parseInt($(".order_card").css("left"));

    },
    titleMouseup:function () {
        card.downX = '';
        card.downY = '';
        // clearInterval(this.t)
        console.log("標題彈起")
    },
    addInfo:function (d,doc) {
        doc = doc?doc:$('.order_card')
        var htmlinfo='';
        if(d[this.config.isOther]){
            htmlinfo = this.temHtml.info_other;
        }else {
            htmlinfo = this.temHtml.info_our;
        }
        var time = analysisParam(d,this.config.createTime);
        var nickName = analysisParam(d,this.config.nickName);
        var contentInfo = analysisParam(d,this.config.info);
        var avatar = analysisParam(d,this.config.avatar);
        htmlinfo = htmlinfo.replace(/{{time}}/g,time)
            .replace(/{{nickName}}/g,nickName)
            .replace(/{{contentInfo}}/g,contentInfo)
            .replace(/{{avatar}}/g,avatar?avatar.indexOf("/")==-1?"image/"+avatar:avatar:this.param.def_avatar)
        doc.find("ul").append(htmlinfo);
        function analysisParam(data,params) {
            if(!data)
                return ;
            var resData = data;
            if(params.indexOf(".")!=-1){
                params = params.split('.');
                for (var i=0;i<params.length;i++){
                    resData = resData[params[i]];
                }
            }else {
                resData = data[params];
            }
            return  resData;
        }
    },
    close:function () {
        $(".order_card").hide();
    }
}
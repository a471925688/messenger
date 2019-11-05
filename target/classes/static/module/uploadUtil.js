var Upload;
var upload = function () {

};
upload.prototype = {
    /**
     * 单图片上传
     * @param upload
     * @param id
     * @param url
     * @param data
     * @param ext
     */
    uploadImg:function (upload,id,url,data,ext) {
        upload.render({
            elem: '#'+id
            ,url: url //上传接口
            ,exts:ext
            ,data:data
            ,size:700
            ,choose: function (obj) {
                //将每次选择的文件追加到文件队列
                fileCopy = obj.pushFile();
                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function (index, file, result) {
                    $("#container").remove();
                    $("#imgPath").val("");
                    if (file.size > 0 && $('#ImgPreview').find('img').length === 0) {
                        $('#ImgPreview').empty();
                    }
                    // 添加图片 ImgPreview-预览的dom元素的id
                    $("#"+id).append('<div class="image-container" id="container"><div class="delete-css"><button id="upload_img_"  class="layui-btn layui-btn-danger layui-btn-xs">重新上传</button></div>' +
                        '<img id="showImg" style="width: 180px;height: 160px; margin:10px;cursor:pointer;"src="' + result + '" alt="' + file.name + '"></div>');
                    //删除某图片
                    $("#upload_img_").bind('click', function () {
                        delete fileCopy[index];
                        $('#'+id).click();
                        $("#container").remove();
                        return false;
                    });
                    //某图片放大预览
                    $("#showImg").bind('click', function () {
                        var width = $("#showImg").width();
                        var height = $("#showImg").height();
                        var scaleWH = width / height;
                        var bigH = 600;
                        var bigW = scaleWH * bigH;
                        if (bigW > 900) {
                            bigW = 900;
                            bigH = bigW / scaleWH;
                        }
                        // 放大预览图片
                        layer.open({
                            type: 1,
                            title: false,
                            closeBtn: 1,
                            shadeClose: true,
                            area: [bigW + 'px', bigH + 'px'], //宽高
                            content: "<img width='" + bigW + "' height='" + bigH + "' src='" + result + "' />"
                        });
                        return false;
                    });

                })
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    delete fileCopy[index];
                    $("#imagePath").val(res.fileName);
                }
                layer.msg(res.msg, {
                    icon: res.code,
                    time: 1500 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        });
    },

    /**
     * 单图片上传(附帶剪切功能)
     * @param upload
     * @param id
     * @param url
     * @param data
     * @param ext
     * @param mark 剪切板的尺寸保存處
     */
    uploadImgCropper:function (upload,id,url,data,ext,mark) {
        upload.render({
            elem: '#'+id
            ,url: url //上传接口
            ,exts:ext
            ,data:data
            ,size:1024*10*2
            , before: function (obj) {
                //将每次选择的文件追加到文件队列
                fileCopy = obj.pushFile();
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    console.log(mark.attr("imgratio"))
                    $("#container").remove();
                    var option = {
                        imgUrl:result,
                        mark:mark.find("option:selected").attr("imgratio"),
                        done:function (obj) {
                            if(obj.code == 0){ //剪切完成
                                $("#imagePath").val(obj.fileName);
                                $("#showImg").attr("src",$("#ctxPath").val()+"/temp/"+obj.fileName);
                            }
                        },
                        croppersPath:$("#ctxPath").val()+"/module/cropper/"
                    }
                    croppers_pic(option);

                    // 添加图片 ImgPreview-预览的dom元素的id
                    $("#"+id).append('<div class="image-container" id="container"><div class="delete-css"><button id="upload_img_"  class="layui-btn layui-btn-danger layui-btn-xs">重新上传</button></div>' +
                        '<li style="position:relative"><img id="showImg" style="max-width: 180px; max-height: 160px;margin:10px;cursor:pointer;"src="' + result + '" alt="' + file.name + '"></li></div>');
                    //删除某图片
                    $("#upload_img_").bind('click', function () {
                        delete fileCopy[index];
                        $('#'+id).click();
                        $("#container").remove();
                        return false;
                    });
                    //某图片放大预览
                    $("#showImg").bind('click', function () {
                        var width = $("#showImg").width();
                        var height = $("#showImg").height();
                        var scaleWH = width / height;
                        var bigH = 600;
                        var bigW = scaleWH * bigH;
                        if (bigW > 900) {
                            bigW = 900;
                            bigH = bigW / scaleWH;
                        }
                        // 放大预览图片
                        layer.open({
                            type: 1,
                            title: false,
                            closeBtn: 1,
                            shadeClose: true,
                            area: [bigW + 'px', bigH + 'px'], //宽高
                            content: "<img width='" + bigW + "' height='" + bigH + "' src='" + result + "' />"
                        });
                        return false;
                    });
                });
            }
            , done: function (res, index, upload) {
                if(res.code == 0){ //上传成功
                    delete fileCopy[index];
                    $("#imagePath").val(res.fileName);
                }
            }
        });
    },
    /**
     * 多图片上传
     * @param upload
     * @param id
     * @param url
     * @param ext
     */
    uploadImgs:function (upload,id,url,data,ext) {
        upload.render({
            elem: '#'+id
            ,url: url //上传接口
            ,exts:ext
            ,size:700
            ,multiple: true
            // ,before: function(obj) {
            //     //预读本地文件示例，不支持ie8
            //     obj.preview(function (index, file, result) {
            //         $("#detailsimgdescribe").hide();
            //         $("#"+id).append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img" width="180px" height="180px" style="margin-left: 10px">')
            //     })
            // }
            ,data:data
            ,choose: function (obj) {
                //将每次选择的文件追加到文件队列
                FilesCopy = obj.pushFile();
                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function (index, file, result) {
                    if (file.size > 0 && $('#ImgPreview').find('img').length === 0) {
                        $('#ImgPreview').empty();
                    }
                    $("#detailsimgdescribe").hide();
                    // 添加图片 ImgPreview-预览的dom元素的id
                    $("#"+id).append('<div class="image-container" style="width:200px; height:200px;float: left" id="container' + index + '" name="'+file.name+'"><div class="delete-css"><button id="upload_img_' + index + '"  name="'+file.name+'" class="layui-btn layui-btn-danger layui-btn-xs">删除</button></div>' +
                        '<img id="showImg' + index + '" style="width: 180px;height: 160px; margin:10px;cursor:pointer;"src="' + result + '" alt="' + file.name + '"></div>');
                    //删除某图片
                    $("#upload_img_" + index).bind('click', function () {
                        delete FilesCopy[index];
                        var reg1 = new RegExp(';[^;]*'+$(this).attr('name')+';');
                        console.info(reg1);
                        var reg2 = new RegExp('[^;]*'+$(this).attr('name')+';');
                        var reg3 = new RegExp(';[^;]*'+$(this).attr('name'));
                        console.info(reg2);
                        var paths = $("#imagesPath").val();
                        console.info(paths.indexOf(paths));
                        paths=paths.replace(reg1,";");
                        paths=paths.replace(reg2,"");
                        paths=paths.replace(reg3,"");
                        $("#imagesPath").val(paths);
                        $("#container" + index).remove();
                        return false;
                    });
                    //某图片放大预览
                    $("#showImg" + index).bind('click', function () {
                        var width = $("#showImg" + index).width();
                        var height = $("#showImg" + index).height();
                        var scaleWH = width / height;
                        var bigH = 600;
                        var bigW = scaleWH * bigH;
                        if (bigW > 900) {
                            bigW = 900;
                            bigH = bigW / scaleWH;
                        }
                        // 放大预览图片
                        layer.open({
                            type: 1,
                            title: false,
                            closeBtn: 1,
                            shadeClose: true,
                            area: [bigW + 'px', bigH + 'px'], //宽高
                            content: "<img width='" + bigW + "' height='" + bigH + "' src='" + result + "' />"
                        });
                        return false;
                    });

                })
            }
            ,done: function(res, index, files){
                if(res.code == 0){ //上传成功
                    if( $("#imagesPath").val()){
                        $("#imagesPath").val($("#imagesPath").val()+";"+res.path);
                    }else {
                        $("#imagesPath").val(res.path);
                    }
                    delete FilesCopy[index];
                }
                layer.msg(res.msg, {
                    icon: res.code,
                    time: 1500 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        });
    },
    /**
     * 单文件上传
     */
    uploadFile:function (upload,id,url,data,saveid) {
        var demoListView = $('#demoList')
            ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: url
            ,accept: 'file'
            ,multiple: true
            ,auto: false
            ,bindAction: '#testListAction'
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td></td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete" >删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                        var  path =$(this).attr('url')
                        var paths=$("#"+saveid).val();
                        paths=paths.replace(path+";","");
                        paths=paths.replace(path,"");
                        $("#"+saveid).val(paths);
                        // var reg1 = new RegExp(';[^;]*'+$(this).attr('name')+';');
                        // console.info(reg1);
                        // var reg2 = new RegExp('[^;]*'+$(this).attr('name')+';');
                        // var reg3 = new RegExp(';[^;]*'+$(this).attr('name'));
                        // console.info(reg2);
                        // var paths = $("#"+saveid).val();
                        // console.info(paths);
                        // console.info(paths.indexOf(paths));
                        // paths=paths.replace(reg1,";");
                        // paths=paths.replace(reg2,"");
                        // paths=paths.replace(reg3,"");
                        // $("#"+saveid).val(paths);
                    });

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    // window.open("https://codeload.github.com/douban/douban-client/legacy.zip/master");
                    tds.eq(1).html("<a href='"+res.url+"' target=\"_Blank\">"+res.url+"</a>")
                    // tds.eq(1).html("<a href='#'onclick='window.open(\""+res.url+"\")'>"+res.url+"</a>")
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).find('.demo-delete').attr("url",res.url);

                    // tds.eq(3).html(''); //清空操作
                    if( $("#"+saveid).val()){
                        $("#"+saveid).val($("#"+saveid).val()+";"+res.url);
                    }else {
                        $("#"+saveid).val(res.url);
                    }
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

    },
    showImgHandle:function (id,url) {
        // 添加图片 ImgPreview-预览的dom元素的id
        $("#"+id).append('<div class="image-container" style="width:200px" id="container"><div class="delete-css"><button id="upload_img_" class="layui-btn layui-btn-danger layui-btn-xs lang" key="re_upload">重新上传</button></div>' +
            '<img id="showImg" style="max-width: 180px;max-height: 160px; margin:10px;cursor:pointer;"src="' + url + '"></div>');
        //删除某图片
        $("#upload_img_").bind('click', function () {
            $('#'+id).click();
            $("#container").remove();
            return false;
        });
        //某图片放大预览
        $("#showImg").click(function () {
            Upload.previewImg("#showImg");
            return false;
        })
    },
    previewImg:function (id) {
        var width = $(id).width();
        var height = $(id).height();
        var scaleWH = width / height;
        var bigH = 600;
        var bigW = scaleWH * bigH;
        if (bigW > 900) {
            bigW = 900;
            bigH = bigW / scaleWH;
        }
        // 放大预览图片
        layer.open({
            type: 1,
            title: false,
            closeBtn: 1,
            shadeClose: true,
            area: [bigW + 'px', bigH + 'px'], //宽高
            content: "<img width='" + bigW + "' height='" + bigH + "' src='" + $(id).attr("src") + "' />"
        });
        return false;
    }
}
var fileCopy=null;
var FilesCopy=null;
Upload = new upload();
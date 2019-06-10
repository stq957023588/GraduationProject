
//加载未读信息数量
function loadUnreadMsgNum(account){
    $.ajax({
        type:'post',
        url:'getAllUnreadNum',
        data:{
            account:account
        },
        dataType:'json',
        success:function (data) {
            if(data.data>0){
                $("#msgNum").html(data.data);
            }
        },
        error:function (e) {
            console.log(e);
        }
    })
}



Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//获取url参数
function getUrlParam(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}


//登出
$("#loginOut").click(function(){
    $.ajax({
        type:'post',
        url:'loginOut',
        dataType:'json',
        success:function (data) {
            window.location.reload();
        },
        error:function (e) {
            console.log(e);
        }
    })
})


//加载头像
function loadHeadPicAjax(account,imgId){
    $.ajax({
        type:'post',
        url:'showHeadPicStrByAccount',
        data:{
            account:account
        },
        success:function (data) {
            $(imgId).attr("src",data);
        },
        error:function (e) {
            console.log(e);
        }
    })
}

function addUserElements(data){
    $.each(data,function (i,n){
        addOneUser(i,n);
    })
}

function addOneUser(i,n){
    var users=$("#users");
    var userInfo=$("<div class=\"userInfo mtop10 row\"></div>");
    var headPicDiv=$("<div class=\"headPicDiv\" style=\"float: left\"></div>")
    var headPic;
    console.log(n);
    $.ajax({
        type:'GET',
        url:'headPicCheck',
        data:{
            account:n.account
        },
        dataType:'json',
        success:function (data) {
            // console.log(data);
            if(data){
                headPic=$("<img src=\""+n.headPic+"\" class=\"headPicCss-b\">");
            }else{
                headPic=$("<img src=\"/img/ymc_.jpg\" class=\"headPicCss-b\">");
            }
            headPic.appendTo(headPicDiv);
        },
        error:function (e) {
            console.log(e);
        }
    })
    headPicDiv.appendTo(userInfo);
    var userProperty=$("<div class=\"userProperty\" style=\"display: inline-block;padding-left: 50px\"></div>")
    var userName=$("<div style=\"height: 64px;font-size: 19px;line-height: 49px;padding-top: 15px\"><span>"+n.name+"</span></div>")
    userName.appendTo(userProperty);
    var f_t=$("<div style=\"height:64px;line-height: 49px;padding-bottom: 15px\"></div>")
    var funNum;
    $.ajax({
        type:'post',
        url:'getUserInfoByAccount',
        data:{
            account:n.account
        },
        dataType:'json',
        success:function (data) {
            // console.log(data);
            $("<span class='w-grey'>粉丝数:</span>").appendTo(f_t);
            funNum=$("<span class='w-grey'>"+data.data.fansNum+"</span>");
            funNum.appendTo(f_t);

            $("<span class='w-grey' style='padding-left: 10px'>投稿数:</span>").appendTo(f_t);
            var tNum=$("<span class='w-grey'>"+data.data.strategiesNum+"</span>");
            tNum.appendTo(f_t);
        },
        error:function (e) {
            console.log(e);
        }
    })
    f_t.appendTo(userProperty);
    userProperty.appendTo(userInfo);
    var btnBox=$("<div class=\"all-center\" style=\"float: right;height: 128px;width: 200px;\"></div>");
    var btn;
    $.ajax({
        type:'post',
        url:'checkConcernOrNot',
        data:{
            account:n.account
        },
        dataType:'json',
        success:function (data) {
            // console.log(data);
            if(data.data){
                btn=$("<button type=\"button\" class=\"col-md-4 col-md-offset-4 btn btn-default\">已关注</button>")
            }else{
                btn=$("<button type=\"button\" class=\"col-md-4 col-md-offset-4 btn btn-info\">关注</button>")
            }
            btn.click(function (){
                var url=this.innerHTML=="关注" ? "concernUser" : "cancelConcern";
                $.ajax({
                    type:"POST",
                    url:url,
                    data:{
                        account:n.account
                    },
                    dataType:"json",
                    success:function (data) {
                        // console.log(data);
                        var msg=data.tip
                        layer.msg(msg,{time:2*1000});
                        if(msg=="已关注"){
                            btn.html("已关注");
                            btn.removeClass("btn-info").addClass("btn-default");
                            funNum.html(parseInt(funNum.html())+1);
                        }else if(msg=="取消关注"){
                            btn.html("关注");
                            btn.removeClass("btn-default").addClass("btn-info");
                            funNum.html(parseInt(funNum.html())-1);
                        }
                    },
                    error:function (e) {
                        console.log(e);
                    }
                })
            })
            btn.appendTo(btnBox);
        },
        error:function (e) {
            console.log(e);
        }
    });
    btnBox.appendTo(userInfo);
    userInfo.appendTo(users);
}

function addStrategyElements(data){
    // console.log(data);
    $.each(data,function (i,n) {
        addOneElement(i,n);
    })
}


function addOneElement(i,n,options){
    var defaults={
        contianerId:"#artilces",
    };
    var config = $.extend(defaults, options);


    var a_strategy=$("<div class=\"row a-strategy\"></div>");

    var headPicDiv=$("<div class=\"col-md-2 on-put\" ></div>");
    var headPicImg;
    $.ajax({
        type:"GET",
        url:"headPicCheck",
        data:{
            account:n.strategy.account
        },
        success:function (data) {
            if(data){
                $.ajax({
                    type:'post',
                    url:'showHeadPicStrByAccount',
                    data:{
                        account:n.strategy.account
                    },
                    success:function (data) {
                        headPicImg=$("<img src=\""+data+"\" class=\"img-circle img-responsive\" style='height: 96px;width: 96px'>");
                        headPicImg.appendTo(headPicDiv);
                        $.ajax({
                            type:'get',
                            url:'getUserNameByAccount',
                            data:{
                                account:n.strategy.account
                            },
                            dataType:'json',
                            success:function (data) {
                                var nameDiv=$("<div class=\"w-grey all-center mtop10\" style=\"font-size: 14px;\">" + data.data + "</div>");
                                nameDiv.appendTo(headPicDiv);
                            },
                            error:function (e) {
                                console.log(e);
                            }
                        });
                    },
                    error:function (e) {
                        console.log(e);
                    }
                })
            }else{
                headPicImg=$("<img src=\"/img/ymc_.jpg\" class=\"img-circle img-responsive\" style='height: 96px;width: 96px'>");
                headPicImg.appendTo(headPicDiv);
                $.ajax({
                    type:'get',
                    url:'getUserNameByAccount',
                    data:{
                        account:n.strategy.account
                    },
                    dataType:'json',
                    success:function (data) {
                        var nameDiv=$("<div class=\"w-grey all-center mtop10\" style=\"font-size: 14px;\">" + data.data + "</div>");
                        nameDiv.appendTo(headPicDiv);
                    },
                    error:function (e) {
                        console.log(e);
                    }
                });
            }



        },
        error:function (e) {
            console.log(e);
        }
    })
    headPicDiv.appendTo(a_strategy);

    var stra_infoDiv=$("<div class=\"col-md-10\" ></div>");

    var titleDiv=$("<div class=\"on-put\" style=\"font-size: 20px\">"+n.strategy.title+"</div>");
    titleDiv.click(function (){
        window.location.href="/showStrategyInfo?strategyId="+n.strategy.id+"&browser="+getBrowserInfo();
    });
    titleDiv.hover(function (){
        this.style.color="skyblue";
    },function (){
        this.style.color="black";
    })
    titleDiv.appendTo(stra_infoDiv);

    var timeDiv=$("<div class=\"w-grey\" style=\"font-size: 14px;\">" + n.publishTimeStr + "</div>");
    timeDiv.appendTo(stra_infoDiv);

    var placeDic=$("<div class=\"mtop10\"></div>");
    var citySpan=$("<span style=\"padding-right: 20px\">"+n.cityName+"</span>");
    citySpan.appendTo(placeDic);
    if(n.strategy.attractionsName!=null){
        var attractionSpan=$("<span style=\"padding-right: 20px\">"+n.strategy.attractionsName+"</span>");
        attractionSpan.appendTo(placeDic);
    }
    placeDic.appendTo(stra_infoDiv);

    var contentTextDiv=$("<div class=\"mtop10 on-put\" style=\"font-size: 17px\">"+n.strategy.contentText+"</div>");
    contentTextDiv.click(function (){
        window.location.href="/showStrategyInfo?strategyId="+n.strategy.id+"&browser="+getBrowserInfo();
    })
    contentTextDiv.appendTo(stra_infoDiv);

    var popularNum=$("<div class=\"mtop10\" style=\"height: 16px\"></div>");

    var watchImg=$("<img src=\"/img/眼睛.png\" width=\"22px\">");
    var watchNum=$("<span class=\"wordAfter\">"+n.watchNum+"</span>");
    var commentImg=$("<img src=\"/img/讨论.png\" height=\"18px\">");
    var commentNum=$("<span class=\"wordAfter\"></span>");
    $.ajax({
        type:'post',
        url:'getStrategyCommentCount',
        data:{
            strategyId:n.strategy.id
        },
        dataType:'json',
        success:function (data) {
            // console.log(data);
            commentNum.html(data.data);
        },
        error:function (e) {
            console.log(e);
        }
    })
    var upgoodImg=$("<img src=\"/img/点赞.png\" height=\"18px\">");
    var upgoodNum=$("<span class=\"wordAfter\">"+n.upgoodNum+"</span>");
    watchImg.appendTo(popularNum);
    watchNum.appendTo(popularNum);
    commentImg.appendTo(popularNum);
    commentNum.appendTo(popularNum);
    upgoodImg.appendTo(popularNum);
    upgoodNum.appendTo(popularNum);

    popularNum.appendTo(stra_infoDiv);

    stra_infoDiv.appendTo(a_strategy);

    a_strategy.appendTo($(config.contianerId));

};



//字符串转换成二进制
function toBinary(str) {
    var total2str = "";
    for (var i = 0; i < str.length; i++) {
        var num10 = str.charCodeAt(i);  ///< 以10进制的整数返回 某个字符 的unicode编码
        var str2 = num10.toString(2);   ///< 将10进制数字 转换成 2进制字符串

        if( total2str == "" ){
            total2str = str2;
        }else{
            total2str = total2str + " " + str2;
        }
    };
    return  total2str;
}




//获取浏览器类型
//浏览器类型及版本
function getBrowserInfo() {
    var agent = navigator.userAgent.toLowerCase();
    var regStr_ie = /msie [\d.]+;/gi;
    var regStr_ff = /firefox\/[\d.]+/gi
    var regStr_chrome = /chrome\/[\d.]+/gi;
    var regStr_saf = /safari\/[\d.]+/gi;
    var isIE = agent.indexOf("compatible") > -1 && agent.indexOf("msie" > -1); //判断是否IE<11浏览器
    var isEdge = agent.indexOf("edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isIE11 = agent.indexOf('trident') > -1 && agent.indexOf("rv:11.0") > -1;
    if (isIE) {
        var reIE = new RegExp("msie (\\d+\\.\\d+);");
        reIE.test(agent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if (fIEVersion == 7) {
            return "IE/7";
        } else if (fIEVersion == 8) {
            return "IE/8";
        } else if (fIEVersion == 9) {
            return "IE/9";
        } else if (fIEVersion == 10) {
            return "IE/10";
        }
    } //isIE end
    if (isIE11) {
        return "IE/11";
    }
    //firefox
    if (agent.indexOf("firefox") > 0) {
        return agent.match(regStr_ff);
    }
    //Safari
    if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
        return agent.match(regStr_saf);
    }
    //Chrome
    if (agent.indexOf("chrome") > 0) {
        return agent.match(regStr_chrome);
    }
}



//获取json中的数组
function getJsonArray(url,param) {
    var result;
    $.ajax({
        async:false,
        type:"GET",
        url:url,
        dataType:"json",
        success:function (data) {
            result=data[param];
        },
        error:function (e) {
            console.log(e)
        }
    });
    return result;
};

//将什么添加到标签中
function appendWhatToWhere(options) {
    var where=$(options.where);
    var what=options.what;

    $.each(what.content,function (i,n) {
        var item=$(what.label);
        item.html(n);
        item.appendTo(where);
    })
}


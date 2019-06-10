function SortPage(options){
    var defaults={
        contianer:"",
        pageNum:1,
        pageSize:2,
        ajaxData:{
            strategyId:""
        },
        pageCount:0,
        add:function (){
        
        }
    };
    var config = $.extend(defaults, options);
    this.configProperty=config;
    var contianerBox=$(config.contianer);
    $.ajax({
        type:'post',
        url:"getStrategyCommentCount",
        data:{
            strategyId:config.ajaxData.strategyId
        },
        dataType:'json',
        success:function (data) {
            // console.log(data);
            var pageCountTemp=Math.floor(data.data/2);
            if(data.data%2!=0){
                pageCountTemp=pageCountTemp+1;
            }
            config.pageCount=pageCountTemp;
            // config.pageCount=9;
            Arrangement();
        },
        error:function (e) {
            console.log(e);
        }
    });

    addElementsTo();
    // this.Arrangement=function (pageCount){
    //     config.pageCount=pageCount;
    //     Arrangement();
    // }
    function Arrangement(){
        contianerBox.empty();
        // for(var i=1;i<=config.pageCount;i++){
        var i1,i2;
        if(config.pageCount<=5){
            i1=1;i2=config.pageCount;
        }else{
            if(config.pageNum-2<=1){
                i1=1;
                i2=5;
            }else if(config.pageNum+2>config.pageCount){
                i1=config.pageCount-4;
                i2=config.pageCount;
            }else{
                i1=config.pageNum-2;
                i2=config.pageNum+2;
            }
        }
        for(var i=i1,j=1;i<=i2;i++,j++){
            if(i1!=1 && j==1){
                $("<span style=\"padding-left: 5px\">...</span>").appendTo(contianerBox);
            }

            var pageNumBtn=$("<span class=\"pageNumBtn on-put\" style=\"padding-left: 5px\">"+i+"</span>&nbsp;&nbsp;");
            if(config.pageNum==i){
                pageNumBtn.css({
                    "color":"skyblue",
                    "fontSize":"18px"
                })
            }
            pageNumBtn.click(function (){
                pageNumChange(parseInt(this.innerHTML));
            })
            pageNumBtn.appendTo(contianerBox);
            if(i2!=config.pageCount && j==5){
                $("<span style=\"padding-left: 5px\">...</span>").appendTo(contianerBox);
            }
        }
    }

    this.pageNumChange=function(pageNumNow){
        pageNumChange(pageNumNow);
    }
    
    function pageNumChange(pageNumNow){
        if(pageNumNow<=0){
            layer.msg("已经是第一页了");
            return;
        }
        if(pageNumNow>config.pageCount){
            layer.msg("已经是最后一页了");
            return;
        }
        config.pageNum=pageNumNow;
        Arrangement();
        addElementsTo();
    }
    
    function addElementsTo(){
        $.ajax({
            type:'post',
            url:"getStrategyCommentSort",
            data:{
                pageNum:config.pageNum,
                pageSize:config.pageSize,
                strategyId:config.ajaxData.strategyId
            },
            dataType:'json',
            success:function (data) {
                // console.log(data);
                config.add(data.data);
            },
            error:function (e) {
                console.log(e);
            }
        })
    }
}
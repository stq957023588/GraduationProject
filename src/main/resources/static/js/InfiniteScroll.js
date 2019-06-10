var pageNum=0;
var Container;
var url;
var ajaxParam;
var cycleFunction;
function getPageNum() {
    pageNum++;
    return pageNum;
};

function init(option) {
    Container=$(option.container);
    url=option.url;
    ajaxParam=option.ajaxParam;
    cycleFunction=option.cycleFunction;
    getData();
};

var getDataLock=false;
function getData() {
    if(getDataLock){
        return;
    }
    getDataLock=true;
    $("#loadingImg").show();
    ajaxParam.page=getPageNum();
    $.ajax({
        type: "POST",
        url: url,
        data: ajaxParam,
        success: function (data) {
            $("#loadingImg").hide();
            getDataLock=false;
            if(data.length==0){
                if(pageNum==1){
                    Container.html("no data");
                }else {
                    noMoreData();
                    noMoreData=function () {};
                }
            }else {
                console.log(data);
                $.each(data.data,function (i,n) {
                    var row=$('<div class="row"><div/>')
                    var col=$('<div class="col-md-4 col-md-offset-1 well well-sm"></div>')
                    col.html(n);
                    col.appendTo(row);
                    row.appendTo(Container);

                });
            }
        },
        error: function () {

            Container.html('出错啦');
        },
        dataType: 'json'
    });

};


function noMoreData() {
    var item=$('<div><div/>');
    item.html("no more data");
    item.appendTo(Container);
    document.onscroll=function(){ };
}
document.onscroll=function(){
    if($(window).scrollTop()+1>=$(document).height()-$(window).height()){
        getData();
    }
};
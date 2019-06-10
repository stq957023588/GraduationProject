var config={
    containerId:"",
    ajaxUrl:"",
    ajaxParam:"",
    cycleFunction:function (){

    },
    pageSize:5,
}
var currPageNum=0;
function setConfig(options){
    config=$.extend(config,options);
}

var getDataLock=false;
function getData(){
    if(getDataLock){
        return;
    }
    getDataLock=true;

}


function testExtend(){
    console.log(config.containerId);
    config.cycleFunction();
}
var previewImg=$('<img>');
var uploadBtn=$('<input type="file" accept="image/*">');


function uploadImgPreviewInit(options) {
    var defaults={
        resetId: "",
        name: "file",
        height: "100px",
        width: "100px",
        defaultImg: "",
        containerId: "",
        inputId:"",
        allowType: ["gif", "jpeg", "jpg", "bmp", "png"],
        success:$.noop, //上传成功时的回调函数,
        inputChangeFun:function (){
            
        }
    };
    var config = $.extend(defaults, options);

    var defaultImg=config.defaultImg;
    var allowType = config.allowType;
    var success = config.success;

    var container=$(config.containerId);
    container.css({
        "height":config.height,
        "width" :config.width
    });

    $(config.resetId).click(function () {
        previewImg.attr('src', defaultImg);
    });

    previewImg.attr("src",defaultImg);
    previewImg.css({
        "height":config.height,
        "width" :config.width
    });
    if(config.inputId!=""){
        uploadBtn.attr("id",config.inputId);
    }
    uploadBtn.attr("name",config.name);
    uploadBtn.appendTo(container);
    previewImg.appendTo(container);
    uploadBtn.hide();

    uploadBtn.click(function () {
        // console.log("btn click");
    });

    previewImg.click(function () {
        // console.log("img click");
        uploadBtn.click();
    });
    uploadBtn.change(function () {
        var $file = $(this);
        var fileObj = $file[0];
        var windowURL = window.URL || window.webkitURL;
        var dataURL;
        var $img = previewImg;


        if (fileObj && fileObj.files && fileObj.files[0]) {
            dataURL = windowURL.createObjectURL(fileObj.files[0]);
            $img.attr('src', dataURL);
        } else {
            $img.attr('src', defaultImg);
            // dataURL = $file.val();
            // var imgObj = previewImg;
            // // 两个坑:
            // // 1、在设置filter属性时，元素必须已经存在在DOM树中，动态创建的Node，也需要在设置属性前加入到DOM中，先设置属性在加入，无效；
            // // 2、src属性需要像下面的方式添加，上面的两种方式添加，无效；
            // imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            // imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
        }

        config.inputChangeFun();

    });
}


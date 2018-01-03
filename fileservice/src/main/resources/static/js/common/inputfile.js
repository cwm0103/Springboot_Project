/**
 * Created by chenwangming on 2017/9/5.
 * 上传文文件脚本
 */



//http://plugins.krajee.com/file-input#events  API
(function (window, $) {
    var UrlArr=[];
    var fileUpload={
        params: {
            language:"zh",
            uploadUrl:"/file/upload",
            allowedFileExtensions:['jpg', 'png','gif','txt'],
            maxFileSize:100,
            allowedPreviewTypes:[],//, 'html', 'text', 'video', 'audio', 'flash', 'object'
            initialPreview:[],
            initialPreviewConfig:[],
        },
        init:function (obj,params) {
            this.tiggerObj = obj;
            this.params = $.extend(this.params, params);
            this.loadDowm(obj);
            return this;
        },
        loadDowm:function (obj) {
            var self = this;
            $(obj).fileinput({
                language: self.params.language,//'zh',//语言
                uploadUrl:self.params.uploadUrl,// '/file/uploadFile',//上传路径
                allowedFileExtensions :self.params.allowedFileExtensions, //['jpg', 'png','gif','txt'],//['jpg', 'png','gif'],//允许上传的扩展名
                maxFileSize:self.params.maxFileSize,//maxFileSize,//设置大小
                allowedPreviewTypes:self.params.allowedPreviewTypes, //['image'],//['image', 'html', 'text', 'video', 'audio', 'flash', 'object']
                previewFileIconSettings: {
                    'docx': '<i class="fa fa-file-word-o text-primary"></i>',
                    'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
                    'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
                    'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
                    'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
                    'sql': '<i class="fa fa-file-word-o text-primary"></i>',
                    'doc': '<i class="fa fa-file-word-o text-primary"></i>',
                    'xls': '<i class="fa fa-file-excel-o text-success"></i>',
                    'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
                    'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
                    'txt': '<i class="fa fa-file-photo-o text-warning"></i>',
                },
                initialPreview:self.params.initialPreview,
                initialPreviewConfig:self.params.initialPreviewConfig

            }).on("filesuccessremove",function (event,previewId,index) {
                var file_id= $("#"+previewId+" input[type='hidden']").val();
                if(file_id!=null)
                {
                    $.ajax({
                        type: "POST",
                        cache:false,
                        async : true,
                        dataType : "json",
                        url:  "/file/delete",
                        data: {fileid:file_id},
                        success: function(data){
                            if(data.ok!=undefined)
                            {
                                return true;
                            }else
                            {
                                alert(data.no);
                                return false;
                            }
                        }
                    });
                }else {
                    return false;
                }
            }).on("fileuploaded",function (event, data, previewId, index) {
                var res = data.response;
                var ress=res.ok;
                UrlArr[ress]=res.ok.file_path;
                $("#"+previewId+" input[type='hidden']").val(ress);
                self.params.success(UrlArr);
            });
        },
    };
    $.fn.inputfile = function (options) {
        return fileUpload.init(this, options);
    };
})(window, jQuery);
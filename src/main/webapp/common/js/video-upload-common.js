var VideoEditor = {
	videoeditor : null,
	// 初始化编辑器，主要是隐藏主题框架，给特定的文本框复制
	initText : function(id, btn) {
		$(document.body).append('<div id="auto_video_ueditor_init"></div>');
		var videoeditor = this.getEditor('auto_video_ueditor_init');
		videoeditor.ready(function(){
			videoeditor.addListener('afterUpvideo', function (t, args) {
				var src = args[0].id;
				/*$("#"+id).val(src);*/
				$("#fileText").val(args[0].id);
			});
			videoeditor.setDisabled();
			videoeditor.hide();
		});
		$('#' + btn).click(function() {
			videoeditor.getDialog("insertvideo").open();
		});
	},
	// 获取文件上传所依赖的编辑器
	getEditor : function(id) {
		this.videoeditor = UE.getEditor(id);
		return this.videoeditor;
	}
};
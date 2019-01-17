var FileEditor = {
	fileeditor : null,
	// 初始化编辑器，主要是隐藏主题框架，给特定的文本框复制
	initText : function(id, btn) {
		$(document.body).append('<div id="auto_file_ueditor_init"></div>');
		var fileeditor = this.getEditor('auto_file_ueditor_init');
		fileeditor.ready(function(){
			fileeditor.addListener('afterUpfile', function (t, args) {
				var src = args[0].id;
				$("#"+id).val(src);
				$("#filePath").val(args[0].src);
			});
			fileeditor.setDisabled();
			fileeditor.hide();
		});
		$('#' + btn).click(function() {
			fileeditor.getDialog("attachment").open();
		});
	},
	// 获取文件上传所依赖的编辑器
	getEditor : function(id) {
		this.fileeditor = UE.getEditor(id);
		return this.fileeditor;
	}
};
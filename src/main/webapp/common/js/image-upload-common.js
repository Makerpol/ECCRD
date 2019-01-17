var ImageEditor = {
	editor : null,
	// 初始化编辑器，主要是隐藏主题框架，给特定的文本框复制
	initText : function(id, btn, showcover, imageCount) {
		$(document.body).append('<div id="auto_ueditor_init" width=500></div>');
		var editor = this.getEditor('auto_ueditor_init');
		editor.ready(function(){
			editor.addListener('beforeInsertImage', function (t, args) {
				var ids = '';
				var count = 0;
				$(args).each(function(index,row){
					ids += row.id + ',';
					count = index + 1;
				})
				$("#"+id).val(ids);
				if (showcover) {
					$("#"+showcover).attr('src', args[0].src);
					$("#"+showcover).attr('alt', args[0].alt);
				}
				if (imageCount) {
					$("#"+imageCount).html(count);
				}
			});
			editor.setDisabled();
			editor.hide();
		});
		$('#' + btn).click(function() {
			editor.getDialog("insertimage").open();
		});
	},
	// 获取图片上传所依赖的编辑器
	getEditor : function(id) {
		this.editor = UE.getEditor(id);
		return this.editor;
	}
};
// 初始化上传按钮 三个参数为：最终写入的Text，出发按钮，最终显示的DIV
// ImageEditor.initText('imgText', 'selectImg', 'showcover');
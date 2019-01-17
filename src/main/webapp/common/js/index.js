$(function(){
	// tab菜单控制
	$(".tab-model").each(function(index, elm){
		$($(elm).find('.tab-body')[0]).show();
		$(elm).find('ol li').mouseover(function(){
			var i = $(this).index();
			$(elm).find('ol li').each(function(index, li){
				$(li).removeClass('active');
			});
			$(elm).find('.tab-body').each(function(index, div){
				if (index == i) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});
			$(this).addClass('active');
		});
	});
	
})

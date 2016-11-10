//新增

$(function() {
	$("#javawebform").validationEngine({
		showPrompts : false,
		addFailureCssClassToField : "u-ipt-err"
	});

	$("#javawebform").ajaxForm({
		dataType : 'json',
		success : function(data) {
			if (data.result) {
				layer.alert(data.msg, {
					icon : 1
				}, function(index) {
					layer.close(index);
					$.artTabs({
						isRefresh : true
					});
				});
			} else {
				layer.alert(data.msg, {
					icon : 2
				});
			}
		}
	});
});

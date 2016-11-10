/**
 * 页面 menu/add.ftl中引入的Js
 */
$(function() {
	$("#add_form").validationEngine({
		showPrompts : false,
		addFailureCssClassToField : "u-ipt-err"
	});
	// 表单提交后获取
	$('#add_form').ajaxForm({
		dataType : 'json',
		success : function(data) {
			if (data.result) {
				layer.alert("保存成功，将刷新页面", {
					icon : 1
				}, function(index) {
					layer.close(index);
					window.location.reload();
				});
			} else {
				layer.alert(data.errors, {
					icon : 2
				});
			}
		}
	});

	$("#parentId").change(function() {
		var value = $(this).val();
		if (value == "") {
			$("#href").attr("readonly", "readonly");
			$("input[name='menuType']").val('1');
			$("#href").val("");
			$(".grandparents").css({
				"display" : "",
				"float" : "none"
			});
		} else {
			$("#href").removeAttr("readonly");
			$("input[name='menuType']").val('2');
			$(".grandparents").hide();
		}
	});
	$("#parentId").change();
});
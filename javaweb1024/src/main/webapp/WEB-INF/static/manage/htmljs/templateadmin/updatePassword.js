/**
 * 页面 admin/update.ftl中引入的Js
 */
$(function(){
	$("#update_admin_form").validationEngine();
	//表单提交后获取
	$('#update_admin_form').ajaxForm({
		dataType : 'json',
		success : function(data) {
			if (data.result) {
				bootbox.alert("保存成功，将刷新页面", function() {
					window.location.reload();
				});
			} else {
				layer.alert(data.msg, {icon: 2});
			}
		}
	});
});
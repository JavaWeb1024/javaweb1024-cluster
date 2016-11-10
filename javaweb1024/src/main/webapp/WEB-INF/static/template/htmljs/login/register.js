/**
 * 
 */
/**
 * 页面 admin/add.ftl中引入的Js
 */
$(function () {
    $("#add_templateadmin_form").validationEngine({
        showPrompts: false,
        addFailureCssClassToField: "u-ipt-err"
    });
    $("#add_templateadmin_form").ajaxForm({
		dataType : 'json',
		success : function(data) {
			if (data.result) {
				layer.alert(data.msg, {
					icon : 1
				}, function(index) {
					layer.close(index);
					top.location=base;
				});
			} else {
				layer.alert(data.msg, {
					icon : 2
				});
			}
		}
	})
});
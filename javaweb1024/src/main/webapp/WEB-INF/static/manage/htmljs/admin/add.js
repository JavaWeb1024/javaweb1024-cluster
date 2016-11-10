/**
 * 页面 admin/add.ftl中引入的Js
 */
$(function () {
    $("#add_admin_form").validationEngine({
        showPrompts: false,
        addFailureCssClassToField: "u-ipt-err"
    });
    // 表单提交后获取
    $('#add_admin_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                layer.alert("保存成功，将刷新页面", {
                    icon: 1
                }, function (index) {
                    layer.close(index);
                    $.artTabs({
                        isRefresh: true
                    });
                });
            } else {
                layer.alert(data.msg, {
                    icon: 2
                });
            }
        }
    });
});
/**
 * 页面 admin/update.ftl中引入的Js
 */
$(function () {
    $("#update_admin_form").validationEngine({
        showPrompts: false,
        addFailureCssClassToField: "u-ipt-err"
    });
    //表单提交后获取
    $('#update_admin_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.result) {
                layer.alert("修改成功，将刷新页面", {
                    icon: 1
                }, function (index) {
                    layer.close(index);
                    $.artTabs({
                        isRefresh: true
                    });
                });
            } else {
                layer.alert(data.msg, {icon: 2});
            }
        }
    });
});
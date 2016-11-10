/**
 * 页面 admin/update.html中引入的Js
 */
$(function () {
    $("#update_admin_form").validationEngine();
    //表单提交后获取
    $('#update_admin_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.result) {
                layer.alert("密码修改成功!", {
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
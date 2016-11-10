/**
 * 页面 menu/add.html中引入的Js
 */
$(function () {
    var role_id = "";
    $("#add_form").validationEngine({
        showPrompts: false,
        addFailureCssClassToField: "u-ipt-err"
    });
    //表单提交后获取
    $('#add_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.result) {
                layer.alert("保存成功", {
                    icon: 1
                }, function (index) {
                    role_id = data.t;
                    layer.close(index);
                    role_id = data.t;
                    $("input[name='id']").val(role_id);
                });
            } else {
                layer.alert(data.msg, {icon: 2});
            }
        }
    });
    $(".menu_id").on("click", function () {
        $(this).attr("checked","checked");
        if (role_id == "") {
            layer.alert("请先执行保存第一步的数据", {icon: 2});
            return;
        }
        var menus = $(this).attr("menu_id");
        layer.open({
            type: 2,
            title: '附相应权限',
            shadeClose: true,
            shade: 0.6,
            area: ['90%', '90%'],
            content: base + '/manage/role/add_authorization.jhtml?roles=' + role_id + "&menus=" + menus //iframe的url
        });
    });
});
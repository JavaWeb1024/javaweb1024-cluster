/**
 * 页面 admin/add.ftl中引入的Js
 */
$(function () {
    $("#add_form").validationEngine({
        showPrompts: false,
        addFailureCssClassToField: "u-ipt-err"
    });
    // 表单提交后获取
    $('#add_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                layer.alert("保存成功", {
                    icon: 1
                }, function (index) {
                    layer.close(index);
                    var html = "<tr><td>" + $('#authorizationDescription').val() + "</td><td>" + $('#authorizationName').val() + "</td>" + "<td> <a href=\"javascript:void (0);\" title=\"删除\" data-val=" + data.msg + " class=\"delete\"><i class=\" icon-remove-circle\"></i></td></tr>"
                    $("#tbody").append(html);
                    $('#authorizationDescription,#authorizationName').val("");
                });
            } else {
                layer.alert("操作失败", {
                    icon: 2
                });
            }
        }
    });
    //删除
    $(".delete").live("click", function () {
        var id = $(this).data("val");
        var tr =  $(this).closest("tr");
        layer.msg('你确定要删除此条信息吗？', {
            time: 0, // 不自动关闭
            shadeClose: true, // 开启遮罩关闭
            shade: 0.5,
            btn: ['删除', '取消'],
            yes: function (index) {
                $.ajax({
                    type: "POST",
                    url: base + '/manage/role/delete_authorization.jhtml?id=' + id,
                    dataType: "json",
                    error: function (data) {
                        layer.alert("操作失败,请联系管理员!", {
                            icon: 3
                        });
                    },
                    success: function (data) {
                        // 删除成功
                        if (data.success) {
                            layer.alert(data.msg, {
                                icon: 1
                            }, function (index) {
                                layer.close(index);
                                $(tr).remove();
                            });
                        } else {// 失败
                            layer.alert(data.msg, {
                                icon: 2
                            }, function (index) {
                                layer.close(index);
                            });
                        }
                    }
                });
            }
        });
    });
});
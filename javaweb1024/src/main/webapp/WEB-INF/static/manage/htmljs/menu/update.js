/**
 * 页面 menu/add.ftl中引入的Js
 */
$(function () {
    $("#update_form").validationEngine();
    // 表单提交后获取
    $('#update_form').ajaxForm({
        dataType: 'json',
        success: function (data) {
            if (data.result) {
                layer.alert("保存成功，将刷新页面", {
                    icon: 1
                }, function (index) {
                    layer.close(index);
                    $.artTabs({
                        isRefresh : true
                    });
                });
            } else {
                layer.alert(data.msg, {
                    icon: 2
                });
            }
        }
    });
    $("#deleteMenu").click(function () {
        var id = $(this).data("val");
        layer.msg('你确定要删除此条信息吗？', {
            time: 0, // 不自动关闭
            shadeClose: true, // 开启遮罩关闭
            shade: 0.5,
            btn: ['删除', '取消'],
            yes: function (index) {
                $.ajax({
                    type: "POST",
                    url: '/manage/menu/deleteJson.jhtml?id=' + id,
                    dataType: "json",
                    error: function (data) {
                        layer.alert("操作失败,请联系管理员!", {
                            icon: 3
                        });
                    },
                    success: function (data) {
                        // 删除成功
                        if (data.result) {
                            layer.alert(data.msg, {
                                icon: 1
                            }, function (index) {
                                layer.close(index);
                                $.artTabs({
                                    closeTab: true,
                                    toTab: {id : "25"},
                                    isRefresh: true
                                });
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
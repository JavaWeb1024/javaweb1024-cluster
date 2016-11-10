/**
 * 页面 role/list.ftl中引入的Js
 */
$(function () {
    $(".js_delete_role").click(function () {
        var id = $(this).data("value");
        var tr = $(this).closest("tr");
        layer.msg('你确定要删除此角色的所有信息吗？', {
            time: 0, // 不自动关闭
            shadeClose: true, // 开启遮罩关闭
            shade: 0.5,
            btn: ['删除', '取消'],
            yes: function (index) {
                $.ajax({
                    type: "POST",
                    url: base + '/manage/role/deleteJson.jhtml?id=' + id,
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

    /**
     * 新增角色
     */
    $("#add_role").on("click", function () {
        layer.open({
            type: 2,
            title: '新增角色',
            shadeClose: true,
            shade: 0.5,
            area: ['80%', '80%'],
            content: base + '/manage/role/add.jhtml' //iframe的url
        });
    });

    /**
     * 修改角色
     */
    $(".js_update_role").on("click", function () {
        var id=$(this).data("value");
        layer.open({
            type: 2,
            title: '修改角色',
            shadeClose: true,
            shade: 0.5,
            area: ['80%', '80%'],
            content: base + '/manage/role/update.jhtml?id='+id
        });
    });
});
/**
 * admin/manage.html页面JS
 */

$(function () {
    /**
     * 删除操作
     */
    $(".js_delete_admin").click(
        function () {
            var id = $(this).attr('adminid');
            layer.msg('你确定要删除此条信息吗？', {
                time: 0, // 不自动关闭
                shadeClose: true, // 开启遮罩关闭
                shade: 0.5,
                btn: ['删除', '取消'],
                yes: function (index) {
                    $.ajax({
                        type: "POST",
                        url: base + '/manage/admin/deleteJson.jhtml?id='
                        + id,
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
    /**
     * 新增管理员
     */
    $("#add_manager").on("click", function () {
        layer.open({
            type: 2,
            title: '新增管理员',
            shadeClose: true,
            shade: 0.6,
            area: ['80%', '60%'],
            content: base + '/manage/admin/add.jhtml' //iframe的url
        });
    });
    /**
     * 修改管理员
     */
    $(".js_update_admin").on("click", function () {
        var id=$(this).attr("adminid");
        layer.open({
            type: 2,
            title: '修改管理员',
            shadeClose: true,
            shade: 0.6,
            area: ['80%', '60%'],
            content: base + '/manage/admin/update.jhtml?id='+id //iframe的url
        });
    });
});

/**
 * 页面 menu/add.ftl中引入的Js
 */
$(function () {
    $(".menu_id").on("click", function () {
        var role_id = $(this).attr("role_id");
        var menus = $(this).attr("menu_id");
        $(this).siblings("input[name='menus']").attr("checked", "checked");
        layer.open({
            type: 2,
            title: '附相应权限',
            shadeClose: true,
            shade: 0.6,
            area: ['90%', '90%'],
            content: base + '/manage/role/add_authorization.jhtml?roles=' + role_id + "&menus=" + menus //iframe的url
        });
    });

    $("input[type='checkbox']").on("click", function () {
        var checkbox =$(this);
        if (!$(this).prop("checked")) {
            var menu_id = $(this).val();
            var role_id = $(this).attr("role_id");
            layer.msg('你确定要删除此角色的菜单信息吗？', {
                time: 0, // 不自动关闭
                shadeClose: true, // 开启遮罩关闭
                shade: 0.5,
                btn: ['删除', '取消'],
                yes: function (index) {
                    $.ajax({
                        type: "POST",
                        url: base + '/manage/role/updateJson.jhtml?menu_id=' + menu_id + "&role_id=" + role_id,
                        dataType: "json",
                        error: function (data) {
                            layer.alert("操作失败,请联系管理员!", {
                                icon: 3
                            });
                        },
                        success: function (data) {
                            // 删除成功
                            if (data.success) {
                                layer.alert("取消成功!", {
                                    icon: 1
                                }, function (index) {
                                    layer.close(index);
                                });
                            } else {// 失败
                                layer.alert("取消失败", {
                                    icon: 2
                                }, function (index) {
                                    layer.close(index);
                                });
                            }
                        }
                    });
                },
                end:function(index){
                    $(checkbox).prop("checked",true);
                }
            });
        }
    });

});
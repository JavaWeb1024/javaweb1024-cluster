/**
 * 页面 menu/list.html中引入的Js
 */
$(function () {
    $(".js_delete_menu").click(function () {
        var id = $(this).attr("adminid");
        layer.msg('你确定要删除此条信息吗？', {
            time: 0, // 不自动关闭
            shadeClose: true, // 开启遮罩关闭
            shade: 0.5,
            btn: ['删除', '取消'],
            yes: function (index) {
                $.ajax({
                    type: "POST",
                    url: base + '/manage/menu/deleteJson.jhtml?id=' + id,
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
    // 新增子节点
    $(".addChildren").on("click", function () {
        var parentId = $(this).attr("adminid");
        var url = base + "/manage/menu/add_children.jhtml?parentId=" + parentId;
        artTabs({
            addTab: {
                items: {
                    id: 'addChildren',
                    title: '新增子菜单',
                    url: url
                }
            }
        });
    });
    // 编辑父节点
    $(".editParent").on("click", function () {
        var parentId = $(this).attr("adminid");
        var url = base + "/manage/menu/update.jhtml?id=" + parentId + "&type=1";
        artTabs({
            addTab: {
                items: {
                    id: 'editParent',
                    title: '编辑父节点',
                    url: url
                }
            }
        });
    });
    $(".editChild").on("click", function () {
        var parentId = $(this).attr("adminid");
        var url = base + "/manage/menu/update.jhtml?id=" + parentId + "&type=2";
        artTabs({
            addTab: {
                items: {
                    id: 'editChild',
                    title: '编辑子节点',
                    url: url
                }
            }
        });
    });
    /**
     * 新增祖籍菜单使用
     */
    $("#add_menu").on("click", function () {
        var url = base + "/manage/menu/add_menu.jhtml";
        artTabs({
            addTab: {
                items: {
                    id: 'add_menu',
                    title: '新增祖籍节点',
                    url: url
                }
            }
        });
    });
    /**
     * 新增祖籍菜单使用
     */
    $("#add_parent").on("click", function () {
        var url = base + "/manage/menu/add_parent.jhtml";
        artTabs({
            addTab: {
                items: {
                    id: 'add_parent',
                    title: '新增父节点',
                    url: url
                }
            }
        });
    });
});
/**
 * admin/manage.ftl页面JS
 */

$(function() {
    /**
     * 删除操作
     */
    $(".js_delete_admin").click(function() {
	var id = $(this).attr('adminid');
	layer.msg('你确定要删除此条信息吗？', {
	    time : 0, // 不自动关闭
	    shadeClose : true, // 开启遮罩关闭
	    shade : 0.5,
	    btn : [ '删除', '取消' ],
	    yes : function(index) {
		$.ajax({
		    type : "POST",
		    url : base + '/manage/templateadmin/deleteJson.jhtml?id=' + id,
		    dataType : "json",
		    error : function(data) {
			layer.alert("操作失败,请联系管理员!", {
			    icon : 3
			});
		    },
		    success : function(data) {
			// 删除成功
			if (data.result) {
			    layer.alert(data.msg, {
				icon : 1
			    }, function(index) {
				layer.close(index);
				window.location.reload();
			    });
			} else {// 失败
			    layer.alert(data.msg, {
				icon : 2
			    }, function(index) {
				layer.close(index);
			    });
			}
		    }
		});
	    }
	});
    });
});

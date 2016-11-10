/**
 * 
 */
$(function() {
	$("#tableId").jqGrid({
		datatype : "json",
		url : base + '/template/product/getProducts.jhtml',
		mtype : 'POST',
		colNames : ['', '产品名称', '产品编号', '生产产地', '产品容量', '产品材质', '产品类别', '生产日期', '生产企业', '操作'],
		colModel : [{
			name : 'id',
			index : 'id',
			hidden : true
		}, {
			name : 'productName',
			index : 'productName'
		}, {
			name : 'productNumber',
			index : 'productNumber'
		}, {
			name : 'productHome',
			index : 'productHome'
		}, {
			name : 'productCapacity',
			index : 'productCapacity'
		}, {
			name : 'productMaterial',
			index : 'productMaterial'
		}, {
			name : 'productCategory',
			index : 'productCategory',
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == "1") {
					return "酒水";
				} else {
					return "文物";
				}
			}
		}, {
			name : 'productDate',
			index : 'productDate',
			formatter : 'date',
			formatoptions : {
				srcformat : 'u',
				newformat : 'Y-m-d'
			}
		}, {
			name : 'productCompany',
			index : 'productCompany'
		}, {
			formatter : act,
			align:'center'
		}],
		rowNum : 10,
		rowList : [10, 20, 30],
		pager : '#pager',
		toolbarfilter : true,
		height : '100%',
		viewrecords : true,
		autowidth : true,
		rownumbers : true,
		prmNames : {
			page : "pageNum", // 表示请求页码的参数名称
			rows : "rows", // 表示请求行数的参数名称
			sort : "page.oid", // 表示用于排序的列名的参数名称
			order : "page.oor", // 表示采用的排序方式的参数名称
			search : "issearch", // 表示是否是搜索请求的参数名称
			nd : "nd", // 表示已经发送请求的次数的参数名称
			state : "isstate"
		},
		jsonReader : { // 返回数据格式设置
			root : "rows",
			records : "records",
			total : "total",
			repeatitems : false
		},
		successTip : false,
		multiselect : false
	});
	function act(cellvalue, options, rowObject) {
		var arr_html = [];
		arr_html.push("<div class='operating'><a href='javascript:' onclick=\"showButton('" + rowObject.id
				+ "');\" class='operating-comment' title='详情'></a><a href='javascript:void(0);' onclick='updateButtom(" + rowObject.id
				+ ")' class='operating-pencil' title='修改'></a><a href='javascript:void(0);' onclick='deleteButtom(" + rowObject.id + ")' class='operating-trash' title='删除'></a></div>");

		return arr_html.join("");
	}
	// 搜索
	$('#search').click(function() {
		$("#tableId").jqGrid('setGridParam', {
			postData : {
				productName : $("input[name='productName']").val(),
				productNumber : $("input[name='productNumber']").val()
			}
		});
		$("#tableId").jqGrid().trigger("reloadGrid");
	});

	/**
	 * 新增商品
	 */
	$("#addProducts").click(function() {
		layer.open({
			type : 2,
			title : '新增商品',
			shadeClose : true,
			shade : 0.6,
			area : ['80%', '60%'],
			content : base + '/template/product/addProducts.jhtml' // iframe的url
		});
	});

});
/**
 * 展示商品信息
 * 
 * @param id
 */
function showButton(id) {
	layer.open({
		type : 2,
		title : '商品信息展示',
		shadeClose : true,
		shade : 0.6,
		area : ['80%', '60%'],
		content : base + '/template/product/show_products.jhtml?id=' + id // iframe的url
	});
}
/**
 * | 修改商品信息
 * 
 * @param id
 */
function updateButtom(id) {
	layer.open({
		type : 2,
		title : '修改商品',
		shadeClose : true,
		shade : 0.6,
		area : ['80%', '60%'],
		content : base + '/template/product/update_products.jhtml?id=' + id // iframe的url
	});
}

/**
 * 删除商品
 * 
 * @param id
 */
function deleteButtom(id) {
	layer.msg('你确定要删除此条信息吗？', {
		time : 0, // 不自动关闭
		shadeClose : true, // 开启遮罩关闭
		shade : 0.5,
		btn : ['删除', '取消'],
		yes : function(index) {
			$.ajax({
				type : "POST",
				url : base + '/template/product/deleteProducts.jhtml?id=' + id,
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
							$.artTabs({
								isRefresh : true
							});
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
}

/*
1、跳转到相应的tab页，是否刷新，完全只需要调用一个方法，在方法中传入指定的id接口就可以。
skipAppoint(id,isRefresh) 这里还需要考虑，原来的页面可能不小心已经不存在了的情况，可能需要作个判断，如果不存在，就重新打开一个页面

2、在iframe中关闭当前tab页，这个可以单独一个方法，可以在其他方法之后调用

3、iframe中新增或者修改窗口
function childAddIframe(title,url,id,isRefresh,isReturn)

*/



var idArray = [];
$(function(){
	$('#0Con').css({'overflow':'auto'}).height($(window).height()-76);
	
	tabCloseEven();	
	/*nav效果*/
	var getLi = $('#navCon ul').find('li');
	getLi.hover(function(){
		$(this).children('dl').fadeIn(300);	
	},function(){
		$(this).children('dl').fadeOut(0);
	});
	
	
	//功能模块开始
	
	/*绑定nav上的链接打开*/
	for(var i=0; i<getLi.length; i++){
		getLi.eq(i).find('dd').click(function(){
			
			var itemData = {};
			itemData.url = $(this).find('a').attr('href');
			itemData.title = $(this).find('a').text();
			itemData.id = $(this).find('a').attr('id');		
			itemData.height = $(window).height() - 76;
			
			if(jQuery.inArray(itemData.id, idArray) == -1){
				$('#tabCon').append(strIframe(itemData.id,itemData.url,itemData.height));
				$('#tabList').append(strTablist(itemData.id,itemData.title));
				idArray.push(itemData.id);
				$('#'+ itemData.id +'list').addClass('on').siblings().removeClass('on');
				$('#'+ itemData.id +'Con').siblings('div').hide();
			}else{
				$('#'+ itemData.id +'list').addClass('on').siblings().removeClass('on');
				$('#'+ itemData.id +'Con').show().siblings('div').hide();
			}
			return false;
		})
	}
	
	/*绑定li的click事件*/
	$('#tabList').delegate('li','click',function(){
		var getListId = $(this).attr('id');
		var i = getListId.indexOf('list');
		var getIframeId = getListId.substring(0,i);
		$(this).addClass('on').siblings().removeClass('on');
		$('#'+ getIframeId + 'Con').show().siblings('div').hide();	
	});
	
	/*绑定li的右键事件*/	
	$('#tabList').delegate('li','contextmenu',function(e) {
		var getListId = $(this).attr('id');
		var i = getListId.indexOf('list');
		var getIframeId = getListId.substring(0,i);
		$(this).addClass('on').siblings().removeClass('on');
		$('#'+ getIframeId + 'Con').show().siblings('div').hide();
		
		var getIndex_id = $('#tabList').find('li').eq(0).attr('id');
		if(getIndex_id == getListId){
			return false;
		}else{
			$('#mm').show().css({'top':e.pageY-2,'left':e.pageX-4});
			return false;
		}
    });
	
	$('#mm').find('.menu-item').hover(function(){
		$(this).addClass('menu-active');
	},function(){
		$(this).removeClass('menu-active');
	});
	$('#mm').bind('mouseleave',function(){
		$(this).hide();	
	})
	
	
	var left = 0;
	var v_wid = 120;
	$('#tabLeft').on('click',function(){
		var getConWidth = $('#tabList').parent().innerWidth();
		var getUlWidth = 0;
		var getLi = $('#tabList').find('li');
		for(var i=0;  i< getLi.length; i++){
			getUlWidth = getUlWidth + getLi.eq(i).outerWidth(true);
		}
		if(left >= 0){
			return false;
		}else{
			$('#tabList').animate({"left": left + v_wid});
			left = left + v_wid;
		}
	});
	
	$('#tabRight').on('click',function(){
		var getConWidth = $('#tabList').parent().innerWidth();
		var getUlWidth = 0;
		var getLi = $('#tabList').find('li');
		for(var i=0;  i< getLi.length; i++){
			getUlWidth = getUlWidth + getLi.eq(i).outerWidth(true);
		};
		if(getUlWidth<=getConWidth){
			return false;
		}else if((getUlWidth - Math.abs(left))<=getConWidth){
			return false;	
		}else{
			$('#tabList').animate({"left": left - v_wid});
			left = left - v_wid;
		};
	});
})

/*高度自适应*/
$(window).resize(function(){
	var height = $(window).height() - 76;
	$('#tabCon').children('div').height(height);
});

/*iframe内容生成*/
function strIframe(id,url,height){
	var str = '';
		str += '<div id="'+ id +'Con" style="height:' + height + 'px;" class="m-tab-content"><iframe scrolling="auto" src="../style/js/'+ url +'" frameborder="0" class="m-frameCon"></iframe></div>';
	return str;
}

/*li标签生成*/
function strTablist(id,titText){
	var str = '';
		str += '<li id="'+ id +'list"><span>'+ titText +'</span><a href="javascript:" onclick="closeIframe(this)" class="m-tab-close"></a></li>';
	return str;
}

/*关闭tab页*/
function closeIframe(myself){
	var getListId = $(myself).parent().attr('id');
	var i = getListId.indexOf('list');
	var getIframeId = getListId.substring(0,i);
	var j = jQuery.inArray(getIframeId, idArray);
	idArray.splice(j,1);
	if($(myself).parent().hasClass('on')){
		$('#'+ getIframeId +'list',parent.document).prev().addClass('on');
		$('#'+ getIframeId +'Con',parent.document).prev().show();
		$('#'+ getIframeId +'list').remove();
		$('#'+ getIframeId +'Con').remove();
	}else{
		$('#'+ getIframeId +'list').remove();
		$('#'+ getIframeId +'Con').remove();
	}
}

/*iframe中新增或者修改窗口*/
function childAddIframe(title,url,id,isRefresh,isReturn){
	var isReturn = isReturn || false;
	var height = $(window,parent.document).height()-76;
	if(jQuery.inArray(id, parent.idArray) == -1){
		if(isReturn){
			var iframeId = $('#tabList',parent.document).find('li').filter('.on').attr('id');
			url = url+ '#isReturn='+ iframeId +'';
		}
		$('#tabCon',parent.document).append(strIframe(id,url,height));
		$('#tabList',parent.document).append(strTablist(id,title));
		parent.idArray.push(id);
		$('#'+ id +'list',parent.document).addClass('on').siblings().removeClass('on');
		$('#'+ id +'Con',parent.document).siblings('div').hide();
	}else{
		if(isRefresh){
			$('#'+ id +'list',parent.document).addClass('on').siblings().removeClass('on');
			$('#'+ id +'Con',parent.document).show().siblings('div').hide();
			$('#'+ id +'Con',parent.document).find(".m-frameCon")[0].contentWindow.location.href = url;
		}else{
			$('#'+ id +'list',parent.document).addClass('on').siblings().removeClass('on');
			$('#'+ id +'Con',parent.document).show().siblings('div').hide();
		}
	}
}

/*iframe跳转到相应的页面*/
function skipAppoint(id){
	$('#'+ id +'list',parent.document).addClass('on').siblings().removeClass('on');
	$('#'+ id +'Con',parent.document).show().siblings('div').hide();	
}

/*iframe中关闭当前窗口*/
function childClosedIframe(isRefresh){
	var isRefresh = isRefresh || false;
	var getLi = $('#tabList',parent.document).find('li').filter('.on');
	var getListId = getLi.attr('id');
	var i = getListId.indexOf('list');
	var getIframeId = getListId.substring(0,i);
	
	var getUrl = $('#'+ getIframeId + 'Con',parent.document).find(".m-frameCon")[0].contentWindow.location.href;
	var showListId = parseQueryString(getUrl)['isReturn'];
	if(showListId){
		var i = showListId.indexOf('list');
		var getId = showListId.substring(0,i);
		if(jQuery.inArray(getId, parent.idArray) == -1){
			alert('原来页面不存在，请重新打开');
			return;
		}else{
			//alert(showListId);
			$('#'+ showListId,parent.document).addClass('on');
			if(isRefresh){
				//alert(getId);
				$('#'+ getId + 'Con',parent.document).find(".m-frameCon")[0].contentWindow.location.reload();
			}
			$('#'+ getId +'Con',parent.document).show();
		}
	}else{
		$('#'+ getIframeId +'list',parent.document).prev().addClass('on');
		$('#'+ getIframeId +'Con',parent.document).prev().show();
	}
	//删除数据中的id
	var j = jQuery.inArray(getIframeId, parent.idArray);
	parent.idArray.splice(j,1);
	$('#'+ getIframeId +'list',parent.document).remove();
	$('#'+ getIframeId +'Con',parent.document).remove();
}

//获取url中指定参数
function parseQueryString(url) {
  var params = {};
  var arr = url.split("#");
  if (arr.length <= 1){
    return params; 
  }
  //arr = arr[1].split("&");
  for (var i = 0, l = arr.length; i < l; i++) {
    var a = arr[i].split("=");
    params[a[0]] = a[1];
  }
  return params;
}


//绑定右键菜单事件
function tabCloseEven() {
	
	//刷新
	$('#mm-tabupdate').click(function() {
		var getListId = $('#tabList').find('li').filter('.on').attr('id');
		var i = getListId.indexOf('list');
		var getIframeId = getListId.substring(0,i);	
		$('#'+ getIframeId + 'Con').find(".m-frameCon")[0].contentWindow.location.reload();	
		$('#mm').hide();	
	})
	//关闭当前
	$('#mm-tabclose').click(function() {
		var getListId = $('#tabList').find('li').filter('.on').attr('id');
		var i = getListId.indexOf('list');
		var getIframeId = getListId.substring(0,i);
		var j = jQuery.inArray(getIframeId, idArray);
		idArray.splice(j,1);
		$('#'+ getIframeId +'list').prev().addClass('on');
		$('#'+ getIframeId +'Con').prev().show();
		$('#'+ getIframeId +'list').remove();
		$('#'+ getIframeId +'Con').remove();
		$('#mm').hide();
	})
	//全部关闭
	$('#mm-tabcloseall').click(function() {
		$('#tabList').children('li').each(function(i){
			if(i==0){
				//do something...	
			}else{
				$(this).remove();
			}
		})
		$('#tabCon').children('div').each(function(i){
			if(i==0){
				//do something...	
			}else{
				$(this).remove();
			}
		})
		$('#tabList').find('li').eq(0).addClass('on');
		$('#tabCon').find('div').eq(0).show();
		idArray = [];
		$('#mm').hide();
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		var getListId = $('#tabList').find('li').filter('.on').attr('id');
		var i = getListId.indexOf('list');
		var getIframeId = getListId.substring(0,i);
		var index = $('#tabList').find('li').filter('.on').index();
		$('#tabList').children('li').each(function(i){
			if(i==0 || i==index){
				//do something...	
			}else{
				$(this).remove();
			}
		})
		$('#tabCon').children('div').each(function(i){
			if(i==0 || i==index){
				//do something...	
			}else{
				$(this).remove();
			}
		})
		idArray = [];
		idArray.push(getIframeId);
		$('#mm').hide();
	});
}


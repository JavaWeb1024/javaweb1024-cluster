/*
 *   0. artTabs框架
 *   1. Author : tonyt
 *   2. Version : 1.0 | 20150418
 *   3. Description ：
 *      
 *		----当需要涉及到iframe多层级切换时的功能需求-----
 */
 
var idArray = [];
(function ($) {
var artTabs = function (config) {
	config = config || {};
	var defaults = artTabs.defaults		
	// 合并默认配置
	for (var i in defaults) {
		if (config[i] === undefined) config[i] = defaults[i];		
	};
	
	return new artTabs.fn._init(config);
};

artTabs.fn = artTabs.prototype = {	
	_init: function (config) {
		var that = this;
		that.config = config;
		if($('#mm',top.document).hasClass('m-menu')){
			var curId = that.curId();	//当前操作iframe的Id
			
			//iframe中新增tab页(新增跟修改是同一个意思，只是修改的话需要刷新)
			if(config.addTab !== null){
				if(that.addIframe(config.addTab.items)){		
					if(config.isRefresh){that.refreshIframe(config.addTab.items);};		
					if(config.closeTab){that.closeIframe(curId)};
				}
			}
			//iframe中跳转指定Id的tab页
			if(config.toTab !== null){
				if(that.toIframe(config.toTab.id)){
					if(config.isRefresh){that.refreshIframe(config.toTab.id);};
					if(config.closeTab){that.closeIframe(curId)};
				};
			}
			//iframe中纯粹关闭当前页，默认跳转到上一页
			if(config.toTab == null && config.addTab == null && config.closeTab){
				//alert("关闭");
				that.prevShow(curId);
				that.closeIframe(curId);
			}
			//iframe中纯粹刷新当前页，在导航上已有此功能，一般不需要这样调用
			if(config.toTab == null && config.addTab == null && config.isRefresh){
				//alert("刷新");
				that.refreshIframe(curId);
			}
			return;
		}else{
			that._getMenu(config.menu); 	//加载导航	
			var getLi = $('.m-nav ul').find('li');
			$('#0Con').css({'overflow':'auto'}).height($(window).height()-112);
			that.menuAnimate(getLi);	//添加导航效果
			that.menuClick(getLi);		//绑定menu上的链接打开
			that.closeTab_a();			//绑定li里面a标签的关闭
			that.tabCut();				//绑定li的切换
			that.resize();				//高度自适应
			that.nextPrev();			//左右切换
			that._getRighthand();		//绑定li的右键菜单
			that.tabCloseEven();		//右键菜单功能绑定
		};
		return that;
	},
	curId: function(){
		var $top_doc = top.document;
		var getLi = $('#tabList', $top_doc).find('li').filter('.on');
		var listId = getLi.attr('id');
		var i = listId.indexOf('list');
		var tabId = listId.substring(0,i);
		return tabId;
	},
	addIframe: function(itemData){
		var that = this,
			$top_doc = top.document;
		var itemData = itemData;
			itemData.height = $('#0Con', $top_doc).height();
		if(jQuery.inArray(itemData.id, top.idArray) == -1){
			that._getIframe(itemData);
			that._getTablist(itemData);
			top.idArray.push(itemData.id);
			that.addShow(itemData.id);
		}else{
			that.cutShow(itemData.id);
		}
		return that;
	},
	toIframe: function(tabId){
		var that = this;
		if(jQuery.inArray(tabId, top.idArray) == -1){
			alert('指定ID= "'+ tabId +'" 的页面不存在，请重新打开');
			return false;
		}else{
			that.cutShow(tabId);
		}
		return that;
	},
	closeIframe: function(tabId){
		var that = this;
		var tabId = tabId;
		
		//删除数据中的id
		var j = jQuery.inArray(tabId, top.idArray);
		top.idArray.splice(j,1);
		that.removeTab(tabId);
	},
	tabCut: function(){
		var that = this;
		$('#tabList').delegate('li','click',function(){
			var listId = $(this).attr('id');
			var i = listId.indexOf('list');
			var tabId = listId.substring(0,i);
			that.cutShow(tabId);
		});
	},
	menuClick: function(li_lists){
		var that = this;
		li_lists.each(function(){
			$(this).find('dd').click(function(){
				var itemData = {};
				itemData.url = $(this).find('a').attr('href');
				itemData.title = $(this).find('a').text();
				itemData.id = $(this).find('a').attr('id');		
				itemData.height = $(window).height() - 112;
				
				if(jQuery.inArray(itemData.id, idArray) == -1){
					that._getIframe(itemData);
					that._getTablist(itemData);
					idArray.push(itemData.id);	//给数组传值,记录tab的Id数
					that.addShow(itemData.id);		//新增tab页
				}else{
					that.cutShow(itemData.id);		//已有tab页切换
				}
				return false;
			})
		})
		return that;
	},
	addShow: function(tabId){
		var $top_doc = top.document;
		$('#'+ tabId +'list',$top_doc).addClass('on').siblings().removeClass('on');
		$('#'+ tabId +'Con',$top_doc).siblings('div').hide();
		return this;
	},
	cutShow: function(tabId){
		var $top_doc = top.document;
		$('#'+ tabId +'list',$top_doc).addClass('on').siblings().removeClass('on');
		$('#'+ tabId +'Con',$top_doc).show().siblings('div').hide();
		return this;
	},
	prevShow: function(tabId){
		var $top_doc = top.document;
		$('#'+ tabId +'list',$top_doc).prev().addClass('on');
		$('#'+ tabId +'Con',$top_doc).prev().show();
		return this;
	},
	removeTab: function(tabId){
		var $top_doc = top.document;
		$('#'+ tabId +'list',$top_doc).remove();
		$('#'+ tabId +'Con',$top_doc).remove();
		return this;
	},
	closeTab_a: function(){
		var that = this;	
		$('#tabList').undelegate().delegate('a.m-tab-close','click',function(){
			var listId = $(this).parent().attr('id');
			var i = listId.indexOf('list');
			var tabId = listId.substring(0,i);
			var j = jQuery.inArray(tabId, idArray);
			idArray.splice(j,1);
			if($(this).parent().hasClass('on')){
				that.prevShow(tabId);
				that.removeTab(tabId);
			}else{
				that.removeTab(tabId);
			}	
		})	
		return that;
	},
	nextPrev: function(){
		var left = 0;
		var v_wid = 120;
		$('#tabLeft').on('click',function(){
			var getConWidth = $('#tabList').parent().innerWidth();
			var getUlWidth = 0;
			var getLi = $('#tabList').find('li');
			for(var i=0; i< getLi.length; i++){
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
			for(var i=0; i< getLi.length; i++){
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
	},
	menuAnimate: function(li_lists){
		li_lists.children('span').toggle(function(){
			$(this).removeClass('open').addClass('close');
			$(this).next('dl').slideUp(200);
		},function(){
			$(this).removeClass('close').addClass('open');
			$(this).next('dl').slideDown(200);
		});
		return this;
	},
	resize: function(){
		$(window).resize(function(){
			var height = $(window).height() - 112;
			$('#tabCon').children('div').height(height);
		});
		return this;
	},
	refreshIframe: function(itemData){
		var $top_doc = top.document;
		if(itemData.id){
			$('#'+ itemData.id + 'Con', $top_doc).find(".m-frameCon")[0].contentWindow.location.href = itemData.url;
		}else{
			$('#'+ itemData + 'Con', $top_doc).find(".m-frameCon")[0].contentWindow.location.reload();
		}
		return this;
	},
	_getMenu: function(data){
		var wrap = $('<div class="m-nav"></div>');
		var ul = $('<ul></ul>');
		var h3 = $('<h3>'+ data.title +'</h3>');
		$(data.menuList).each(function(i){
			var strLi = $('<li><span class="open">'+ data.menuList[i].text +'</span></li>');
			var strDl = $('<dl></dl>');
			$(data.menuList[i].items).each(function(j){
				var strDd = '<dd><a id="'+ data.menuList[i].items[j].id +'" href="'+ data.menuList[i].items[j].href +'">'+ data.menuList[i].items[j].text +'</a>';	
				strDl.append(strDd);
			})
			strLi.append(strDl);
			ul.append(strLi);
		})
		wrap.prepend(h3);
		wrap.append(ul);
		$('.m-container').prepend(wrap);
		return this;
	},
	_getIframe: function(itemData){
		var $top_doc = top.document;
		var str = '';
			str += '<div id="'+ itemData.id +'Con" style="height:' + itemData.height + 'px;" class="m-tab-content"><iframe scrolling="auto" src="'+ itemData.url +'" frameborder="0" class="m-frameCon"></iframe></div>';
		$('#tabCon',$top_doc).append(str);
		return this;
	},
	_getTablist: function(itemData){
		var $top_doc = top.document;
		var str = '';
			str += '<li id="'+ itemData.id +'list"><span>'+ itemData.title +'</span><a href="javascript:" class="m-tab-close"></a></li>';
		$('#tabList',$top_doc).append(str);
		return this;
	},
	_getRighthand: function(){
		var that = this;
		if($('#mm',top.document).hasClass('m-menu')){
			return;
		}else{
			$('body').append(artTabs.rightHand);
			$('#tabList').delegate('li','contextmenu',function(e) {
				var listId = $(this).attr('id');
				var i = listId.indexOf('list');
				var tabId = listId.substring(0,i);
				that.cutShow(tabId);
				
				var getIndex_id = $('#tabList').find('li').eq(0).attr('id');
				if(getIndex_id == listId){
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
		}
		return that;
	},
	tabCloseEven: function(){
		var that = this;
		//刷新
		$('#mm-tabupdate').click(function() {
			var listId = $('#tabList').find('li').filter('.on').attr('id');
			var i = listId.indexOf('list');
			var tabId = listId.substring(0,i);	
			that.refreshIframe(tabId);
			$('#mm').hide();	
		})
		//关闭当前
		$('#mm-tabclose').click(function() {
			var listId = $('#tabList').find('li').filter('.on').attr('id');
			var i = listId.indexOf('list');
			var tabId = listId.substring(0,i);
			var j = jQuery.inArray(tabId, idArray);
			idArray.splice(j,1);
			that.prevShow(tabId);
			that.removeTab(tabId);
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
			var listId = $('#tabList').find('li').filter('.on').attr('id');
			var i = listId.indexOf('list');
			var tabId = listId.substring(0,i);
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
			idArray.push(tabId);
			$('#mm').hide();
		});
		return this;
	}
};

artTabs.fn._init.prototype = artTabs.fn;
$.fn.tabs = $.fn.artTabs = function () {
	var config = arguments;
	this[this.live ? 'live' : 'bind']('click', function () {
		artTabs.apply(this, config);
		return false;
	});
	return this;
};

artTabs.rightHand = 
'<div id="mm" class="m-menu">'
+	'<div class="menu-line"></div>'
+	'<div class="menu-item" id="mm-tabupdate">'
+		'<div class="menu-text">刷新</div>'
+	'</div>'
+	'<div class="menu-sep"></div>'
+	'<div class="menu-item" id="mm-tabclose">'
+		'<div class="menu-text">关闭</div>'
+	'</div>'
+	'<div class="menu-item" id="mm-tabcloseother">'
+		'<div class="menu-text">关闭其他</div>'
+	'</div>'
+	'<div class="menu-item" id="mm-tabcloseall">'
+		'<div class="menu-text">关闭全部</div>'
+	'</div>'
+'</div>';


/**
 * 默认配置
 * 说明：
 * 1.一个调用方法中不能同时出现addTab,toTab
 * 2.menu用于加载菜单数据,需要单独调用(后期扩展三级菜单或者更多)
 * 3.道理上来讲, closeTab跟isRefresh可以跟其他的同时一起调用
 */
artTabs.defaults = {								
	menu: null,			//导航数据
	addTab: null,		//新增tab页
	toTab: null,		//跳转到指定tab页
	closeTab: false,	//关闭当前tab页
	isRefresh: false
};
window.artTabs = $.tabs = $.artTabs = artTabs;
})(jQuery);
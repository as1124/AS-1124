/**
 * Idata集成工具类
 *
 */
var iDataUtil = window.iDataUtil ||(function(iDataUtil,undefined) {
	var _configData={};//配置信息
	var _isLoad=false;//标记是否已加载配置信息
//	var _isLogin=false;//是否已登录
	/**
	 * 初始化
	 */
	var _init = function() {
		_getConfig();
	};
	
	/**
	 * 加载配置文件
	 */
	var _getConfig=function(){
		var xmlHttp;
		if(window.ActiveXObject)  
    {  
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
    }  
    else if(window.XMLHttpRequest)  
    {  
        xmlHttp = new XMLHttpRequest();  
    } 
		try 
    {  
        xmlHttp.onreadystatechange = function ()  
				{     
				    if(xmlHttp.readyState == 4)  
				    {    
				        if (xmlHttp.status == 200 || xmlHttp.status == 0|| xmlHttp.status == 304)  
				        {  
				            var result = xmlHttp.responseText;  
				            var json = eval("(" + result + ")");  
				            _configData=json;
										_isLoad=true;
				        }else{
				        		_isLoad=false;
			    					alert('Idata 加载配置信息出错！');
				        }  
				    } 
				    
				    
				};  
        xmlHttp.open("GET", _iDataContext+"/common/idata/idata.json", false);  
        xmlHttp.send(null);  
    }  
    catch(e)  
    {  
        _isLoad=false;
		    alert('Idata 加载配置信息出错！'+e);
    } 
		
		/*jQuery.ajax( {    
		    url:_iDataContext+"/common/idata/idata.json",   
		    type:'post',  
		    async: false,
		    dataType:'json',
		    success:function(data, textStatus, jqXHR) { 
		    	if(data){
		    		_configData=data;
					_isLoad=true;
		    	}else{
		    		_isLoad=false;
			    	alert('Idata 加载配置信息出错！'+e);
		    	}
					
		    },    
		    error:function(e) {  
		    	_isLoad=false;
		    	alert('Idata 加载配置信息出错！'+e);
		    }    
		}); */
	};
	/**
	 * 登录
	 
	var _doLogin=function(){
		if(!_isLogin){
			debugger;
			//var url=_configData.url+_configData.modules.login+"?user="+_configData.user+"&password="+_configData.password;
			var url=_iDataContext+"/common/idata/idataLogin.jsp";
			jQuery("#IDataLogin").append("<iframe style='display:none;' src='"+url+"'></iframe>");
			_isLogin=true;
		}
	}; */
	
	var _appendUser=function(url){
		return url+"?user="+_configData.user+"&password="+_configData.password;
		//return url;
	};
	var _getViewParamsStr=function(viewParams){
		var ret="";
		if(viewParams&&viewParams.length){
			for(var i=0;i<viewParams.length;i++){
				var data=viewParams[i];
				ret=ret+"&"+data.name+"="+data.value;
			}
		}
		return ret;
	};
	/**
	 * 参数转换成JSON字符串
	 * @obj 待转换字符串
	 */
	var _toJSONString=function (obj) { 
		var m = {	'\b': '\\b',
							'\t': '\\t',
							'\n': '\\n',
							'\f': '\\f',
							'\r': '\\r',
							'"' : '\\"',
							'\\': '\\\\'	};
		var s = {
			array: function (x) {
				var a = ['['], b=false, f, i, l = x.length, v;
				for (i = 0; i < l; i += 1) {
					v = x[i];
					f = s[typeof v];
					if (f) {
						v = f(v);
						if (typeof v == 'string') {
							if (b) {
								a[a.length] = ',';
							}
							a[a.length] = v;
							b = true;
						}
					}
				}
				a[a.length] = ']';
				return a.join('');
			},
			'boolean': function (x) {
				return String(x);
			},
			'null': function (x) {
				return "null";
			},
			number: function (x) {
				return isFinite(x) ? String(x) : 'null';
			},
			object: function (x) {
				if (x) {
					if (x instanceof Array) {
						return s.array(x);
					}
					var a = ['{'], b=false, f, i, v;
					for (i in x) {
						v = x[i];
							f = s[typeof v];
							if (f) {
								v = f(v);
								if (typeof v == 'string') {
									if (b) {
										a[a.length] = ',';
									}
									a.push(s.string(i), ':', v);
									b = true;
								}
							}
						}
					a[a.length] = '}';
					return a.join('');
				}
				return 'null';
			},
			string: function (x) {
				if (/["\\\x00-\x1f]/.test(x)) {
					x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
							var c = m[b];
							if (c) {
								return c;
							}
							c = b.charCodeAt();
							return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
					});
				}
        		return '"' + x + '"';
			}
		};
		return s[typeof obj](obj);
	};
	
	if(!_isLoad){
		_init();
	}	

	
	return {
	  /*getConfig:function(){
		return _configData;  
	  },*/
	  toJSONString:function(obj){
		  return _toJSONString(obj);
	  },
		/**
		 * 打开首页
		 */
	  getIndexUrl: function() {
		  return encodeURI(_appendUser(_configData.url+_configData.modules.index));
	  },
	  	/**
		 * 打开资源
		 * @resourceId 资源ID
		 * @viewParams 显示参数 
		 * @queryParams 查询参数
		 */
	  getResourceUrl: function(resourceId,viewParams) {
		  var url=_appendUser(_configData.url+_configData.modules.resource);
		  url=url+(resourceId?"&resid="+resourceId:"");
		  url=url+_getViewParamsStr(viewParams);
		//  url=url+(queryParams?"&paramsInfo="+_toJSONString(queryParams):"");
		  return encodeURI(url);
	  },
		/**
		 * 打开目录
		 * @id 模块ID
		 * @viewParams 显示参数 
		 */
	  getModuleUrl: function(id,viewParams) {
		  var url=_appendUser(_configData.url+_configData.modules.module);
		  url=url+(id?"&id="+id:"");
		  url=url+_getViewParamsStr(viewParams);
		  return encodeURI(url);
	  },
	  	/**
		 * 打开portal
		 * @pageid 对应的显示的页面标 I2c949e121d27be5d011d2884d0ea0103
		 * @noBanner 是否隐藏页面头部 默认为不隐藏
		 * @showPath 是否显示当前位置 默认为true
		 * @hideTab 是否隐藏TAB 默认false
		 */
	  getPortalUrl: function(viewParams) {
		  var url=_appendUser(_configData.url+_configData.modules.portal);
		  url=url+_getViewParamsStr(viewParams);
		  return encodeURI(url);
	  }
     
	};
}) (window.iDataUtil || {});

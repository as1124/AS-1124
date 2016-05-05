<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%--
- Author(s): huang
- Date: 2014-08-13 12:03:26
- Description:
  --%>
  <head>
    <title>
      机构查询
    </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%= request.getContextPath() %>/common/nui/nui.js" type="text/javascript">
    </script>
  </head>
  <body style="width:98%;height:95%;">
    <div class="nui-panel" title="菜单列表" iconCls="icon-add" style="width:100%;height:85%;" showToolbar="false" showFooter="false" >
      <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
          <tr>
            <td style="width:100%;">
              <a class="nui-button" iconCls="icon-add" onclick="add()">
                增加
              </a>
              <a id="update" class="nui-button" iconCls="icon-edit" onclick="edit()">
                编辑
              </a>
              <a class="nui-button" iconCls="icon-remove" onclick="remove()">
                删除
              </a>
            </td>
          </tr>
        </table>
      </div>
      <div class="nui-fit">
        <div id="datagrid1" dataField="menus" class="nui-datagrid" style="width:100%;height:100%;" 
        url="com.primeton.demo.menuComponent.newbiz.biz.ext" pageSize="10" showPageInfo="true" 
        multiSelect="true" onselectionchanged="selectionChanged" allowSortColumn="false">
          <div property="columns">
            <div type="indexcolumn">
            </div>
            <div type="checkcolumn">
            </div>
            <div field="type" headerAlign="center" allowSort="true" visible="false">
              菜单类别
            </div>
            <div field="name" headerAlign="center" allowSort="true" >
              菜单名称
            </div>
            <div field="key" headerAlign="center" allowSort="true" >
              Key
            </div>
            <div field="url" headerAlign="center" allowSort="true" >
             url
            </div>
            <div field="media_id" headerAlign="center" allowSort="true" >
              多媒体ID
            </div>
            <div field="sub_button" headerAlign="center" allowSort="true" >
              子菜单
            </div>
          </div>
        </div>
      </div>
    </div>
<script type="text/javascript">
      nui.parse();
      //var grid = nui.get("datagrid1");
      //grid.load();
	var json1 = "{'name':'微服务','sub_button':[{'type':'click','name':'微绑定','key':'primeton_bind'},{'type':'click','name':'账户信息','key':'primeton_userinfo'},{'type':'view','name':'关于我们','url':'http://www.primeton.com'}]}";
	var json2 = "{'name':'菜单','sub_button':[{'type':'view','name':'搜索','url':'http://www.baidu.com'},{'type':'view','name':'视频','url':'http://v.qq.com'},{'type':'click','name':'赞一下','key':'support'}]}";
	var json3 = "{'name':'89&10','sub_button':[{'type':'location_select','name':'位置',key:'send_location'},{'type':'media_id','name':'素材','media_id':'_1Bkhbh4VeP4qhoiN3fILWfe9VZ0EB6oCzRxkMzYJfc'},{'type':'pic_photo_or_album','name':'发图','key':'send_photo'}{'type':'view_limited','name':'新闻','media_id':'_1Bkhbh4VeP4qhoiN3fILcyce975caGASLDiykfnDRw'}]}";
	function add(){
		nui.ajax({
              url:"com.primeton.demo.menuComponent.createMenu.biz.ext",
              type:'POST',
              data:{menus:[json1,json2,json3],__type:'com.primeton.mobile.wechat.model.menu.WechatMenu'},
              cache: false,
              contentType:'text/json',
              success:function(text){
              		var result = nui.decode(text);
              		alert(result);
              }
		
		});
	
	}
	
</script>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

	//用Ajax设置现在充氧
	function oxygenNow(){
		var oxygenNow = "oxygenNow";
		$.ajax({
			url:"${pageContext.request.contextPath }/SendTCPStr",
			data: {"oxygenNow": oxygenNow},
			type:"post",
			success: function(msg){
				if(msg == "success"){
					alert("现在开始充氧了...");
				}
			},
			error: function(){alert("设置失败了...");}
			
		});
	}
	//用Ajax设置现在投食
	function feedNow(){
		var feedNow = "feedNow";
		$.ajax({
			url:"${pageContext.request.contextPath }/SendTCPStr",
			data:{"feedNow" : feedNow},
			type:"post",
			success: function(msg){
				if(msg == "success"){
					alert("现在开始投食了...");
				}
			},
			error: function(){alert("设置失败了...");}
			
		});
	}
	//保存设置
	function saveTime(){
		//判断是否为空，如果为空，弹窗提示
		var oxygenTime = $("#oxygenTime");
		if(oxygenTime.val() == "0"){
			alert("充氧的间隔时间不能设置为空！");
			oxygenTime.focus();
			return;
		}
		var feedTime = $("#feedTime");
		if(feedTime.val() == "0"){
			alert("投食的间隔时间不能设置为空！");
			feedTime.focus();
			return;
		}
		//不为空就保存
		$("#ff").submit();
		return true;
	}
	
	$('#oxygenTime').combobox('select', '2');
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能鱼缸主页</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/easyui.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css" type="text/css"></link>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
</head>
<body style="background:url(images/background.jpg) repeat;">

	<form id="ff" action="${pageContext.request.contextPath }/SendTCPStr" method="post" style="padding-top: 20%;">  
	
	<div align="center" >
    <div >
    	<label for="oxygenSelect" style="">充氧的间隔时间:</label>   
        <select id="oxygen" class="easyui-combobox"  name="oxygenTime" style="width:120px;">   
        	<c:forEach items="${oxygenMap }" var="map" >
        		<option value="${map.key }" <c:if test=
        		'${map.key==userOxFe.oxygenTime }'>selected="selected"</c:if>>${map.value }</option>
        	</c:forEach>
		</select>    
		&nbsp;
		<a id="oxygenNow" href="javascript:oxygenNow()" class="easyui-linkbutton" 
		data-options="iconCls:'icon-ok'">现在充氧</a>  
    </div>   
    <br>
    <div >   
        <label for="feedSelect" >投食的间隔时间:</label>   
        <select id="feed" class="easyui-combobox" name="feedTime" style="width:120px;">   
		    <c:forEach items="${feedMap }" var="map">
        		<option value="${map.key }" <c:if test=
        		'${map.key==userOxFe.feedTime }'>selected="selected"</c:if>>${map.value }</option>
        	</c:forEach>   
		</select>
		&nbsp;
		<a id="feedNow" href="javascript:feedNow()" class="easyui-linkbutton" 
		data-options="iconCls:'icon-ok'">现在投食</a> 
    </div>  
    <br>
    <div >
    	<a id="saveTime" href="javascript:saveTime()" class="easyui-linkbutton" 
    	data-options="iconCls:'icon-save'">保存设置</a>
    </div>
    </div> 
	</form>  



</body>
</html>
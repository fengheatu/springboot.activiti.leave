<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>请假已结束的流程实例列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>

    <script src="/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="/js/module/activiti/workflow.js" type="text/javascript"></script>
</head>

<body>
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<th>假种</th>
				<th>申请人</th>
				<th>请假天数</th>
				<th>请假原因</th>
				<th>开始时间</th>
				<th>流程启动时间</th>
				<th>流程结束时间</th>
				<th>流程结束原因</th>
				<th>流程版本</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result }" var="object">
				<c:set var="hpi" value="${object[1] }" />
				<c:set var="leave" value="${object[0] }" />
				<tr id="${leave.id }">
					<td>${leave.type }</td>
					<td>${leave.userId }</td>
					<td>${leave.days }</td>
					<td>${leave.reason }</td>
					<td><fmt:formatDate value="${leave.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td><fmt:formatDate value="${hpi.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td><fmt:formatDate value="${hpi.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${hpi.deleteReason }</td>
					<td><b title='流程版本号'>V: ${hpi.processDefinitionVersion }</b></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>

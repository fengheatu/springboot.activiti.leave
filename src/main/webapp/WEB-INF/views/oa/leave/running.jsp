<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>请假正在运行中的流程实例列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function() {
		// 跟踪
	    $('.trace').click(graphTrace);
	});
	</script>
</head>

<body>
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<th>假种</th>
				<th>申请人</th>
				<th>开始时间</th>
				<th>请假原因</th>
				<th>当前节点</th>
				<th>任务创建时间</th>
				<th>流程状态</th>
				<th>当前处理人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result }" var="object">
				<c:set var="leave" value="${object[0] }" />
				<c:set var="task" value="${object[1] }" />
				<c:set var="pi" value="${object[2] }" />
				<tr id="${leave.id }" tid="${task.id }">
					<td>${leave.type }</td>
					<td>${leave.userId }</td>
					<td><fmt:formatDate value="${leave.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${leave.reason}</td>
					<td>
						<a href="/leave/process/trace/auto/${pi.id }" target="_blank" title="点击查看流程图">${task.name }</a>
						<a href="/leave/process/trace/auto/${pi.id }/${pi.processDefinitionId}" target="_blank" title="点击查看流程图">diagram-viewer</a>
					</td>
					<td><fmt:formatDate value="${task.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${pi.revision }</b></td>
					<td>${task.assignee }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>

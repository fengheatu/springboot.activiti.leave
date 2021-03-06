<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>请假待办任务列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>
    <style type="text/css">
    /* block ui */
	.blockOverlay {
		z-index: 1004 !important;
	}
	.blockMsg {
		z-index: 1005 !important;
	}
    </style>

    <script src="/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script src="/js/module/oa/leave/leave-todo.js" type="text/javascript"></script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
	</c:if>
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
					<a class="trace" href='#' pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
				</td>
				<td><fmt:formatDate value="${task.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${pi.revision }</b></td>
					<td>
						<c:if test="${empty task.assignee }">
							<a class="claim" href="/leave/task/claim/${task.id}" target="mainIframe">签收</a>
						</c:if>
						<c:if test="${not empty task.assignee }">
							<%-- 此处用tkey记录当前节点的名称 --%>
							<a class="handle" tkey='leaveDetails' tname='${task.name }' href="#">办理</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 下面是每个节点的模板，用来定义每个节点显示的内容 -->
	<!-- 使用DIV包裹，每个DIV的ID以节点名称命名，如果不同的流程版本需要使用同一个可以自己扩展（例如：在DIV添加属性，标记支持的版本） -->

	<!-- 部门领导审批 -->
	<div id="leaveDetails" style="display: none">

		<!-- table用来显示信息，方便办理任务 -->
		<%@include file="view-form.jsp" %>
	</div>

</body>
</html>

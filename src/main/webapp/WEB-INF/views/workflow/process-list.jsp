<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<script>
		var notLogon = ${empty user};
		if (notLogon) {
			location.href = '${ctx}/login?error=nologon';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-base-styles.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
	<%@ include file="/common/include-custom-styles.jsp" %>
	<title>流程列表</title>

	<script src="/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#redeploy').button({
    		icons: {
    			primary: 'ui-icon-refresh'
    		}
    	});
    	$('#deploy').button({
    		icons: {
    			primary: 'ui-icon-document'
    		}
    	}).click(function() {
    		$('#deployFieldset').toggle('normal');
    	});
    });
    </script>
</head>
<body>
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<th>ProcessDefinitionId</th>
				<th>DeploymentId</th>
				<th>名称</th>
				<th>KEY</th>
				<th>版本号</th>
				<th>XML</th>
				<th>图片</th>
				<th>部署时间</th>
				<th>是否挂起</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="object">
				<c:set var="process" value="${object[0] }" />
				<c:set var="deployment" value="${object[1] }" />
				<tr>
					<td>${process.id }</td>
					<td>${process.deploymentId }</td>
					<td>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td><a target="_blank" href='/process/resource/read?processDefinitionId=${process.id}&resourceType=xml'>${process.resourceName }</a></td>
					<td><a target="_blank" href='/process/resource/read?processDefinitionId=${process.id}&resourceType=image'>${process.diagramResourceName }</a></td>
					<td><fmt:formatDate value="${deployment.deploymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${process.suspended} |
						<c:if test="${process.suspended }">
							<a href="/process/update/active/${process.id}">激活</a>
						</c:if>
						<c:if test="${!process.suspended }">
							<a href="/process/update/suspend/${process.id}">挂起</a>
						</c:if>
					</td>
					<td>
                        <a href='/process/delete/${process.deploymentId}'>删除</a>
                    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
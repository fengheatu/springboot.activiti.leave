<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>登录</title>
	<script>
		var logon = ${not empty user};
		if (logon) {
			location.href = '/main/index';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <style type="text/css">
        .login-center {
            width: 600px;
            margin-left:auto;
            margin-right:auto;
        }
        #loginContainer {
            margin-top: 3em;
        }
        .login-input {
            padding: 4px 6px;
            font-size: 14px;
            vertical-align: middle;
        }
    </style>

    <script src="/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/js/common/plugins/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	$(function() {
		$('button').button({
			icons: {
				primary: 'ui-icon-key'
			}
		});
	});
	</script>
</head>

<body>
    <div id="loginContainer" class="login-center">
		<div style="text-align: center;">
            <h2>工作流引擎Activiti演示项目</h2>
		<hr />
		<form action="/user/login" method="post">
			<table>
				<tr>
					<td width="200" style="text-align: right;">用户名：</td>
					<%--<td><input id="username" name="username" class="login-input" placeholder="用户名（见下左表）" /></td>--%>
                    <td height="24" colspan="2" valign="bottom">
                        <select name="mobile" class="login-input" >
                            <option value="13122198121">普通员工</option>
                            <option value="13122198122">部门经理1</option>
                            <option value="13122198123">部门经理2</option>
                            <option value="13122198124">总经理1</option>
                            <option value="13122198125">总经理2</option>
                        </select><br/>
                    </td>
				</tr>
				<tr>
					<td style="text-align: right;">密码：</td>
					<td><input id="password" name="password" type="password" class="login-input" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<button type="submit">登录Demo</button>
					</td>
				</tr>
			</table>
		</form>
    </div>
</body>
</html>

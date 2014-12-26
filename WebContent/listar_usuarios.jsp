<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="erro.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Usuários</title>
</head>
<body>
	<h1>Lista de Usuários</h1>
	<hr />
	<table width="100%" border="1">
		<tr bgcolor="#c0c0c0">
			<td>Id</td>
			<td>Login</td>
			<td>Senha</td>
		</tr>
	<%
			Class.forName("com.mysql.jdbc.Driver");
			String sql = "select * from usuarios";
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
			
			Statement stm = conexao.createStatement();
			
			ResultSet rs = stm.executeQuery(sql);
			String cor = "#FFF68F";
			int count = 0;
			while(rs.next()){
	%>
		<tr bgcolor="<%=cor %>">
			<td><%=rs.getInt("id") %></td>
			<td><%=rs.getString("login") %></td>
			<td><%=rs.getString("senha") %></td>
		</tr>
	<%
				if(count == 0){
					cor = "#CDBE70";
					count++;
				}else if(count == 1){
					cor = "#FFF68F";
					count = 0;
				}
			}
	%>
	</table>
	<br />
	<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />
</body>
</html>
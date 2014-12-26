<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.io.IOException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="erro.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cadastro de Usuários</title>
	</head>
	<body>
		<%
			if(request.getParameter("txtLogin") != null){
				//out.println("Ok!");

					String login = request.getParameter("txtLogin");
					String senha = request.getParameter("txtSenha");

							Class.forName("com.mysql.jdbc.Driver");
							out.println("Conectado com sucesso!");
							String sql = "insert into usuarios(login, senha) values (?,?)";
							Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
							
							PreparedStatement stmt = conexao.prepareStatement(sql);
							
							stmt.setString(1, login);
							stmt.setString(2, senha);
							
							stmt.execute();
							stmt.close();
							conexao.close();
							
							response.sendRedirect("http://localhost:8080/ChamadosAsf/ListarChamados");
			}

		%>
		<form method="POST" action="cadastro_usuario.jsp">
			Login:<input type="text" name="txtLogin" /><br />
			Senha:<input type="text" name="txtSenha" /><br />
			<input type="submit" value="Cadastrar" />
		</form>
	</body>
</html>


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListarChamadosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		HttpSession sessao = request.getSession();
		
		if(sessao.getAttribute("login") == null){
			response.sendRedirect("http://localhost:8080/ChamadosAsf/Login");
		}
		
		out.println(sessao.getAttribute("info"));
		out.println("<a href='http://localhost:8080/ChamadosAsf/Login?msg=logoff'>Sair</a>");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String sql = "select * from chama";
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
			
			if(request.getParameter("id") != null){
				int id = Integer.parseInt(request.getParameter("id"));
				String sqlDelete = "delete from chama where id = ?";
				PreparedStatement stmt = conexao.prepareStatement(sqlDelete);
				stmt.setInt(1, id);

				stmt.execute();
				stmt.close();
			}
			
			Statement stm = conexao.createStatement();
			
			ResultSet rs = stm.executeQuery(sql);
			out.println("<h1>Lista de Chamados");
			out.println("<hr />");
			out.println("<table width='100%' border='1'>");
			out.println("<tr bgcolor='#00CD00'>");
			out.println("<td>ID</td>");
			out.println("<td>Titulo</td>");
			out.println("<td>Data</td>");
			out.println("<td>Editar</td>");
			out.println("<td>Apagar</td>");
			out.println("</tr>");
			String cor = "#FFF68F";
			int count = 0;
			while(rs.next()){
				out.println("<tr bgcolor='"+cor+"'>");
				out.println("<td>"+rs.getInt("id")+"</td>");
				out.println("<td>"+rs.getString("titulo")+"</td>");
				out.println("<td>"+rs.getString("data")+"</td>");
				out.println("<td><a href='http://localhost:8080/ChamadosAsf/EditarChamado?id="+rs.getInt("id")+"'>[EDITAR]</a></td>");
				out.println("<td><a href='http://localhost:8080/ChamadosAsf/ListarChamados?id="+rs.getInt("id")+"'>[APAGAR]</a></td>");
				out.println("</tr>");
				if(count == 0){
					cor = "#CDBE70";
					count++;
				}else if(count == 1){
					cor = "#FFF68F";
					count = 0;
				}
			}
			out.println("</table>");
			out.println("<br />");
			out.println("<br />");
			out.println("<br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/NovoChamado'>Novo Chamado</a><br />");
			rs.close();
			stm.close();
			conexao.close();
			
		}catch(SQLException e){
			out.println("Erro \n"+e.getMessage());
		}catch (ClassNotFoundException e) {
			out.println("Erro \n"+e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	}
}

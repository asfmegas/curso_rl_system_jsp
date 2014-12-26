

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 * Servlet implementation class EditarChamadoServlet
 */
public class EditarChamadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String sql = "select * from chama where id = ?";
				Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
				
				PreparedStatement stmt = conexao.prepareStatement(sql);
				stmt.setInt(1, Integer.parseInt(request.getParameter("id")));
				
				
				ResultSet rs = stmt.executeQuery();
				
				PrintWriter out = response.getWriter();
				
				if(rs.next()){
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Editar Chamado</title>");
					out.println("</head>");
					out.println("<body>"); 	
					out.println("<h1>Preencha as informações do chamado</h1>");
					out.println("<hr />");
					out.println("<form method='POST'>");
					out.println("ID......:<input type='text' name='txtId' readonly='readonly' value='"+rs.getInt("id")+"' /><br />");
					out.println("Titulo..:<input type='text' name='txtTitulo' value='"+rs.getString("titulo")+"' /><br />");
					out.println("Conteúdo:<br /><textarea rows='10' cols='40' name='txtConteudo'>"+rs.getString("conteudo")+"</textarea><br />");
					out.println("<input type='submit' value='Atualizar chamado' /><br /><br />");
					out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />");
					out.println("<a href='/Logoff'>Sair</a>");
					out.println("</form>");
					out.println("</body>");
					out.println("</html>");
				}else{
					out.println("Esse chamado não existe");
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			int id = Integer.parseInt(request.getParameter("txtId"));
			String titulo = request.getParameter("txtTitulo");
			String conteudo = request.getParameter("txtConteudo");
			
			if(titulo.trim().length() < 4){
				out.println("Preencha o campo titulo");
			}else if(conteudo.trim().length() < 4){
				out.println("Preencha o campo conteudo");
			}else {
				try{

					Class.forName("com.mysql.jdbc.Driver");
					out.println("Conectado com sucesso!");
					String sql = "update chama set titulo=?, conteudo=? where id=?";
					Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
					
					PreparedStatement stmt = conexao.prepareStatement(sql);
					
					stmt.setString(1, titulo.toUpperCase());
					stmt.setString(2, conteudo.toUpperCase());
					stmt.setInt(3, id);
					
					stmt.execute();
					stmt.close();
					conexao.close();
					
					response.sendRedirect("http://localhost:8080/ChamadosAsf/ListarChamados");
					
					
				}catch(SQLException e){
					out.println("Erro \n"+e.getMessage());
				}catch (ClassNotFoundException e) {
					out.println("Erro \n"+e.getMessage());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "Erro\n"+e.getMessage());
		}
	}

}



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class NovoChamadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession sessao = request.getSession();
			
			if(sessao.getAttribute("login") == null){
				response.sendRedirect("http://localhost:8080/ChamadosAsf/Login");
			}
			
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr />");
			out.println(sessao.getAttribute("info"));
			out.println("<a href='http://localhost:8080/ChamadosAsf/Login?msg=logoff'>Sair</a>");
			out.println("<form method='POST'>");
			out.println("Titulo:<br /><input type='text' name='txtTitulo' /><br />");
			out.println("Conteúdo:<br /><textarea rows='10' cols='40' name='txtConteudo'></textarea><br />");
			out.println("<input type='submit' value='Abrir chamado' /><br /><br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />");
			out.println("<a href='/Logoff'>Sair</a>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			HttpSession sessao = request.getSession();
			
			if(sessao.getAttribute("login") == null){
				response.sendRedirect("http://localhost:8080/ChamadosAsf/Login");
			}
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr />");
			try {
				String titulo = request.getParameter("txtTitulo");
				String conteudo = request.getParameter("txtConteudo");
				
				if(titulo.trim().length() < 4){
					out.println("<h3><font color='#CD0000'>Preencha o campo titulo</h3></font>");
				}else if(conteudo.trim().length() < 4){
					out.println("<h3><font color='#CD0000'>Preencha o campo conteúdo</h3></font>");
				}else {
					try{

						Class.forName("com.mysql.jdbc.Driver");
						out.println("Conectado com sucesso!");
						String sql = "insert into chama(titulo, conteudo, data) values (?,?,?)";
						Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
						
						PreparedStatement stmt = conexao.prepareStatement(sql);
						
						Date hoje = new Date(new java.util.Date().getTime());
						//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						stmt.setString(1, titulo.toUpperCase());
						stmt.setString(2, conteudo.toUpperCase());
						stmt.setDate(3, hoje);
						
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
			out.println("<form method='POST'>");
			out.println("Titulo:<br /><input type='text' name='txtTitulo' /><br />");
			out.println("Conteúdo:<br /><textarea rows='10' cols='40' name='txtConteudo'></textarea><br />");
			out.println("<input type='submit' value='Abrir chamado' /><br /><br />");
			out.println("</form>");
			out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />");
			out.println("<a href='/Logoff'>Sair</a>");
			out.println("</body>");
			out.println("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

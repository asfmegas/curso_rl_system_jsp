

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			
			String login_name = "";
			
			Cookie[] ck = request.getCookies();
			
			if(ck != null){
				for(Cookie cookie : ck){
					if(cookie.getName().equals("login_name")){
						login_name = cookie.getValue();
					}
				}
			}
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Login</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha seu login e senha</h1>");
			out.println("<hr />");
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("logoff")){
					HttpSession sessao = request.getSession();
					sessao.invalidate();
					out.println("<span style='color: red'>Deslogado com sucesso!</span><br /><br />");
				}
			}
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("error")){
					out.println("<span style='color: red'>Login e/ou senhas incorretos!</span><br /><br />");
				}
			}
			out.println("<form method='POST'>");
			out.println("Login:<br /><input type='text' name='txtLogin' value='"+login_name+"'/><br />");
			out.println("Senha:<br /><input type='password' name='txtSenha' /><br />");
			out.println("<input type='submit' value='Entrar' /><br /><br />");
			out.println("</form>");
			//out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/cadastro_usuario.jsp'>Novo Cadastro</a>");
			out.println("</body>");
			out.println("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Login</title>");
			out.println("</head>");
			out.println("<body>"); 
			out.println("<h1>Preencha seu login e senha</h1>");
			out.println("<hr />");
			
			try {
				String login = request.getParameter("txtLogin");
				String senha = request.getParameter("txtSenha");
				
				Cookie ck = new Cookie("login_name", login);
				ck.setMaxAge(60*60*24*30);
				response.addCookie(ck);
				
					try{
						Class.forName("com.mysql.jdbc.Driver");
						out.println("Conectado com sucesso!");
						String sql = "select * from usuarios where login=? and senha=?";
						Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/chamados","root","asfmegas18");
						
						PreparedStatement stmt = conexao.prepareStatement(sql);
						
						stmt.setString(1, login);
						stmt.setString(2, senha);
						
						stmt.execute();
						
						ResultSet rs = stmt.executeQuery();
						
						if(rs.next()){
							HttpSession sessao = request.getSession();
							sessao.setAttribute("login", login);
							sessao.setAttribute("info", request.getRemoteAddr());
							
							response.sendRedirect("http://localhost:8080/ChamadosAsf/ListarChamados");
						}else{
							response.sendRedirect("http://localhost:8080/ChamadosAsf/Login?msg=error");
						}
						
						stmt.close();
						rs.close();
						conexao.close();
					}catch(SQLException e){
						out.println("Erro \n"+e.getMessage());
					}catch (ClassNotFoundException e) {
						out.println("Erro \n"+e.getMessage());
					}
				
			} catch (IOException e) {
				e.printStackTrace();
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "Erro\n"+e.getMessage());
			}
			out.println("<form method='POST'>");
			out.println("Login:<br /><input type='text' name='txtLogin' /><br />");
			out.println("Senha:<br /><input type='password' name='txtSenha' /><br />");
			out.println("<input type='submit' value='Abrir chamado' /><br /><br />");
			out.println("</form>");
			//out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Entrar</a><br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/cadastro_usuario.jsp'>Novo Cadastro</a>");
			out.println("<a href='#'>Sair</a>");
			out.println("</body>");
			out.println("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

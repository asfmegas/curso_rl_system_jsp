import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Sistema de Chamados</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("</body>");
			out.println("<h1>Sistema de chamados</h1>");
			out.println("<hr />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/NovoChamado'>Novo chamado</a><br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/ListarChamados'>Listar chamados</a><br />");
			out.println("<a href='http://localhost:8080/ChamadosAsf/Logoff'>Sair</a>");
			out.println("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest reuqest, HttpServletResponse response){
		
	}
}

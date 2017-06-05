package br.com.petshow.rest.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.petshow.rest.SuperRestClass;
import br.com.petshow.util.WriteConsoleUtil;

/**
 * Servlet implementation class WebRestApplicationContext
 */
@WebServlet(value="/webRestApplicationContext", loadOnStartup=3)
public class WebRestApplicationContext extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		WriteConsoleUtil.write("Iniciando o Servlet para carregar o SpringContext");
		super.init(config);
		SuperRestClass.setContext(WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()));
		WriteConsoleUtil.write("**** OK carregado !!!! ****");
	}
	
	
}

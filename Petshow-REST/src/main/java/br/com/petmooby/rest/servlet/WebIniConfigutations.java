package br.com.petmooby.rest.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.petmooby.constants.WebServletStartsConstants;
import br.com.petmooby.enums.EnumUF;
import br.com.petmooby.role.AcessoRole;
import br.com.petmooby.role.CidadeRole;
import br.com.petmooby.util.WriteConsoleUtil;

@WebServlet(value="/webIniConfigutations", loadOnStartup=WebServletStartsConstants.START_INI_CONFIGURATIONS)
public class WebIniConfigutations extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6522392989960698269L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		WriteConsoleUtil.write("Iniciando o Servlet para verificar as configurações iniciais básicas do Servidor");
		super.init(config);
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		insertAuthorities(context);
		insertCidadeCE(context);
		insertCidadePE(context);
	}

	private void insertAuthorities(ApplicationContext context) {
		AcessoRole acesso = context.getBean(AcessoRole.class);
		acesso.load();
		WriteConsoleUtil.write("Acessos carregados...........OK");
	}
	
	private void insertCidadeCE(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.CE).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Ceará ............... INI");
			cidadeRole.inserirCidadesCEJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Ceará ............... OK");
		}
	}
	
	private void insertCidadePE(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.PE).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Pernambuco ............... INI");
			cidadeRole.inserirCidadesPEJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Pernambuco ............... OK");
		}
	}
	
}

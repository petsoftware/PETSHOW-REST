package br.com.petshow.rest.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.petshow.constants.WebServletStartsConstants;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.role.AcessoRole;
import br.com.petshow.role.CidadeRole;
import br.com.petshow.util.WriteConsoleUtil;

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
		insertCidadeSP(context); 
		insertCidadeMA(context);
		insertCidadePI(context);
		insertCidadePB(context);
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
	
	private void insertCidadeSP(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.SP).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de São Paulo ............... INI");
			cidadeRole.inserirCidadesSPJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de São Paulo ............... OK");
		}
	}
	
	private void insertCidadeMA(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.MA).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Maranhão ............... INI");
			cidadeRole.inserirCidadesMAJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Maranhão ............... OK");
		}
	}
	
	private void insertCidadePI(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.PI).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Piaui ............... INI");
			cidadeRole.inserirCidadesPIJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Piaui ............... OK");
		}
	}
	
	private void insertCidadePB(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.PB).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Paraiba ............... INI");
			cidadeRole.inserirCidadesPBJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Paraiba ............... OK");
		}
	}
	
}

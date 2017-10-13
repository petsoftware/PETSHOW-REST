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
		insertCidadeAC(context);
		insertCidadeAL(context);
		insertCidadeAM(context);
		insertCidadeBA(context);
		insertCidadeDF(context);
		insertCidadeES(context);
		insertCidadeMG(context);
		insertCidadeMS(context);
		insertCidadeMT(context);
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
	
	private void insertCidadeAC(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.AC).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Acre ............... INI");
			cidadeRole.inserirCidadesACJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Acre ............... OK");
		}
	}
	
	private void insertCidadeAL(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.AL).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Alagoas ............... INI");
			cidadeRole.inserirCidadesALJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Alagoas ............... OK");
		}
	}
	
	private void insertCidadeAM(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.AM).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Amazonas ............... INI");
			cidadeRole.inserirCidadesAMJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Amazonas ............... OK");
		}
	}
	
	private void insertCidadeBA(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.BA).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Bahia ............... INI");
			cidadeRole.inserirCidadesBAJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Bahia ............... OK");
		}
	}
	
	private void insertCidadeDF(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.DF).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Distrito Federal ............... INI");
			cidadeRole.inserirCidadesDFJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Distrito Federal ............... OK");
		}
	}
	
	private void insertCidadeES(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.ES).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Espirito Santo ............... INI");
			cidadeRole.inserirCidadesESJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Espirito Santo ............... OK");
		}
	}
	
	private void insertCidadeMG(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.MG).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Minas Gerais ............... INI");
			cidadeRole.inserirCidadesMGJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades de Minas Gerais ............... OK");
		}
	}
	
	private void insertCidadeMS(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.MS).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso do Sul............... INI");
			cidadeRole.inserirCidadesMSJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso do Sul............... OK");
		}
	}
	
	private void insertCidadeMT(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.MT).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso ............... INI");
			cidadeRole.inserirCidadesMTJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso ............... OK");
		}
	}

}

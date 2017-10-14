package br.com.petshow.rest.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.petshow.constants.WebServletStartsConstants;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.role.AcessoRole;
import br.com.petshow.role.CidadeRole;
import br.com.petshow.role.RacasRole;
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
		inserirCidadesDoEstadoBrasileiro(context);
		inserirRacasNoBanco(context);
	}

	private void inserirCidadesDoEstadoBrasileiro(ApplicationContext context) {
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
		insertCidadePA(context);
		insertCidadePR(context);
		insertCidadeRJ(context);
		insertCidadeRN(context);
		insertCidadeRS(context);
		insertCidadeRO(context);
		insertCidadeRR(context);
		insertCidadeSC(context);
		insertCidadeSE(context);
		insertCidadeTO(context);
		insertCidadeAP(context);
	}
	
	private void inserirRacasNoBanco(ApplicationContext context) {
		RacasRole racasRole = context.getBean(RacasRole.class);
		try{
			try {
				if(racasRole.consultaRacas(EnumTipoAnimal.CACHORRO).isEmpty()){
					System.out.println("Inserirndo racas de cachorro");
					racasRole.inserirRacasDeCachorro();
					System.out.println("Inserirndo racas de cachorro - FIM");
				}
				if(racasRole.consultaRacas(EnumTipoAnimal.GATO).isEmpty()){
					System.out.println("Inserirndo racas de gato");
					racasRole.inserirRacasDeGato();;
					System.out.println("Inserirndo racas de gato - FIM");
				}
				if(racasRole.consultaRacas(EnumTipoAnimal.CAVALO).isEmpty()){
					System.out.println("Inserirndo racas de cavalo");
					racasRole.inserirRacasDeCavalo();
					System.out.println("Inserirndo racas de cavalo - FIM");
				}
				if(racasRole.consultaRacas(EnumTipoAnimal.PASSARO).isEmpty()){
					System.out.println("Inserirndo racas de passaro");
					racasRole.inserirRacasDePassaro();
					System.out.println("Inserirndo racas de pasaro - FIM");
				}
			} catch (ExceptionValidation e) {}
		}finally{
			racasRole = null;
		}
	}

	private void insertAuthorities(ApplicationContext context) {
		AcessoRole acesso = context.getBean(AcessoRole.class);
		acesso.load();
		WriteConsoleUtil.write("Acessos carregados...........OK");
	}

	private void insertCidadeCE(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.CE).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Ceará ............... INI");
				cidadeRole.inserirCidadesCEJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Ceará ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadePE(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.PE).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Pernambuco ............... INI");
				cidadeRole.inserirCidadesPEJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Pernambuco ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeSP(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.SP).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de São Paulo ............... INI");
				cidadeRole.inserirCidadesSPJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de São Paulo ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeMA(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.MA).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Maranhão ............... INI");
				cidadeRole.inserirCidadesMAJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Maranhão ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadePI(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.PI).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Piaui ............... INI");
				cidadeRole.inserirCidadesPIJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Piaui ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadePB(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.PB).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Paraiba ............... INI");
				cidadeRole.inserirCidadesPBJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Paraiba ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeAC(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.AC).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Acre ............... INI");
				cidadeRole.inserirCidadesACJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Acre ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeAL(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.AL).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Alagoas ............... INI");
				cidadeRole.inserirCidadesALJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Alagoas ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeAM(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.AM).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Amazonas ............... INI");
				cidadeRole.inserirCidadesAMJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Amazonas ............... OK");
			}
		}finally{
			cidadeRole = null;
		}

	}

	private void insertCidadeBA(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.BA).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Bahia ............... INI");
				cidadeRole.inserirCidadesBAJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Bahia ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeDF(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.DF).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Distrito Federal ............... INI");
				cidadeRole.inserirCidadesDFJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Distrito Federal ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeES(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.ES).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Espirito Santo ............... INI");
				cidadeRole.inserirCidadesESJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Espirito Santo ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeMG(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.MG).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Minas Gerais ............... INI");
				cidadeRole.inserirCidadesMGJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Minas Gerais ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeMS(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.MS).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso do Sul............... INI");
				cidadeRole.inserirCidadesMSJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Mato Grosso do Sul............... OK");
			}
		}finally{
			cidadeRole = null;
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

	private void insertCidadePA(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		if(cidadeRole.findAllByUF(EnumUF.PA).size() == 0){
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Para ............... INI");
			cidadeRole.inserirCidadesPAJob();
			WriteConsoleUtil.write("Iniciar carregamento das cidades do Para ............... OK");
		}
	}

	private void insertCidadePR(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.PR).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Parana ............... INI");
				cidadeRole.inserirCidadesPRJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Parana ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeRJ(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.RJ).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio de Janeiro ............... INI");
				cidadeRole.inserirCidadesRJJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio de Janeiro ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeRN(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.RN).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio Grande do Norte ............... INI");
				cidadeRole.inserirCidadesRNJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio Grande do Norte ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeRS(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.RS).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio Grande do Sul ............... INI");
				cidadeRole.inserirCidadesRSJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Rio Grande do Sul ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeRO(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.RO).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Rondonia ............... INI");
				cidadeRole.inserirCidadesROJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Rondonia ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeRR(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.RR).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Roraima ............... INI");
				cidadeRole.inserirCidadesRRJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Roraima ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeSC(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.SC).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Santa Catarina ............... INI");
				cidadeRole.inserirCidadesSCJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Santa Catarina ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeSE(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.SE).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Sergipe ............... INI");
				cidadeRole.inserirCidadesSEJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Sergipe ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

	private void insertCidadeTO(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.TO).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Tocantins ............... INI");
				cidadeRole.inserirCidadesTOJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades de Tocantins ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}
	
	private void insertCidadeAP(ApplicationContext context) {
		CidadeRole cidadeRole = context.getBean(CidadeRole.class);
		try{
			if(cidadeRole.findAllByUF(EnumUF.AP).size() == 0){
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Amapa ............... INI");
				cidadeRole.inserirCidadesAPJob();
				WriteConsoleUtil.write("Iniciar carregamento das cidades do Amapa ............... OK");
			}
		}finally{
			cidadeRole = null;
		}
	}

}

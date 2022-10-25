package it.prova.gestioneproprietari.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.dao.EntityManagerUtil;
import it.prova.gestioneproprietari.model.Automobile;
import it.prova.gestioneproprietari.model.Proprietario;
import it.prova.gestioneproprietari.service.MyServiceFactory;
import it.prova.gestioneproprietari.service.automobile.AutomobileService;
import it.prova.gestioneproprietari.service.proprietario.ProprietarioService;



public class TestProprietario_Automobile {

	public static void main(String[] args) {
	    ProprietarioService proprietarioService = MyServiceFactory.getProprietarioServiceInstance();
		AutomobileService automobileService = MyServiceFactory.getAutomobileServiceInstance();
		
		try {

			
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testInserisciProprietario(proprietarioService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testInserisciAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testRimozioneAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testContaQuantiProprietariConAutoImmatricolataDopo( proprietarioService, automobileService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");
			
			testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer(proprietarioService,automobileService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");
			testcercaTutteLeAutomobiliConErrori(proprietarioService,automobileService);
			System.out.println(
					"In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");
		} catch (Throwable e) {
		    e.printStackTrace();
	    } finally {
		
		        EntityManagerUtil.shutdown();
	    }
		
	}
	
	
	private static void testInserisciProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testInserisciProprietario inizio.............");
		
		Proprietario nuovoProprietario = new Proprietario("filippo", "bianchi", "flppbc423hsu",new SimpleDateFormat("dd-MM-yy").parse("01-01-2022"));
		if (nuovoProprietario.getId() != null)
			throw new RuntimeException("testInserisciProprietario fallito: record già presente ");

		
		proprietarioService.inserisciNuovo(nuovoProprietario);
		if (nuovoProprietario.getId() == null)
			throw new RuntimeException("testInserisciProprietario fallito ");

		System.out.println(".......testInserisciProprietario fine: PASSED.............");
	}
	
	private static void testInserisciAutomobile(ProprietarioService proprietarioService, AutomobileService automobileService)
			throws Exception {
		System.out.println(".......testInserisciAutomobile inizio.............");

		
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testInserisciAutomobile fallito: non ci sono proprietari a cui collegarci ");

		Automobile nuovaAutomobile = new Automobile("dodge", "viper", "ad6387",2006);
		
		nuovaAutomobile.setProprietario(listaProprietariPresenti.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);

		if (nuovaAutomobile.getId() == null)
			throw new RuntimeException("testInserisciAutomobile fallito ");

		if (nuovaAutomobile.getProprietario() == null)
			throw new RuntimeException("testInserisciAutomobile fallito: non ha collegato il proprietario ");

		System.out.println(".......testInserisciAutomobile fine: PASSED.............");
		
	}
	
	private static void testRimozioneAutomobile(ProprietarioService proprietarioService, AutomobileService automobileService)
			throws Exception {
		System.out.println(".......testRimozioneAutomobile inizio.............");


		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testRimozioneAutomobile fallito: non ci sono proprietari a cui collegarci ");

		Automobile nuovaAutomobile = new Automobile("dodge", "viper", "ad6387",2006);
	
		nuovaAutomobile.setProprietario(listaProprietariPresenti.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);

		Long idAutomobileInserita = nuovaAutomobile.getId();
		automobileService.rimuovi(idAutomobileInserita);
		
		if (automobileService.caricaSingolaAutomobile(idAutomobileInserita) != null)
			throw new RuntimeException("testRimozioneAutomobile fallito: record non cancellato ");
		System.out.println(".......testRimozioneAutomobile fine: PASSED.............");
	}
	
	private static void testContaQuantiProprietariConAutoImmatricolataDopo(ProprietarioService proprietarioService, AutomobileService automobileService) throws Exception{
		System.out.println(".......testContaQuantiProprietariConAutoImmatricolataDopo inizio.............");
		
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testContaQuantiProprietariConAutoImmatricolataDopo fallito: non ci sono proprietari a cui collegarci ");

		Automobile nuovaAutomobile1= new Automobile("dodge", "viper", "ad6387",2006);
		Automobile nuovaAutomobile2 = new Automobile("dodge", "charger", "ad6g387",2020);
		
	
		nuovaAutomobile1.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile1);
		nuovaAutomobile2.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile2);
		
		int inizio=2000;
		if(proprietarioService.contaQuantiProprietariConAutoImmatricolataDopo(inizio) <2)
			throw new RuntimeException("testContaQuantiProprietariConAutoImmatricolataDopo fallito: record non rilevati ");
		
		Long idAutomobileInserita1 = nuovaAutomobile1.getId();
		automobileService.rimuovi(idAutomobileInserita1);
		Long idAutomobileInserita2 = nuovaAutomobile2.getId();
		automobileService.rimuovi(idAutomobileInserita2);
		
		System.out.println(".......testContaQuantiProprietariConAutoImmatricolataDopo fine: PASSED.............");
     }
	
	private static void testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer(ProprietarioService proprietarioService, AutomobileService automobileService) throws Exception{
		System.out.println(".......testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer inizio.............");
		
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer fallito: non ci sono proprietari a cui collegarci ");

		
		Automobile nuovaAutomobile1= new Automobile("dodge", "viper", "ad6387",2006);
		Automobile nuovaAutomobile2 = new Automobile("dodge", "charger", "ad6g387",2020);
		
	
		nuovaAutomobile1.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile1);
		nuovaAutomobile2.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile2);
		
		String cercato=listaProprietariPresenti.get(0).getCodiceFiscale();
		if(automobileService.cercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer(cercato).size() <2)
			throw new RuntimeException("testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer fallito: record non rilevati ");
		
		
		Long idAutomobileInserita1 = nuovaAutomobile1.getId();
		automobileService.rimuovi(idAutomobileInserita1);
		Long idAutomobileInserita2 = nuovaAutomobile2.getId();
		automobileService.rimuovi(idAutomobileInserita2);

		
		System.out.println(".......testCercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer fine: PASSED.............");
     }
	
	private static void testcercaTutteLeAutomobiliConErrori(ProprietarioService proprietarioService, AutomobileService automobileService) throws Exception{
		System.out.println(".......testcercaTutteLeAutomobiliConErrori inizio.............");
		
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testcercaTutteLeAutomobiliConErrori fallito: non ci sono proprietari a cui collegarci ");

		
		Automobile nuovaAutomobile1= new Automobile("dodge", "viper", "ad6387",2006);
		Automobile nuovaAutomobile2 = new Automobile("dodge", "charger", "ad6g387",2020);
		
	
		nuovaAutomobile1.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile1);
		nuovaAutomobile2.setProprietario(listaProprietariPresenti.get(0));
		automobileService.inserisciNuovo(nuovaAutomobile2);
		
		String cercato=listaProprietariPresenti.get(0).getCodiceFiscale();
		if(automobileService.cercaTutteLeAutomobiliConErrori().size() <2)
			throw new RuntimeException("testcercaTutteLeAutomobiliConErrori fallito: record non rilevati ");
		
		
		Long idAutomobileInserita1 = nuovaAutomobile1.getId();
		automobileService.rimuovi(idAutomobileInserita1);
		Long idAutomobileInserita2 = nuovaAutomobile2.getId();
		automobileService.rimuovi(idAutomobileInserita2);

		
		System.out.println(".......testcercaTutteLeAutomobiliConErrori fine: PASSED.............");
     }
	
	
}
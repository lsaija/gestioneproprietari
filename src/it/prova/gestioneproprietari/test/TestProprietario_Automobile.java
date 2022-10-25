package it.prova.gestioneproprietari.test;

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
					"In tabella Municipio ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testInserisciProprietario(proprietarioService);
			System.out.println(
					"In tabella Municipio ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testInserisciAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Municipio ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

			testRimozioneAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Municipio ci sono " + proprietarioService.listAllProprietari().size() + " elementi.");

	    } catch (Throwable e) {
		    e.printStackTrace();
	    } finally {
		
		        EntityManagerUtil.shutdown();
	    }
		
	}
	
	
	private static void testInserisciProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testInserisciProprietario inizio.............");
		
		Proprietario nuovoProprietario = new Proprietario("filippo", "bianchi", "flppbc423hsu",new Date());
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
	
	

}

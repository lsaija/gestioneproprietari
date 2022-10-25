package it.prova.gestioneproprietari.service.automobile;

import java.util.List;

import it.prova.gestioneproprietari.model.Automobile;
import it.prova.gestioneproprietario.dao.automobile.AutomobileDAO;



public interface AutomobileService {
	public List<Automobile> listAllAutomobili() throws Exception;

	public Automobile caricaSingolaAutomobile(Long id) throws Exception;

	public void aggiorna(Automobile automobileInstance) throws Exception;

	public void inserisciNuovo(Automobile automobileInstance) throws Exception;

	public void rimuovi(Long idAutomobileInserita) throws Exception;


	//per injection
	public void setAutomobileDAO(AutomobileDAO automobileDAO);
	
	//altri metodi
	public List<Automobile> cercaTutteLeAutomobiliProprietariConCodicefiscaleIniziaPer(String iniziale) throws Exception;
	
	public List<Automobile> cercaTutteLeAutomobiliConErrori() throws Exception;
	

}

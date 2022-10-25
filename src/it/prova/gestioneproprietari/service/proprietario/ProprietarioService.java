package it.prova.gestioneproprietari.service.proprietario;

import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.model.Proprietario;
import it.prova.gestioneproprietario.dao.proprietario.ProprietarioDAO;



public interface ProprietarioService {
	
	public List<Proprietario> listAllProprietari() throws Exception;

	public Proprietario caricaSingoloProprietario(Long id) throws Exception;
	
	public Proprietario caricaSingoloProprietarioConAutomobii(Long id) throws Exception;

	public void aggiorna(Proprietario proprietarioInstance) throws Exception;

	public void inserisciNuovo(Proprietario proprietarioInstance) throws Exception;

	public void rimuovi(Proprietario proprietarioInstance) throws Exception;

	
	//per injection
	public void setProprietarioDAO(ProprietarioDAO proprietarioDAO);
	
	//altri metodi
	public int contaQuantiProprietariConAutoImmatricolataDopo(int inizio) throws Exception;
	

}

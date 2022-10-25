package it.prova.gestioneproprietario.dao.automobile;

import java.util.List;

import it.prova.gestioneproprietari.dao.IBaseDAO;
import it.prova.gestioneproprietari.model.Automobile;


public interface AutomobileDAO extends IBaseDAO<Automobile>{
	
	
	public List<Automobile> findAllByProprietariConCodicefiscaleIniziaPer(String iniziale) throws Exception;
	
	public List<Automobile> findAllByErrori() throws Exception;
	
}

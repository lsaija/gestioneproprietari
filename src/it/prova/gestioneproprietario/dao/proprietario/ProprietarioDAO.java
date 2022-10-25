package it.prova.gestioneproprietario.dao.proprietario;

import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.dao.IBaseDAO;
import it.prova.gestioneproprietari.model.Automobile;
import it.prova.gestioneproprietari.model.Proprietario;

public interface ProprietarioDAO extends IBaseDAO<Proprietario>{
	public Proprietario getEagerAutomobili(Long id) throws Exception;
	public int countProprietariConAutoImmatricolataDopo(int inizio)throws Exception;

}

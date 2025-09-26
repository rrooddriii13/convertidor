package es.curso.service;

import java.util.List;
import es.curso.model.Vacante;


public interface IVacantesService {
	//1 metodo que va a regresar una lista de vacantes
	List<Vacante> buscarTodas();
	
	//devuelve un obj tipo Vacante segun el id
	Vacante buscarPorId(Integer idVacante);
	

}

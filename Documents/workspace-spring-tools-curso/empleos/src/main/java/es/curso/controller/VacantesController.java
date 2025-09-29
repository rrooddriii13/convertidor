package es.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.curso.model.Vacante;
import es.curso.service.IVacantesService;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	
	//forma inyectar servicio
	@Autowired
	private IVacantesService serviceVacantes;
	
	//
	@GetMapping("/view/{id}")
	//deccclarar parametro id en el metodo verDetalle
	//PathVariable para que spring sepa que es un parametro de la ruta
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		
		//obj tipo vacante 
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
		//mostrar valor en la consola 
		System.out.println(" vacante: " + vacante);
		//agregar al modelo hacer la vista pasarle datos
		model.addAttribute("vacante", vacante);
		//return "vacantes/detalle";
		return "detalle";
	}
	
	//el nombre de "" debe ser igual al del formulario
	@PostMapping("/save")
	public String guardar(@RequestParam("nombre")String nombre,@RequestParam("descripcion")String descripcion,@RequestParam("estatus")String estatus,@RequestParam("fecha")String fecha,@RequestParam("destacado")int destacado,@RequestParam("salario")double salario,@RequestParam("detalles")String detalles, Model model) {	
		System.out.println("Nombre: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha de publicacion: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario: " + salario);
		System.out.println("Detalles: " + detalles);
		return "vacantes/listVacantes";
	}
	
	
	//crear vacante
	@GetMapping("/create")
	public String crear() {
		return "vacantes/formVacante";
	}
	
	//eliminar 
	@GetMapping("/delete/{id}")
	//@GetMapping("/delete")
	public String eliminar( @PathVariable("id") int idVacante, Model model) {
		
		//poara ver si obtienes el id
		System.out.println("Id vacante a eliminar: " + idVacante);
		//agregar al modelo hacer la vista pasarle datos
		model.addAttribute("id", idVacante);
		return "mensaje";
	}

}

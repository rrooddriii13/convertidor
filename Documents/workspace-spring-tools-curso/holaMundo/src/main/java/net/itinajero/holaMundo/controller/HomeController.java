package net.itinajero.holaMundo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Controlador de tipo REST sirve para exponer servicios REST
@RestController
public class HomeController {
	
	// Mapeo de la URL raiz 
	@GetMapping("/")
	public String inicio() {
		return "Hola Mundo desde Spring Boot";
	}

}

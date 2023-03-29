package com.registrousuarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registrousuarios.dto.UsuarioRegistroDTO;
import com.registrousuarios.service.UsuarioService;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioController {

	private UsuarioService usuarioService;

	public RegistroUsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}
	
	@GetMapping
	public String mostrarFormularioDeRegistro() {
		return "registro";
	}
	
	@PostMapping
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO usuarioRegistroDTO) {
		usuarioService.guardarUsuario(usuarioRegistroDTO);
		return "redirect:/registro?exito"; //?Exito seria el mensaje q mandariamos al thymeleaf
	}
}

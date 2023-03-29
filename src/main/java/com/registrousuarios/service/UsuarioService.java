package com.registrousuarios.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.registrousuarios.dto.UsuarioRegistroDTO;
import com.registrousuarios.modelo.Usuario;

public interface UsuarioService extends UserDetailsService{

	public Usuario guardarUsuario(UsuarioRegistroDTO usuarioRegistroDTO);
	public List<Usuario> listarUsuarios();


}

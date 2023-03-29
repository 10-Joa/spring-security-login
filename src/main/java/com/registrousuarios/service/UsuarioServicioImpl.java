package com.registrousuarios.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.registrousuarios.dto.UsuarioRegistroDTO;
import com.registrousuarios.modelo.Rol;
import com.registrousuarios.modelo.Usuario;
import com.registrousuarios.repository.UsuarioRepository;

@Service
public class UsuarioServicioImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Override
	public Usuario guardarUsuario(UsuarioRegistroDTO usuarioRegistroDTO) {
		Usuario usuario= new Usuario(usuarioRegistroDTO.getNombre(), 
				usuarioRegistroDTO.getApellido(), usuarioRegistroDTO.getEmail(),
//				usuarioRegistroDTO.getPassword(),Arrays.asList(new Rol("ROLE_USER")));// asi puedo ver la contraseña en mi bd
			//de esta otra manera estaria con passwordencod guardada
				passwordEncoder.encode(usuarioRegistroDTO.getPassword()),Arrays.asList(new Rol("ROLE_USER")));
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(usuario.getEmail(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}


	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}
}

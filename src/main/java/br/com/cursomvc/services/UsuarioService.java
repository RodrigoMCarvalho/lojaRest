package br.com.cursomvc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.cursomvc.security.Usuario;

public class UsuarioService {

	public static Usuario authenticated() {  //obtém o usuário logado
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} 
		catch (Exception e) {
			return null;
		}
	}
}

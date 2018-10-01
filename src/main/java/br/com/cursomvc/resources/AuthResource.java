package br.com.cursomvc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomvc.security.JWTUtil;
import br.com.cursomvc.security.Usuario;
import br.com.cursomvc.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
	Usuario usuario = UsuarioService.authenticated();
	String token = jwtUtil.generateToken(usuario.getUsername());
	response.addHeader("Authorization", "Bearer " + token);
	
	return ResponseEntity.noContent().build();
	}

}

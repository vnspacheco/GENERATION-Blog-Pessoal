package br.org.generation.blogpessoal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.UsuarioLoguinModel;
import br.org.generation.blogpessoal.model.UsuarioModel;
import br.org.generation.blogpessoal.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLoguinModel> Autentication (@RequestBody Optional<UsuarioLoguinModel> user) {
		return usuarioService.Logar(user)
		.map(resp -> ResponseEntity.ok(resp))
		.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UsuarioModel> post (@RequestBody UsuarioModel usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuario));
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<UsuarioModel> put(@RequestBody UsuarioModel usuario){
		Optional<UsuarioModel> usuarioUpdate = usuarioService.atualizarUsuario(usuario);
		try {
			return ResponseEntity.ok(usuarioUpdate.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
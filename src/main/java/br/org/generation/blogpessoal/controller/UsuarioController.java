package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.UsuarioLoginModel;
import br.org.generation.blogpessoal.model.UsuarioModel;
import br.org.generation.blogpessoal.repository.UsuarioRepository;
import br.org.generation.blogpessoal.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	private UsuarioRepository repository;
	
	@GetMapping
	public ResponseEntity<List<UsuarioModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioModel> getById(@PathVariable long id) {
		return repository.findById(id)
		.map(resp -> ResponseEntity.ok(resp))
		.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLoginModel> Autentication (@RequestBody Optional<UsuarioLoginModel> user) {
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
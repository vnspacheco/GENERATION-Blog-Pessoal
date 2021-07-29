package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.blogpessoal.model.PostagemModel;
import br.org.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagem")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	@GetMapping
	public ResponseEntity<List<PostagemModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostagemModel> getById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<PostagemModel>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<PostagemModel> post(@RequestBody PostagemModel postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostagemModel> put(@PathVariable long id, @RequestBody PostagemModel postagem) {
		Optional<PostagemModel> postagem2 = repository.findById(id);

		if (postagem2.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada", null);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<PostagemModel> postagem = repository.findById(id); 
		
		if (postagem.isPresent()) {
			repository.deleteById(id);
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada", null);
		}
	}
}
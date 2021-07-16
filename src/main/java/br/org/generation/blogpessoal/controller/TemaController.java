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

import br.org.generation.blogpessoal.model.TemaModel;
import br.org.generation.blogpessoal.repository.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<TemaModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TemaModel> getById(@PathVariable long id) {
		return repository.findById(id)
		.map(resp -> ResponseEntity.ok(resp))
		.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<TemaModel>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<TemaModel> post (@RequestBody TemaModel tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TemaModel> put (@PathVariable long id, @RequestBody TemaModel tema){
		Optional<TemaModel> tema2 = repository.findById(id);
		
		if (tema2.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(tema));
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado", null);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<TemaModel> tema = repository.findById(id);
		
		if (tema.isPresent()) {
			repository.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado", null);
		}
	}
}
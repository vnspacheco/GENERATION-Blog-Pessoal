package br.org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.blogpessoal.model.UsuarioModel;

public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private UsuarioModel usuario;
	private UsuarioModel usuarioUpdate;

	
	@BeforeAll
	public void start() {

       usuario = new UsuarioModel(0, "Administrador", "admin@email.com.br", "admin123");
       //usuario = new Usuario(0, "João da Silva", "joao@email.com.br", "13465278", dataPost);
        
        usuarioUpdate = new UsuarioModel(6L, "João da Silva Souza", "joao@email.com.br", "joao123");
	}

	@Test
    @DisplayName("✔ Cadastrar Usuário!")
	public void deveRealizarPostUsuario() {

		HttpEntity<UsuarioModel> request = new HttpEntity<UsuarioModel>(usuario);

		ResponseEntity<UsuarioModel> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, UsuarioModel.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

	}
	
	@Test
    @DisplayName("👍 Listar todos os Usuários!")
	public void deveMostrarTodosUsuarios() {
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Disabled
	@Test
    @DisplayName("😳 Alterar Usuário!")
	public void deveRealizarPutUsuario() {

		HttpEntity<UsuarioModel> request = new HttpEntity<UsuarioModel>(usuarioUpdate);

		ResponseEntity<UsuarioModel> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/alterar", HttpMethod.PUT, request, UsuarioModel.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
}
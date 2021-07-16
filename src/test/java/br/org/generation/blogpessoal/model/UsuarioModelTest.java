package br.org.generation.blogpessoal.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioModelTest {

	public UsuarioModel usuario;
    public UsuarioModel usuarioErro = new UsuarioModel();
	
	@Autowired
	private  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	Validator validator = factory.getValidator();
	
	@BeforeEach
	public void start() {
		
        usuario = new UsuarioModel(1L, "Vinicius", "vnspacheco", "teste12345");

	}

	@Test
	@DisplayName("✔ Valida Atributos Não Nulos")
	void testValidaAtributos() {
		
		Set<ConstraintViolation<UsuarioModel>> violacao = validator.validate(usuario);
		
		System.out.println(violacao.toString());
		
		assertTrue(violacao.isEmpty());
	}
	
	@Test
	@DisplayName("❌ Valida Atributos Nulos")
	void testValidaAtributosNulos() {
		
		usuarioErro.setUsuario("pachecovns");

		Set<ConstraintViolation<UsuarioModel>> violacao = validator.validate(usuarioErro);
		
		System.out.println(violacao.toString());
		
		assertFalse(violacao.isEmpty());
		
	}
}
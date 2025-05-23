package com.norteando.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.norteando.model.Restaurante;
import com.norteando.repository.RestauranteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> getAll(){
		return ResponseEntity.ok(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> getById(@PathVariable Long id) {
		return restauranteRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Restaurante>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(restauranteRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Restaurante> post(@Valid @RequestBody Restaurante restaurante){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(restauranteRepository.save(restaurante));
	}
	
	@PutMapping
	public ResponseEntity<Restaurante> put(@Valid @RequestBody Restaurante restaurante) {
		return restauranteRepository.findById(restaurante.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(restauranteRepository.save(restaurante)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		if(restaurante.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		restauranteRepository.deleteById(id);
	}
	

}

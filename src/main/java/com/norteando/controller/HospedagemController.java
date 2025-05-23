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

import com.norteando.model.Hospedagem;
import com.norteando.repository.HospedagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospedagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HospedagemController {
		
	@Autowired
	private HospedagemRepository hospedagemRepository;
		
	@GetMapping
	public ResponseEntity<List<Hospedagem>> getAll(){
		return ResponseEntity.ok(hospedagemRepository.findAll());
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Hospedagem> getById(@PathVariable Long id) {
		return hospedagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
		
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Hospedagem>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(hospedagemRepository.findAllByNomeContainingIgnoreCase(nome));
	}
		
	@PostMapping
	public ResponseEntity<Hospedagem> post(@Valid @RequestBody Hospedagem hospedagem){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(hospedagemRepository.save(hospedagem));
	}
		
	@PutMapping
	public ResponseEntity<Hospedagem> put(@Valid @RequestBody Hospedagem hospedagem) {
		return hospedagemRepository.findById(hospedagem.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(hospedagemRepository.save(hospedagem)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
		
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Hospedagem> hospedagem = hospedagemRepository.findById(id);
			
		if(hospedagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
		hospedagemRepository.deleteById(id);
	}

}

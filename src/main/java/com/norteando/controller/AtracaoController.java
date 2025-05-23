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

import com.norteando.model.Atracao;
import com.norteando.repository.AtracaoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atracoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AtracaoController {
	
	@Autowired
	private AtracaoRepository atracaoRepository;
		
	@GetMapping
	public ResponseEntity<List<Atracao>> getAll(){
		return ResponseEntity.ok(atracaoRepository.findAll());
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Atracao> getById(@PathVariable Long id) {
		return atracaoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
		
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Atracao>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(atracaoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
		
	@PostMapping
	public ResponseEntity<Atracao> post(@Valid @RequestBody Atracao atracao){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(atracaoRepository.save(atracao));
	}
		
	@PutMapping
	public ResponseEntity<Atracao> put(@Valid @RequestBody Atracao atracao) {
		return atracaoRepository.findById(atracao.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(atracaoRepository.save(atracao)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
		
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Atracao> atracao = atracaoRepository.findById(id);
		
		if(atracao.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		atracaoRepository.deleteById(id);
	}

}

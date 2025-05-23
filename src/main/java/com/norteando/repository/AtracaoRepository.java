package com.norteando.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.norteando.model.Atracao;

public interface AtracaoRepository extends JpaRepository<Atracao, Long>{
	
	public Optional<Atracao> findByNome(@Param("nome") String nome);
	
	public List<Atracao> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}

package com.norteando.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.norteando.model.Hospedagem;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long>{
	
	public Optional<Hospedagem> findByNome(@Param("nome") String nome);
	
	public List<Hospedagem> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}

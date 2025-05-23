package com.norteando.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.norteando.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	public Optional<Categoria> findByNome(@Param("nome") String nome);
	
	public List<Categoria> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
}

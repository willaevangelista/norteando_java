package com.norteando.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.norteando.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
public Optional<Restaurante> findByNome(@Param("nome") String nome);
	
	public List<Restaurante> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}

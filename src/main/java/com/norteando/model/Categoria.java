package com.norteando.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome é obrigatório!")
	@Size(max = 100, message = "O atributo nome deve conter no máximo 100 caracteres!")
	private String nome;
	
	@ManyToMany(mappedBy = "categorias")
	private Set<Atracao> atracoes = new HashSet<>();
	
	@ManyToMany(mappedBy = "categorias")
	@JsonIgnoreProperties({"atracoes", "hospedagens", "restaurantes"})
	private Set<Hospedagem> hospedagens = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorias", cascade = CascadeType.MERGE)
	@JsonIgnoreProperties({"atracoes", "hospedagens", "restaurantes"})
	private Set<Restaurante> restaurantes = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Atracao> getAtracoes() {
		return atracoes;
	}

	public void setAtracoes(Set<Atracao> atracoes) {
		this.atracoes = atracoes;
	}

	public Set<Hospedagem> getHospedagens() {
		return hospedagens;
	}

	public void setHospedagens(Set<Hospedagem> hospedagens) {
		this.hospedagens = hospedagens;
	}

	public Set<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(Set<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}
	
}

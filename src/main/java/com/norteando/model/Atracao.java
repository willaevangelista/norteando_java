package com.norteando.model;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_atracoes")
public class Atracao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome é obrigatório!")
	@Size(max = 100, message = "O atributo nome deve conter no máximo 100 caracteres!")
	private String nome;
	
	@NotBlank(message = "O atributo descrição curta é obrigatório!")
	@Size(max = 200, message = "O atributo descrição curta deve conter no máximo 200 caracteres!")
	private String descricaoCurta;
	
	@NotBlank(message = "O atributo descrição longa é obrigatório!")
	@Column(columnDefinition = "TEXT")
	private String descricaoLonga;
	
	@NotBlank(message = "O atributo foto é obrigatório!")
	@Size(max = 500, message = "O atributo foto deve conter no máximo 500 caracteres!")
	private String foto;
	
	@NotBlank(message = "O atributo horário de funcionamento é obrigatório!")
	@Size(max = 200, message = "O atributo horário de funcionamento deve conter no máximo 200 caracteres!")
	private String funcionamento;
	
	@NotBlank(message = "O atributo endereço é obrigatório!")
	@Size(max = 200, message = "O atributo endereço deve conter no máximo 200 caracteres!")
	private String endereco;
	
	@NotNull(message = "O atributo latitude é obrigatório!")
	private Double latitude;
	
	@NotNull(message = "O atributo longitude é obrigatório!")
	private Double longitude;
	
	@ManyToMany
	@JsonIgnoreProperties({"hospedagens", "restaurantes", "atracoes"})
    @JoinTable(
        name = "tb_atracao_categoria",
        joinColumns = @JoinColumn(name = "tb_atracao_id"),
        inverseJoinColumns = @JoinColumn(name = "tb_categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

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

	public String getDescricaoCurta() {
		return descricaoCurta;
	}

	public void setDescricaoCurta(String descricaoCurta) {
		this.descricaoCurta = descricaoCurta;
	}

	public String getDescricaoLonga() {
		return descricaoLonga;
	}

	public void setDescricaoLonga(String descricaoLonga) {
		this.descricaoLonga = descricaoLonga;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFuncionamento() {
		return funcionamento;
	}

	public void setFuncionamento(String funcionamento) {
		this.funcionamento = funcionamento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

}

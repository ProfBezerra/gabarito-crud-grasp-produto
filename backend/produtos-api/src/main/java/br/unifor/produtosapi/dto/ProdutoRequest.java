package br.unifor.produtosapi.dto;

import jakarta.validation.constraints.*;

public class ProdutoRequest {
  
    @NotBlank(message = "Nome nao pode estar vazio")
    private String nome;
  
    @NotNull(message = "Preco nao pode ser nulo")
    @Positive(message = "Preco deve ser maior que zero")
    private Double preco;
  
    @NotNull(message = "Tipo nao pode ser nulo")
    private Long tipoId;
  
    // Getters e setters
    public String getNome() {
        return nome;
    }
  
    public void setNome(String nome) {
        this.nome = nome;
    }
  
    public Double getPreco() {
        return preco;
    }
  
    public void setPreco(Double preco) {
        this.preco = preco;
    }
  
    public Long getTipoId() {
        return tipoId;
    }
  
    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
}
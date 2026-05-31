package br.unifor.produtosapi.dto;

public class ProdutoResponse {
  
    private Long id;
    private String nome;
    private Double preco;
    private Long tipoId;
    private String tipoNome;
  
    public ProdutoResponse(Long id, String nome, Double preco, Long tipoId, String tipoNome) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipoId = tipoId;
        this.tipoNome = tipoNome;
    }
  
    // Getters
    public Long getId() {
        return id;
    }
  
    public String getNome() {
        return nome;
    }
  
    public Double getPreco() {
        return preco;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public String getTipoNome() {
        return tipoNome;
    }

    @Override
    public String toString() {
        return "ProdutoResponse{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", preco=" + preco +
            ", tipoId=" + tipoId +
            ", tipoNome='" + tipoNome + '\'' +
            '}';
    }
}
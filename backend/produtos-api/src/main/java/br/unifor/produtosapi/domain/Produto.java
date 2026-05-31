package br.unifor.produtosapi.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(nullable = false, length = 150)
    private String nome;
  
    @Column(nullable = false)
    private Double preco;
  
    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoProduto tipo;
  
    public Produto() {}
  
    public Produto(String nome, Double preco, TipoProduto tipo) {
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
    }
  
    // Getters e setters
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
  
    public Double getPreco() {
        return preco;
    }
  
    public void setPreco(Double preco) {
        this.preco = preco;
    }
  
    public TipoProduto getTipo() {
        return tipo;
    }
  
    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", preco=" + preco +
            ", tipo=" + tipo +
            '}';
    }
}
package br.unifor.produtosapi.service;

import br.unifor.produtosapi.domain.Produto;
import br.unifor.produtosapi.domain.TipoProduto;
import br.unifor.produtosapi.dto.ProdutoRequest;
import br.unifor.produtosapi.dto.ProdutoResponse;
import br.unifor.produtosapi.repository.ProdutoRepository;
import br.unifor.produtosapi.repository.TipoProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
  
    private final ProdutoRepository produtoRepository;
    private final TipoProdutoRepository tipoProdutoRepository;
  
    public ProdutoService(ProdutoRepository produtoRepository,
                          TipoProdutoRepository tipoProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.tipoProdutoRepository = tipoProdutoRepository;
    }
  
    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll().stream()
            .map(p -> new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getPreco(),
                p.getTipo().getNome()
            ))
            .collect(Collectors.toList());
    }
  
    public ProdutoResponse criar(ProdutoRequest request) {
        TipoProduto tipo = tipoProdutoRepository.findById(request.getTipoId())
            .orElseThrow(() -> new RuntimeException("Tipo nao encontrado"));
    
        Produto produto = new Produto(request.getNome(), request.getPreco(), tipo);
        Produto salvo = produtoRepository.save(produto);
    
        return new ProdutoResponse(salvo.getId(), salvo.getNome(), 
            salvo.getPreco(), salvo.getTipo().getNome());
    }
  
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
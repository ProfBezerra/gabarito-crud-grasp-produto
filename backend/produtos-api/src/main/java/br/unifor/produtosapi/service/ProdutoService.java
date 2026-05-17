package br.unifor.produtosapi.service;

import br.unifor.produtosapi.domain.Produto;
import br.unifor.produtosapi.domain.TipoProduto;
import br.unifor.produtosapi.dto.ProdutoRequest;
import br.unifor.produtosapi.dto.ProdutoResponse;
import br.unifor.produtosapi.repository.ProdutoRepository;
import br.unifor.produtosapi.repository.TipoProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
                p.getTipo().getId(),
                p.getTipo().getNome()
            ))
            .collect(Collectors.toList());
    }
  
    public ProdutoResponse criar(ProdutoRequest request) {
        TipoProduto tipo = tipoProdutoRepository.findById(request.getTipoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo nao encontrado"));
    
        Produto produto = new Produto(request.getNome(), request.getPreco(), tipo);
        Produto salvo = produtoRepository.save(produto);
    
        return new ProdutoResponse(salvo.getId(), salvo.getNome(),
            salvo.getPreco(), salvo.getTipo().getId(), salvo.getTipo().getNome());
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        TipoProduto tipo = tipoProdutoRepository.findById(request.getTipoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo nao encontrado"));

        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setTipo(tipo);
        Produto salvo = produtoRepository.save(produto);

        return new ProdutoResponse(salvo.getId(), salvo.getNome(),
            salvo.getPreco(), salvo.getTipo().getId(), salvo.getTipo().getNome());
    }
  
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
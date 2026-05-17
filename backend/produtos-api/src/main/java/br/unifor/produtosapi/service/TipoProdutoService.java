package br.unifor.produtosapi.service;

import br.unifor.produtosapi.domain.TipoProduto;
import br.unifor.produtosapi.repository.TipoProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class TipoProdutoService {
  
    private final TipoProdutoRepository repository;
  
    public TipoProdutoService(TipoProdutoRepository repository) {
        this.repository = repository;
    }
  
    public List<TipoProduto> listar() {
        return repository.findAll();
    }
  
    public TipoProduto criar(String nome) {
        TipoProduto tipo = new TipoProduto(nome);
        return repository.save(tipo);
    }

    public TipoProduto atualizar(Long id, String nome) {
        TipoProduto tipo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo nao encontrado"));
        tipo.setNome(nome);
        return repository.save(tipo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo nao encontrado");
        }
        repository.deleteById(id);
    }
}
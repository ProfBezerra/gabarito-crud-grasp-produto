package br.unifor.produtosapi.controller;

import br.unifor.produtosapi.domain.TipoProduto;
import br.unifor.produtosapi.dto.TipoProdutoRequest;
import br.unifor.produtosapi.service.TipoProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TipoProdutoController {
  
    private final TipoProdutoService service;
  
    public TipoProdutoController(TipoProdutoService service) {
        this.service = service;
    }
  
    @GetMapping
    public List<TipoProduto> listar() {
        return service.listar();
    }
  
    @PostMapping
    public ResponseEntity<TipoProduto> criar(@Valid @RequestBody TipoProdutoRequest request) {
        TipoProduto tipo = service.criar(request.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProduto> atualizar(@PathVariable Long id,
                                                 @Valid @RequestBody TipoProdutoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request.getNome()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
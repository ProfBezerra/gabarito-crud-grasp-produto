package br.unifor.produtosapi.controller;

import br.unifor.produtosapi.dto.ProdutoRequest;
import br.unifor.produtosapi.dto.ProdutoResponse;
import br.unifor.produtosapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
  
    private final ProdutoService service;
  
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
  
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
  
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }
  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
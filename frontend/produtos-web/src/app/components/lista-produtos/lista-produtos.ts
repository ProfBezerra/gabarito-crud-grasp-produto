import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProdutoApiService } from '../../services/produto-api';

@Component({
  selector: 'app-lista-produtos',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './lista-produtos.html',
  styleUrls: ['./lista-produtos.css']
})
export class ListaProdutosComponent implements OnInit {
  produtos: any[] = [];
  carregando = false;

  constructor(private api: ProdutoApiService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos() {
    this.carregando = true;
    this.api.getProdutos().subscribe(
      dados => {
        let arr: any[] = [];
        if (Array.isArray(dados)) {
          arr = dados;
        } else if (dados && Array.isArray((dados as any).content)) {
          arr = (dados as any).content;
        } else if (dados && Array.isArray((dados as any).data)) {
          arr = (dados as any).data;
        }

        this.produtos = arr.map(p => ({
          ...p,
          tipo: p.tipo ?? (p.tipoNome ? { nome: p.tipoNome } : undefined)
        }));

        this.carregando = false;
        this.cdr.detectChanges();
      },
      erro => {
        console.error('Erro ao carregar', erro);
        this.carregando = false;
        this.cdr.detectChanges();
      }
    );
  }

  deletar(id: number) {
    this.api.deletarProduto(id).subscribe(
      () => this.carregarProdutos(),
      erro => console.error('Erro ao deletar', erro)
    );
  }
}

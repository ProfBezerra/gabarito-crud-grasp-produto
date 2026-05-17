import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ProdutoApiService } from '../../services/produto-api';

@Component({
  selector: 'app-lista-produtos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './lista-produtos.html',
  styleUrls: ['./lista-produtos.css']
})
export class ListaProdutosComponent implements OnInit {
  produtos: any[] = [];
  tipos: any[] = [];
  carregando = false;
  mensagem = '';

  editandoId: number | null = null;
  editandoNome = '';
  editandoPreco = 0;
  editandoTipoId = 0;

  constructor(private api: ProdutoApiService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.carregarProdutos();
    this.api.getTipos().subscribe(
      dados => { this.tipos = Array.isArray(dados) ? dados : []; this.cdr.detectChanges(); },
      erro => console.error('Erro ao carregar tipos', erro)
    );
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

  iniciarEdicao(produto: any) {
    this.editandoId = produto.id;
    this.editandoNome = produto.nome;
    this.editandoPreco = produto.preco;
    this.editandoTipoId = produto.tipoId;
    this.mensagem = '';
    this.cdr.detectChanges();
  }

  cancelarEdicao() {
    this.editandoId = null;
    this.cdr.detectChanges();
  }

  salvarEdicao() {
    const dados = { nome: this.editandoNome, preco: this.editandoPreco, tipoId: this.editandoTipoId };
    this.api.atualizarProduto(this.editandoId!, dados).subscribe(
      () => {
        this.mensagem = 'Produto atualizado com sucesso!';
        this.editandoId = null;
        this.cdr.detectChanges();
        this.carregarProdutos();
      },
      erro => {
        this.mensagem = 'Erro ao atualizar: ' + erro.message;
        this.cdr.detectChanges();
      }
    );
  }
}

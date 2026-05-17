import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProdutoApiService } from '../../services/produto-api';

@Component({
  selector: 'app-lista-tipos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-tipos.html',
  styleUrls: ['./lista-tipos.css']
})
export class ListaTiposComponent implements OnInit {
  tipos: any[] = [];
  novoNome = '';
  mensagem = '';
  carregando = false;

  editandoId: number | null = null;
  nomeEditando = '';

  constructor(private api: ProdutoApiService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.carregarTipos();
  }

  carregarTipos() {
    this.carregando = true;
    this.api.getTipos().subscribe(
      dados => {
        this.tipos = Array.isArray(dados) ? dados : [];
        this.carregando = false;
        this.cdr.detectChanges();
      },
      erro => {
        console.error('Erro ao carregar tipos', erro);
        this.carregando = false;
        this.cdr.detectChanges();
      }
    );
  }

  cadastrar() {
    if (!this.novoNome.trim()) {
      this.mensagem = 'Informe o nome do tipo.';
      return;
    }
    this.api.criarTipo({ nome: this.novoNome }).subscribe(
      () => {
        this.mensagem = 'Tipo cadastrado com sucesso!';
        this.novoNome = '';
        this.cdr.detectChanges();
        this.carregarTipos();
      },
      erro => {
        this.mensagem = 'Erro ao cadastrar: ' + erro.message;
        this.cdr.detectChanges();
      }
    );
  }

  iniciarEdicao(tipo: any) {
    this.editandoId = tipo.id;
    this.nomeEditando = tipo.nome;
    this.mensagem = '';
    this.cdr.detectChanges();
  }

  cancelarEdicao() {
    this.editandoId = null;
    this.nomeEditando = '';
    this.cdr.detectChanges();
  }

  salvarEdicao() {
    if (!this.nomeEditando.trim()) {
      this.mensagem = 'Nome nao pode estar vazio.';
      this.cdr.detectChanges();
      return;
    }
    this.api.atualizarTipo(this.editandoId!, { nome: this.nomeEditando }).subscribe(
      () => {
        this.mensagem = 'Tipo atualizado com sucesso!';
        this.editandoId = null;
        this.nomeEditando = '';
        this.cdr.detectChanges();
        this.carregarTipos();
      },
      erro => {
        this.mensagem = 'Erro ao atualizar: ' + erro.message;
        this.cdr.detectChanges();
      }
    );
  }

  deletar(id: number) {
    this.api.deletarTipo(id).subscribe(
      () => {
        this.mensagem = 'Tipo excluido com sucesso!';
        this.cdr.detectChanges();
        this.carregarTipos();
      },
      erro => {
        this.mensagem = 'Erro ao excluir: ' + erro.message;
        this.cdr.detectChanges();
      }
    );
  }
}

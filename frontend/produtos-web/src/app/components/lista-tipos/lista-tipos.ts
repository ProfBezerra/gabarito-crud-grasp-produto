import { Component, OnInit } from '@angular/core';
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

  constructor(private api: ProdutoApiService) { }

  ngOnInit(): void {
    this.carregarTipos();
  }

  carregarTipos() {
    this.carregando = true;
    this.api.getTipos().subscribe(
      dados => {
        this.tipos = Array.isArray(dados) ? dados : [];
        this.carregando = false;
      },
      erro => {
        console.error('Erro ao carregar tipos', erro);
        this.carregando = false;
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
        this.carregarTipos();
      },
      erro => {
        this.mensagem = 'Erro ao cadastrar: ' + erro.message;
      }
    );
  }
}

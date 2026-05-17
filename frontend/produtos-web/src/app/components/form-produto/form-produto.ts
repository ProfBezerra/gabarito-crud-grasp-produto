import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ProdutoApiService } from '../../services/produto-api';

@Component({
  selector: 'app-form-produto',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './form-produto.html',
  styleUrls: ['./form-produto.css']
})
export class FormProdutoComponent implements OnInit {
  nome = '';
  preco = 0;
  tipoId = 0;
  tipos: any[] = [];
  mensagem = '';

  constructor(private api: ProdutoApiService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.carregarTipos();
  }

  carregarTipos() {
    this.api.getTipos().subscribe(
      dados => { this.tipos = dados; this.cdr.detectChanges(); },
      erro => console.error('Erro ao carregar tipos', erro)
    );
  }

  salvar() {
    const novo = {
      nome: this.nome,
      preco: this.preco,
      tipoId: this.tipoId
    };

    this.api.criarProduto(novo).subscribe(
      () => {
        this.router.navigate(['/produtos']);  // volta para a lista após salvar
      },
      erro => {
        if (erro.error && Array.isArray(erro.error.erros)) {
          this.mensagem = erro.error.erros.join(' | ');
        } else {
          this.mensagem = 'Erro ao criar produto.';
        }
        this.cdr.detectChanges();
      }
    );
  }
}

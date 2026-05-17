import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ProdutoApiService } from '../../services/produto-api';

@Component({
  selector: 'app-form-produto',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './form-produto.html',
  styleUrls: ['./form-produto.css']
})
export class FormProdutoComponent implements OnInit {
  nome = '';
  preco = 0;
  tipoId = 0;
  tipos: any[] = [];
  mensagem = '';

  constructor(private api: ProdutoApiService, private router: Router) { }

  ngOnInit(): void {
    this.carregarTipos();
  }

  carregarTipos() {
    this.api.getTipos().subscribe(
      dados => this.tipos = dados,
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
        this.mensagem = 'Erro ao criar produto: ' + erro.message;
      }
    );
  }
}

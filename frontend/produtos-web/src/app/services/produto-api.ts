import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Torna serviço singleton (uma instância global)
})
export class ProdutoApiService {

  private apiUrl = 'http://localhost:8080';  // URL do backend Spring Boot

  constructor(private http: HttpClient) { }  // Injeta HttpClient do Angular

  // GET: recupera todos os produtos
  // Observable: fluxo assíncrono de dados
  getProdutos(): Observable<any> {
    return this.http.get(`${this.apiUrl}/produtos`);
  }

  // POST: cria um novo produto
  criarProduto(produto: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/produtos`, produto);
  }

  // PUT: atualiza um produto
  atualizarProduto(id: number, produto: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/produtos/${id}`, produto);
  }

  // DELETE: remove um produto
  deletarProduto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/produtos/${id}`);
  }

  // GET: recupera todos os tipos
  getTipos(): Observable<any> {
    return this.http.get(`${this.apiUrl}/tipos`);
  }

  // POST: cria um novo tipo de produto
  criarTipo(tipo: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/tipos`, tipo);
  }

  // PUT: atualiza um tipo de produto
  atualizarTipo(id: number, tipo: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/tipos/${id}`, tipo);
  }

  // DELETE: remove um tipo de produto
  deletarTipo(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/tipos/${id}`);
  }
}

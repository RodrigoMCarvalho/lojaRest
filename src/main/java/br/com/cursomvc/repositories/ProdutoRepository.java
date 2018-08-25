package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursomvc.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}

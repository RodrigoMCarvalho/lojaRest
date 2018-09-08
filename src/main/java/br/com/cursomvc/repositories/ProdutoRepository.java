package br.com.cursomvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	@Query("SELECT DISTINCT obj Produto obj INNER JOIN obj.categorias cat "
			+ "WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> seach(@Param("nome")String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);

}

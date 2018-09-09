package br.com.cursomvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
//	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat "
//			+ "WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
//	Page<Produto> seach(@Param("nome")String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
	
	Page<Produto> findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(String nome,List<Categoria> categorias, Pageable pageRequest);

}

package emprestimo.livro.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import emprestimo.livro.domain.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Emprestimo obj \n"
			+ " WHERE obj.pessoa.id IN(:pessoaIds) \n"
			+ " and obj.exemplar.livro.id IN (:livroIds) \n"
			+ " and obj.retirada BETWEEN :retiradaGte and :retiradaLte \n"
			+ " and ( \n"
			+ "		(:semDevolucao=-1)\n"
			+ "	 or (obj.devolvido BETWEEN :devolvidoGte and :devolvidoLte and :semDevolucao=0) \n"
			+ "	 or (obj.devolvido is null and :semDevolucao=1) \n"
			+ "     ) \n")
	Page<Emprestimo> findBy(Pageable pageable, @Param("pessoaIds") List<Integer> pessoa_ids,
			 @Param("livroIds") List<Integer> livro_ids,@Param("retiradaGte") Date retirada_gte,@Param("retiradaLte") Date retirada_lte,
			 @Param("devolvidoGte") Date devolvido_gte,@Param("devolvidoLte") Date devolvido_lte,@Param("semDevolucao")Integer sem_devolucao);

}

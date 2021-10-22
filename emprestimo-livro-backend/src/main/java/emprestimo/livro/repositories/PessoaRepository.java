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

import emprestimo.livro.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	@Transactional(readOnly = true)
	Pessoa findByLogin(String login);

	List<Pessoa> findByNomeIgnoreCaseContainingOrderByNome(@Param("nome") String nome);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Pessoa obj \n"
			+ " WHERE lower(obj.nome) LIKE :nomeLike \n"
			+ " and (obj.nascimento = :nascimento or :nascimento is null) \n"
			+ " and ( \n"
			+ "		:desativado=-1\n"
			+ "	 or (:desativado=0 and obj.desativado is null) \n"
			+ "	 or (:desativado=1 and obj.desativado is not null) \n"
			+ "     ) \n"
			)
	Page<Pessoa> findBy(Pageable pageable,@Param("nomeLike") String nomeLike,@Param("nascimento") Date nascimento,@Param("desativado") Integer desativado);
}

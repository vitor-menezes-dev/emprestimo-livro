package emprestimo.livro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emprestimo.livro.domain.Exemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {

}

package emprestimo.livro.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import emprestimo.livro.domain.Emprestimo;
import emprestimo.livro.domain.enums.Perfil;
import emprestimo.livro.repositories.EmprestimoRepository;
import emprestimo.livro.security.UserSS;
import emprestimo.livro.services.exceptions.AuthorizationException;
import emprestimo.livro.services.exceptions.DataIntegrityException;
import emprestimo.livro.services.exceptions.ObjectNotFoundException;

@Service
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository repo;

	@Autowired
	private PessoaService pessoaServ;

	@Autowired
	private LivroService livroServ;

	public Emprestimo find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || (!user.hasRole(Perfil.ROLE_ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Emprestimo> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Emprestimo.class.getSimpleName()));
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há exemplares relacionados");
		}

	}

	public List<Emprestimo> findAll() {
		return repo.findAll();
	}

	public Page<Emprestimo> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Page<Emprestimo> findPage(Integer page, Integer linesPerPage, String orderBy, String direction,
			List<Integer> pessoa_ids, List<Integer> livro_ids, LocalDate retirada_gte, LocalDate retirada_lte,
			LocalDate devolucao_gte, LocalDate devolucao_lte, Integer sem_devolucao) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		if (pessoa_ids == null) {
			pessoa_ids = pessoaServ.findAll().stream().map(x -> x.getId()).collect(Collectors.toList());
		}

		if (livro_ids == null) {
			livro_ids = livroServ.findAll().stream().map(x -> x.getId()).collect(Collectors.toList());
		}
		if (retirada_gte == null) {
			retirada_gte = LocalDate.of(1900, 01, 01);
		}
		if (retirada_lte == null) {
			retirada_lte = LocalDate.of(2900, 01, 01);
		}
		if (devolucao_gte == null) {
			devolucao_gte = LocalDate.of(1900, 01, 01);
		}
		if (devolucao_lte == null) {
			devolucao_lte = LocalDate.of(2900, 01, 01);
		}

		return repo.findBy(pageRequest, pessoa_ids, livro_ids,
				Date.from(retirada_gte.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(retirada_lte.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(devolucao_gte.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(devolucao_lte.atStartOfDay(ZoneId.systemDefault()).toInstant()), sem_devolucao);
	}

}

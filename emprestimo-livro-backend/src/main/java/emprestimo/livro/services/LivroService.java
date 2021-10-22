package emprestimo.livro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import emprestimo.livro.domain.Livro;
import emprestimo.livro.domain.enums.Perfil;
import emprestimo.livro.repositories.LivroRepository;
import emprestimo.livro.security.UserSS;
import emprestimo.livro.services.exceptions.AuthorizationException;
import emprestimo.livro.services.exceptions.DataIntegrityException;
import emprestimo.livro.services.exceptions.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repo;

	public Livro find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || (!user.hasRole(Perfil.ROLE_ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Livro> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Livro.class.getSimpleName()));
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há exemplares relacionados");
		}

	}

	public List<Livro> findAll() {
		return repo.findAll();
	}


	public Page<Livro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}

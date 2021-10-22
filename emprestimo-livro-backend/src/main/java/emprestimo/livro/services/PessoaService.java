package emprestimo.livro.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.domain.enums.Perfil;
import emprestimo.livro.dto.PessoaDTO;
import emprestimo.livro.dto.PessoaNewDTO;
import emprestimo.livro.repositories.PessoaRepository;
import emprestimo.livro.security.UserSS;
import emprestimo.livro.services.exceptions.AuthorizationException;
import emprestimo.livro.services.exceptions.DataIntegrityException;
import emprestimo.livro.services.exceptions.ObjectNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	public Pessoa find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || (!user.hasRole(Perfil.ROLE_ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Pessoa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getSimpleName()));
	}

	@Transactional
	public Pessoa insert(Pessoa obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public Pessoa update(Pessoa obj) {
		Pessoa newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Pessoa newObj, Pessoa obj) {
		newObj.setNome(obj.getNome());
		newObj.setLogin(obj.getLogin());
		newObj.setDesativado(obj.getDesativado());
		newObj.setNascimento(obj.getNascimento());
		if (obj.getSenha() != null)
			newObj.setSenha(obj.getSenha());

		newObj.setPerfis(new HashSet<>());
		for (Perfil perfil : obj.getPerfis()) {
			newObj.addPerfis(perfil);
		}

	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionados");
		}

	}

	public List<Pessoa> findAll() {
		return repo.findAll();
	}

	public List<Pessoa> findByNomeIgnoreCaseContainingOrderByNome(String nome) {
		return repo.findByNomeIgnoreCaseContainingOrderByNome(nome);
	}

	public Pessoa findByLogin(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || (!user.hasRole(Perfil.ROLE_ADMIN) && !email.equals(user.getUsername()))) {
			throw new AuthorizationException("Acesso negado");
		}
		Pessoa obj = repo.findByLogin(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Pessoa.class.getSimpleName());
		}
		return obj;
	}

	public Page<Pessoa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String nomeLike,
			LocalDate nascimento, Integer desativado) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Date dtNascimento;
		if (nascimento != null)
			dtNascimento = Date.from(nascimento.atStartOfDay(ZoneId.systemDefault()).toInstant());
		else
			dtNascimento = null;

		System.out.println(nomeLike + " - " + dtNascimento + " - " + desativado);
		return repo.findBy(pageRequest, "%" + nomeLike + "%", dtNascimento, desativado);
	}

	public Pessoa fromDTO(PessoaDTO objDto) {
		System.out.println(objDto);
		String senha = objDto.getSenha() != null ? pe.encode(objDto.getSenha()) : null;
		return new Pessoa(objDto.getId(), objDto.getNome(), objDto.getLogin(), senha, objDto.getNascimento(),
				objDto.getDesativadoDate());
	}

	public Pessoa fromDTO(PessoaNewDTO objDto) {
		return new Pessoa(null, objDto.getNome(), objDto.getLogin(), pe.encode(objDto.getSenha()),
				objDto.getNascimento(), null);
	}

}

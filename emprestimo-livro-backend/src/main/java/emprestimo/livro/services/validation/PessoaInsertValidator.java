package emprestimo.livro.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.dto.PessoaNewDTO;
import emprestimo.livro.repositories.PessoaRepository;
import emprestimo.livro.resources.exceptions.FieldMessage;

public class PessoaInsertValidator implements ConstraintValidator<PessoaInsert, PessoaNewDTO> {

	@Autowired
	PessoaRepository repo;


	@Override
	public boolean isValid(PessoaNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
// inclua os testes aqui, inserindo erros na lista

		Pessoa aux = repo.findByLogin(objDto.getLogin());
		if (aux != null) {
			list.add(new FieldMessage("login", "Login já cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

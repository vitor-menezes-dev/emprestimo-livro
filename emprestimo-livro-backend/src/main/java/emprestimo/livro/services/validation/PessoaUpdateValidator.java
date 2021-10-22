package emprestimo.livro.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.dto.PessoaDTO;
import emprestimo.livro.repositories.PessoaRepository;
import emprestimo.livro.resources.exceptions.FieldMessage;

public class PessoaUpdateValidator implements ConstraintValidator<PessoaUpdate, PessoaDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	PessoaRepository repo;

	@Override
	public boolean isValid(PessoaDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked" )
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		List<FieldMessage> list = new ArrayList<>();

		Pessoa aux = repo.findByLogin(objDto.getLogin());
		if (aux != null && !aux.getId().equals(Integer.parseInt(map.get("id")))) {
			list.add(new FieldMessage("login", "Login já cadastrado para o usuário "+aux));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

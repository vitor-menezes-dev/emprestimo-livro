package emprestimo.livro.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import emprestimo.livro.domain.Emprestimo;
import emprestimo.livro.domain.Exemplar;
import emprestimo.livro.domain.Livro;
import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.domain.enums.Perfil;
import emprestimo.livro.repositories.EmprestimoRepository;
import emprestimo.livro.repositories.ExemplarRepository;
import emprestimo.livro.repositories.LivroRepository;
import emprestimo.livro.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	ExemplarRepository exemplarRepository;
	
	@Autowired
	EmprestimoRepository emprestimoRepository;


	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantieteTestDatabase() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String hojeStr = sdf.format(new Date());
		String ontemStr = sdf.format(new Date().getTime()-(24*60*60*1000));
		
		Pessoa pessoa1 = new Pessoa(null,"Administrador da Biblioteca","biblioteca",pe.encode("asd"),null,null);
		pessoa1.addPerfis(Perfil.ROLE_ADMIN);
		
		Pessoa pessoa2 = new Pessoa(null,"Felipe Palhares Sousa","felipe",pe.encode("asd"),sdf.parse("03/06/2014"),sdf.parse(ontemStr));
		
		Pessoa pessoa3 = new Pessoa(null,"Gláucia Palhares","glaucia",pe.encode("asd"),sdf.parse("27/09/1976"),null);
		pessoa3.addPerfis(Perfil.ROLE_ADMIN);
		
		Pessoa pessoa4 = new Pessoa(null,"Vitor Andrade Menezes de Sousa","vitor",pe.encode("asd"),sdf.parse("01/09/1982"),null);
		
		Livro livro1 = new Livro(null, "9788535919073", "Noites das Mil e Uma Noites");
		livro1.addAutores("Naguib Mahfouz");
		
		Livro livro2 = new Livro(null, null, "Criação de Lodo");
		livro2.addAutores("Salomão Sousa");
		
		Livro livro3 = new Livro(null, "9780345307125", "Garfield leva o bolo");
		livro3.addAutores("Jim Davis");
		
		Exemplar exemplar1 = new Exemplar(null, livro1, sdf.parse(hojeStr) );
		Exemplar exemplar2 = new Exemplar(null, livro1, sdf.parse(hojeStr) );
		Exemplar exemplar3 = new Exemplar(null, livro1, sdf.parse(hojeStr) );

		Exemplar exemplar4 = new Exemplar(null, livro2, sdf.parse(hojeStr) );
		Exemplar exemplar5 = new Exemplar(null, livro2, sdf.parse(hojeStr) );
		Exemplar exemplar6 = new Exemplar(null, livro2, sdf.parse(hojeStr) );
	
		Exemplar exemplar7 = new Exemplar(null, livro3, sdf.parse(hojeStr) );
		Exemplar exemplar8 = new Exemplar(null, livro3, sdf.parse(hojeStr) );
		Exemplar exemplar9 = new Exemplar(null, livro3, sdf.parse(hojeStr) );
		
		Emprestimo emprestimo1 = new Emprestimo(null, exemplar1, pessoa2, sdf.parse(ontemStr));
		emprestimo1.setDevolvido(sdf.parse(hojeStr));
		Emprestimo emprestimo2 = new Emprestimo(null, exemplar4, pessoa2, sdf.parse(hojeStr));
		Emprestimo emprestimo3 = new Emprestimo(null, exemplar7, pessoa2, sdf.parse(hojeStr));
		Emprestimo emprestimo4 = new Emprestimo(null, exemplar2, pessoa3, sdf.parse(ontemStr));
		emprestimo4.setDevolvido(sdf.parse(hojeStr));
		Emprestimo emprestimo5 = new Emprestimo(null, exemplar5, pessoa3, sdf.parse(hojeStr));
		Emprestimo emprestimo6 = new Emprestimo(null, exemplar8, pessoa3, sdf.parse(hojeStr));
		Emprestimo emprestimo7 = new Emprestimo(null, exemplar3, pessoa4, sdf.parse(ontemStr));
		emprestimo7.setDevolvido(sdf.parse(hojeStr));
		Emprestimo emprestimo8 = new Emprestimo(null, exemplar6, pessoa4, sdf.parse(hojeStr));
		Emprestimo emprestimo9 = new Emprestimo(null, exemplar9, pessoa4, sdf.parse(hojeStr));
	
		
		pessoaRepository.saveAll(Arrays.asList(pessoa1, pessoa2, pessoa3, pessoa4));
		
		exemplarRepository.saveAll(Arrays.asList(exemplar1,exemplar2,exemplar3,
				exemplar4,exemplar5,exemplar6,
				exemplar7,exemplar8,exemplar9));
		
		emprestimoRepository.saveAll(Arrays.asList(emprestimo1,emprestimo2,emprestimo3,emprestimo4,emprestimo5,emprestimo6,emprestimo7,emprestimo8,emprestimo9));
		
	}


}

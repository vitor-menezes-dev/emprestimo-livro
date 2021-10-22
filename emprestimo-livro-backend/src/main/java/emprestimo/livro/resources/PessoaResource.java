package emprestimo.livro.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.dto.PessoaDTO;
import emprestimo.livro.dto.PessoaNewDTO;
import emprestimo.livro.resources.utils.URL;
import emprestimo.livro.services.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> find(@PathVariable Integer id) {
		Pessoa obj = service.find(id);
		return ResponseEntity.ok(obj);
	}

	@GetMapping(value = "/login")
	public ResponseEntity<Pessoa> find(@RequestParam(value = "value") String login) {
		Pessoa obj = service.findByLogin(login);
		return ResponseEntity.ok(obj);
	}

	@PostMapping()
	public ResponseEntity<PessoaDTO> insert(@Valid @RequestBody PessoaNewDTO objDto) {
		Pessoa obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		//return ResponseEntity.created(uri).build();
		PessoaDTO objDtoR = new PessoaDTO(obj);
		return ResponseEntity.ok().body(objDtoR);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PessoaDTO> update(@Valid @RequestBody PessoaDTO objDto, @PathVariable Integer id) {
		Pessoa obj = service.fromDTO(objDto);
		obj.setId(id);
		objDto = new PessoaDTO(service.update(obj));
		return ResponseEntity.ok().body(objDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<List<PessoaDTO>> findByNome(
			@RequestParam(value = "nome_like", defaultValue = "") String nome) {
		List<Pessoa> lista = service.findByNomeIgnoreCaseContainingOrderByNome(URL.decodeParam(nome));
		List<PessoaDTO> listaDto = lista.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<PessoaDTO>> findPage(@RequestParam(value = "_page", defaultValue = "0") Integer page,
			@RequestParam(value = "_limit", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "_sort", defaultValue = "nome") String orderBy,
			@RequestParam(value = "_order", defaultValue = "ASC") String direction,
			@RequestParam(value = "nome_like", defaultValue = "") String nomeLike,
			@RequestParam(value = "nascimento", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nascimento,
			@RequestParam(value = "desativado", defaultValue = "-1", required = false) Integer desativado) {
		Page<Pessoa> lista = service.findPage(page, linesPerPage, orderBy, direction.toUpperCase(),
				URL.decodeParam(nomeLike).toLowerCase(), nascimento, desativado);
		Page<PessoaDTO> listaDto = lista.map(obj -> new PessoaDTO(obj));
		return ResponseEntity.ok().body(listaDto);
	}

}

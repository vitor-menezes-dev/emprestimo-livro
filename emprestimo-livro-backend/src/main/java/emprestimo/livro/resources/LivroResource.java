package emprestimo.livro.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emprestimo.livro.domain.Livro;
import emprestimo.livro.dto.LivroDTO;
import emprestimo.livro.services.LivroService;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

	@Autowired
	private LivroService service;
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Livro> find(@PathVariable Integer id) {
		Livro obj = service.find(id);
		return ResponseEntity.ok(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping()
	public ResponseEntity<List<LivroDTO>> findAll() {
		List<Livro> lista = service.findAll();
		List<LivroDTO> listaDto =  lista.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<LivroDTO>> findPage(@RequestParam(value = "_page", defaultValue = "0") Integer page,
			@RequestParam(value = "_limit", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "_sort", defaultValue = "nome") String orderBy,
			@RequestParam(value = "_order", defaultValue = "ASC")String direction) {
		Page<Livro> lista = service.findPage(page,linesPerPage, orderBy,direction.toUpperCase());
		Page<LivroDTO> listaDto =  lista.map(obj -> new LivroDTO(obj));
		return ResponseEntity.ok().body(listaDto);
	}
	
}

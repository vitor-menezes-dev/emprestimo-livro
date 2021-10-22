package emprestimo.livro.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emprestimo.livro.domain.Emprestimo;
import emprestimo.livro.resources.utils.URL;
import emprestimo.livro.services.EmprestimoService;

@RestController
@RequestMapping(value = "/emprestimos")
public class EmprestimoResource {

	@Autowired
	private EmprestimoService service;
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Emprestimo> find(@PathVariable Integer id) {
		Emprestimo obj = service.find(id);
		return ResponseEntity.ok(obj);
	}

	@GetMapping()
	public ResponseEntity<List<Emprestimo>> findAll() {
		List<Emprestimo> lista = service.findAll();
		return ResponseEntity.ok().body(lista);
	}
	

	@GetMapping(value = "/page")
	public ResponseEntity<Page<Emprestimo>> findPage(@RequestParam(value = "_page", defaultValue = "0") Integer page,
			@RequestParam(value = "_limit", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "_sort", defaultValue = "devolvido") String orderBy,
			@RequestParam(value = "_order", defaultValue = "DESC")String direction,
			@RequestParam(value = "pessoa_ids", defaultValue = "", required = false)String pessoa_ids,
			@RequestParam(value = "livro_ids", defaultValue = "", required = false)String livro_ids,
			@RequestParam(value = "retirada_gte", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate retirada_gte,
			@RequestParam(value = "retirada_lte", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate retirada_lte,
			@RequestParam(value = "devolvido_gte", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate devolucao_gte,
			@RequestParam(value = "devolvido_lte", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate devolucao_lte,
			@RequestParam(value = "sem_devolucao",defaultValue = "-1", required = false) Integer sem_devolucao
			) {
		
		Page<Emprestimo> lista = service.findPage(page,linesPerPage, orderBy,direction.toUpperCase(),URL.decodeIntList(pessoa_ids),URL.decodeIntList(livro_ids),retirada_gte,retirada_lte,devolucao_gte,devolucao_lte,sem_devolucao);
		
		return ResponseEntity.ok().body(lista);
	}
	
}

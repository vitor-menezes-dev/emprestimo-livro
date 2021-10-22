package emprestimo.livro.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import emprestimo.livro.domain.Exemplar;

public class ExemplarDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date adquirido;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dispensado;

	public ExemplarDTO() {
	}

	public ExemplarDTO(Exemplar exemplar) {
		super();
		this.id = exemplar.getId();
		this.adquirido = exemplar.getAdquirido();
		this.dispensado = exemplar.getDispensado();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAdquirido() {
		return adquirido;
	}

	public void setAdquirido(Date adquirido) {
		this.adquirido = adquirido;
	}

	public Date getDispensado() {
		return dispensado;
	}

	public void setDispensado(Date dispensado) {
		this.dispensado = dispensado;
	}

}

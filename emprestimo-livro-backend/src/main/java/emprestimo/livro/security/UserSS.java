package emprestimo.livro.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.domain.enums.Perfil;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String login;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	private boolean enabled;

	public UserSS(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.login = pessoa.getLogin();
		this.senha = pessoa.getSenha();
		this.authorities = pessoa.getPerfis().stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toSet());
		this.enabled = !pessoa.isDesativado();

	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSS [id=");
		builder.append(id);
		builder.append(", login=");
		builder.append(login);
		builder.append(", senha=");
		builder.append(senha);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}

}

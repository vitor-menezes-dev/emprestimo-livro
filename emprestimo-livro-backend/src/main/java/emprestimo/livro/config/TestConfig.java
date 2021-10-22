package emprestimo.livro.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import emprestimo.livro.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public boolean instantiateDatabase(DBService dbService) throws ParseException {
		dbService.instantieteTestDatabase();
		return true;
	}

}

package emprestimo.livro.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import emprestimo.livro.services.DBService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase(DBService dbService) throws ParseException {
		if (strategy.contains("create"))
			dbService.instantieteTestDatabase();
		return true;
	}

	
}

package pro.sky.StarBankApp.StarBankApp.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Primary
    @Bean(name = "defaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.h2")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateH2")
    public JdbcTemplate jdbcTemplateH2(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "jdbcTemplatePostgres")
    public JdbcTemplate jdbcTemplatePostgres(@Qualifier("postgresDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

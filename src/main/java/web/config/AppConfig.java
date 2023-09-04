package web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan("web")
@PropertySource("classpath:application.yml")
public class AppConfig {
    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(env.getProperty("driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("username"));
        dataSource.setPassword(env.getProperty("password"));

        dataSource.setDriverClassName(env.getRequiredProperty("driver-class-name"));
        dataSource.setConnectionTimeout(env.getProperty("connection-timeout", Integer.class, 30000));
        dataSource.setMaximumPoolSize(env.getProperty("maximum-pool-size", Integer.class, 6));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter vendorAdapter,
                                                                       DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);
        em.setPackagesToScan("web.model");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public JpaVendorAdapter vendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    public Properties hibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.show_sql", env.getProperty("show_sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("format_sql"));
        properties.setProperty("hibernate.dialect", env.getProperty("dialect"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hbm2ddl.auto"));

        return properties;
    }
}

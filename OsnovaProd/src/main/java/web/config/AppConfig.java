package web.config;

import org.springframework.orm.jpa.JpaVendorAdapter;
import web.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(value = "web")
public class AppConfig {


    private final Environment env;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManager() {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        em.setJpaProperties(props);
        return em;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getEntityManager().getObject());
        return manager;
    }
}

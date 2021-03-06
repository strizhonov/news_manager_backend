package repositoryit;

import com.epam.lab.repository.AuthorRepositoryImpl;
import com.epam.lab.repository.NewsRepositoryImpl;
import com.epam.lab.repository.TagRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class ITRepositoryConfig {

    private static final String PERSISTENCE_FILE_NAME = "test-persistence.xml";
    private static final String PERSISTENCE_UNIT_NAME = "test-manager";

    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(final EmbeddedDatabase embeddedDatabase) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(embeddedDatabase);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceXmlLocation(PERSISTENCE_FILE_NAME);
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();

    }

    @Bean
    public EntityManager entityManager(final EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public AuthorRepositoryImpl authorRepositoryImpl() {
        return new AuthorRepositoryImpl();
    }

    @Bean
    public TagRepositoryImpl tagRepositoryImpl() {
        return new TagRepositoryImpl();
    }

    @Bean
    public NewsRepositoryImpl newsRepositoryImpl() {
        return new NewsRepositoryImpl();
    }

}

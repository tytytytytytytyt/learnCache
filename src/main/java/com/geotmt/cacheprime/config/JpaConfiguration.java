package com.geotmt.cacheprime.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef ="jpaEntityManagerFactory",transactionManagerRef ="jpaTransactionManager",basePackages ="com.geotmt.cacheprime.dao.jpa")
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    @Qualifier("helloDataSource")
    private DataSource jpaDataSource;

    @Primary
    @Bean(name ="jpaEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().getObject().createEntityManager();
    }

    @Primary
    @Bean(name ="jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        HibernateJpaVendorAdapter japVendor =new HibernateJpaVendorAdapter();
        japVendor.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean entityManagerFactory =new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(jpaDataSource);
        entityManagerFactory.setJpaVendorAdapter(japVendor);
        entityManagerFactory.setPackagesToScan("com.geotmt.cacheprime.entity");
        return entityManagerFactory;
    }

    @Primary
    @Bean(name ="jpaTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager manager =new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}

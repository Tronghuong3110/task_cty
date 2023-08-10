package com.signupfacebook.Newlife_project_1.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EntityScan(basePackages = "com.signupfacebook.Newlife_project_1.model.entity2")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.signupfacebook.Newlife_project_1.repository.repository2",
        entityManagerFactoryRef = "companyEntityManagerFactory",
        transactionManagerRef = "companyTransactionManager"
)

public class ConfigDatabase2 {

    @Bean(name = "dataSource2")
    @ConfigurationProperties("spring.datasource.company")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "companyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory2(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource2") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.signupfacebook.Newlife_project_1.model.entity2")
                .persistenceUnit("second")
                .build();
    }

    @Bean(name = "companyTransactionManager")
    public PlatformTransactionManager transactionManager2(
            @Qualifier("companyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
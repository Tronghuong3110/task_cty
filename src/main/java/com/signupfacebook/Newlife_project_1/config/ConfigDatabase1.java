package com.signupfacebook.Newlife_project_1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@EntityScan(basePackages = "com.signupfacebook.Newlife_project_1.model.entity1")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.signupfacebook.Newlife_project_1.repository.repository1",
        entityManagerFactoryRef = "employeeEntityManagerFactory",
        transactionManagerRef = "employeeTransactionManager"
)
public class ConfigDatabase1 {

    @Primary
    @Bean(name = "dataSource1")
    @ConfigurationProperties("spring.datasource.employee")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "employeeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource1") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.signupfacebook.Newlife_project_1.model.entity1")
                .persistenceUnit("first")
                .build();
    }

    @Primary
    @Bean(name = "employeeTransactionManager")
    public PlatformTransactionManager transactionManager1(
            @Qualifier("employeeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
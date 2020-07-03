package com.goblincwl.dragontwilight.common.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * Created by QinHe on 2018-07-24.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {
                "com.goblincwl.dragontwilight.repository.primary",
                "com.goblincwl.dragontwilight.yggdrasil.repository"
        })
public class PrimaryConfig {

    @Resource(name = "primaryDruidDataSource")
    private DataSource primaryDruidDataSource;

    private final JpaProperties jpaProperties;

    public PrimaryConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDruidDataSource)
                .properties(jpaProperties.getProperties())
                .packages(
                        "com.goblincwl.dragontwilight.entity.primary",
                        "com.goblincwl.dragontwilight.yggdrasil.entity"
                ) //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManagerPrimary(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()).createEntityManager();
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()));
    }

}

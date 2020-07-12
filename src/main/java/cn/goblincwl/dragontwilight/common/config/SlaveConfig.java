package cn.goblincwl.dragontwilight.common.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "entityManagerFactorySlave",
        transactionManagerRef = "transactionManagerSlave",
        basePackages = {"cn.goblincwl.dragontwilight.repository.slave"}) //设置Repository所在位置
public class SlaveConfig {

    @Resource(name = "slaveDruidDataSource")
    private DataSource slaveDruidDataSource;

    private final JpaProperties jpaProperties;

    public SlaveConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean(name = "entityManagerFactorySlave")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySlave(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean slavePersistenceUnit = builder
                .dataSource(slaveDruidDataSource)
                .properties(jpaProperties.getProperties())
                .packages("cn.goblincwl.dragontwilight.entity.slave") //设置实体类所在位置
                .persistenceUnit("slavePersistenceUnit")
                .build();
        slavePersistenceUnit.getJpaPropertyMap().remove("hibernate.hbm2ddl.auto");
        return slavePersistenceUnit;
    }

    @Bean(name = "entityManagerSlave")
    public EntityManager entityManagerSlave(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactorySlave(builder).getObject()).createEntityManager();
    }

    @Bean(name = "transactionManagerSlave")
    public PlatformTransactionManager transactionManagerSlave(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactorySlave(builder).getObject()));
    }

}

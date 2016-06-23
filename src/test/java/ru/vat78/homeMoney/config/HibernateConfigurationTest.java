package ru.vat78.homeMoney.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"ru.vat78.homeMoney"})
@PropertySource(value = { "classpath:test.properties" })
public class HibernateConfigurationTest {

    @Autowired
    private Environment environment;

    @Bean
    public org.springframework.orm.hibernate5.LocalSessionFactoryBean sessionFactory() {
        org.springframework.orm.hibernate5.LocalSessionFactoryBean sessionFactory = new org.springframework.orm.hibernate5.LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.vat78.homeMoney.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        Properties mysqlProperties = new Properties();
        mysqlProperties.setProperty("useJDBCCompliantTimezoneShift","true");
        mysqlProperties.setProperty("useLegacyDatetimeCode","false");
        mysqlProperties.setProperty("serverTimezone","UTC");
        dataSource.setConnectionProperties(mysqlProperties);

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.mode"));
        return properties;
    }

    @Bean
    @Autowired
    public org.springframework.orm.hibernate5.HibernateTransactionManager transactionManager(SessionFactory s) {
        org.springframework.orm.hibernate5.HibernateTransactionManager txManager = new org.springframework.orm.hibernate5.HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }


}
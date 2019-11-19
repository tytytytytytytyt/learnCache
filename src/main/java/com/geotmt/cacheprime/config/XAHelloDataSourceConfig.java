package com.geotmt.cacheprime.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.geotmt.cacheprime.base.common.BuilderPopertiyes;
import com.geotmt.cacheprime.config.jta.DBConfig1;
import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.SQLException;



@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = XAHelloDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "jtaHelloSqlSessionFactory")
public class XAHelloDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.geotmt.cacheprime.dao.hello";
    static final String MAPPER_LOCATION = "classpath:mapper/hello/*.xml";

    // 配置数据源
    @Primary
    @Profile("jta")
    @Bean(name = "jtaHelloDataSource")
    public DataSource unHelloDataSource(DBConfig1 config1) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(config1.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(config1.getPassword());
        mysqlXaDataSource.setUser(config1.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("unHelloDataSource");

        xaDataSource.setMinPoolSize(config1.getMinPoolSize());
        xaDataSource.setMaxPoolSize(config1.getMaxPoolSize());
        xaDataSource.setMaxLifetime(config1.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(config1.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(config1.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(config1.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(config1.getMaxIdleTime());
        xaDataSource.setTestQuery(config1.getTestQuery());
        return xaDataSource;
    }

    @Primary
    @Profile("jta")
    @Bean(name = "jtaHelloSqlSessionFactory")
    public SqlSessionFactory jtaHelloSqlSessionFactory(@Qualifier("jtaHelloDataSource") DataSource jtaHelloDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(jtaHelloDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(XAHelloDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Primary
    @Profile("jta")
    @Bean(name = "jtaHelloTransactionManager")
    public DataSourceTransactionManager jtaHelloTransactionManager(@Qualifier("jtaHelloDataSource") DataSource jtaHelloDataSource) {
        return new DataSourceTransactionManager(jtaHelloDataSource);
    }


    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public JtaTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = userTransaction();

        JtaTransactionManager manager = new JtaTransactionManager(userTransaction, atomikosTransactionManager());
        return manager;
    }


}

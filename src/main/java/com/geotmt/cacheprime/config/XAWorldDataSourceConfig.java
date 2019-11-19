package com.geotmt.cacheprime.config;



import com.geotmt.cacheprime.config.jta.DBConfig2;
import com.mysql.cj.jdbc.MysqlXADataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
import java.sql.SQLException;



// 扫描 Mapper 接口并容器管理
@Configuration
@MapperScan(basePackages = XAWorldDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "jtaWorldSqlSessionFactory")
public class XAWorldDataSourceConfig{

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.geotmt.cacheprime.dao.world";
    static final String MAPPER_LOCATION = "classpath:mapper/world/*.xml";



    @Profile("jta")
    @Bean(name = "jtaWorldDataSource")
    public DataSource jtaWorldDataSource(DBConfig2 config2) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(config2.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(config2.getPassword());
        mysqlXaDataSource.setUser(config2.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("unWorldDataSource");

        xaDataSource.setMinPoolSize(config2.getMinPoolSize());
        xaDataSource.setMaxPoolSize(config2.getMaxPoolSize());
        xaDataSource.setMaxLifetime(config2.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(config2.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(config2.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(config2.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(config2.getMaxIdleTime());
        xaDataSource.setTestQuery(config2.getTestQuery());
        return xaDataSource;

    }

    @Profile("jta")
    @Bean(name = "jtaWorldTransactionManager")
    public DataSourceTransactionManager jtaWorldTransactionManager(@Qualifier("jtaWorldDataSource") DataSource jtaWorldDataSource) {
        return new DataSourceTransactionManager(jtaWorldDataSource);
    }


    @Profile("jta")
    @Bean(name = "jtaWorldSqlSessionFactory")
    public SqlSessionFactory jtaWorldSqlSessionFactory(@Qualifier("jtaWorldDataSource") DataSource jtaWorldDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(jtaWorldDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(XAWorldDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

//    @Data
//    @Component
//    @ConfigurationProperties(prefix = "mysql.datasource.jtaworld")
//    static class DBConfig2 {
//
//        private String url;
//        private String username;
//        private String password;
//        private int minPoolSize;
//        private int maxPoolSize;
//        private int maxLifetime;
//        private int borrowConnectionTimeout;
//        private int loginTimeout;
//        private int maintenanceInterval;
//        private int maxIdleTime;
//        private String testQuery;
//    }

}

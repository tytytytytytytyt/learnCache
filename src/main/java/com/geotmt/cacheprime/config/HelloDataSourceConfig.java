package com.geotmt.cacheprime.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Profile(value = {"dev","test"})
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = HelloDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "helloSqlSessionFactory")
public class HelloDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.geotmt.cacheprime.dao.hello";
    static final String MAPPER_LOCATION = "classpath:mapper/hello/*.xml";

    @Value("${hello.datasource.url}")
    private String url;

    @Value("${hello.datasource.username}")
    private String user;

    @Value("${hello.datasource.password}")
    private String password;

    @Value("${hello.datasource.driverClassName}")
    private String driverClass;

    @Profile(value = {"dev","test"})
    @Bean(name = "helloDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Profile(value = {"dev","test"})
    @Bean(name = "helloTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }
    @Profile(value = {"dev","test"})
    @Bean(name = "helloSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("helloDataSource") DataSource helloDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(helloDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(HelloDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }


}

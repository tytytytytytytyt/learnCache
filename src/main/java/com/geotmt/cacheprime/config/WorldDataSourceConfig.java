package com.geotmt.cacheprime.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zhangjiandong
 * @ClassName MasterDataSourceConfig
 * @Description TODO
 * @Date 2019/4/8
 * @Version 1.0
 **/
// 扫描 Mapper 接口并容器管理
@Configuration
@MapperScan(basePackages = WorldDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "worldSqlSessionFactory")
public class WorldDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.geotmt.cacheprime.dao.world";
    static final String MAPPER_LOCATION = "classpath:mapper/world/*.xml";

    @Value("${world.datasource.url}")
    private String url;

    @Value("${world.datasource.username}")
    private String user;

    @Value("${world.datasource.password}")
    private String password;

    @Value("${world.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "worldDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "worldTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "worldSqlSessionFactory")
    @Primary
    public SqlSessionFactory worldSqlSessionFactory(@Qualifier("worldDataSource") DataSource worldDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(worldDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(WorldDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}

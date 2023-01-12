package myBatis;

import java.io.FileReader;
import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private static final String CONFIG_FILE_NAME = "mybatis-config.xml";

    static {
        try {
            FileReader reader = new FileReader(CONFIG_FILE_NAME);
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch (IOException fileNotFoundException ) {
            Logger logger = LoggerFactory.getLogger(MyBatisConnectionFactory.class);
            logger.atInfo().log("Could not load config file ! Check if file exist and path is correct (best guess)" , fileNotFoundException);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

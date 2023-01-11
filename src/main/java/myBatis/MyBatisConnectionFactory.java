package myBatis;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private static final String CONFIG_FILE_NAME = "mybatis-config.xml";

    static {
        try {
            FileReader reader = new FileReader(CONFIG_FILE_NAME);
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

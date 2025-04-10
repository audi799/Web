package org.example.jdbcstudy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 마리아db dependencies 의존성 추가한 후 사용.
public class DatabaseUtils {
    private static final String CONNECTION_URL = "jdbc:mariadb://localhost:33067/";
    private static final String CONNECTION_USERNAME = "root";
    private static final String CONNECTION_PASSWORD = "test1234";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");  // 인자 문자열에 Driver 치면 인텔리센스 뜨고 선택하면됨.
        return DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
    }

    //객체화 막음
    private DatabaseUtils() {
        super();
    }
}

package com.student.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接管理类
 * 负责管理与MySQL数据库的连接，实现单例模式确保连接唯一性
 */
public class DatabaseConnection {
    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root"; // 修改为你的用户名
    private static final String PASSWORD = "password"; // 修改为你的密码

    private static Connection connection = null;

    /**
     * 私有构造方法，防止外部实例化
     */
    private DatabaseConnection() {}

    /**
     * 获取数据库连接（单例模式）
     * @return Connection 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // 加载MySQL驱动
                Class.forName("com.mysql.cj.jdbc.Driver");
                // 建立连接
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("数据库连接成功！");
            } catch (ClassNotFoundException e) {
                System.err.println("找不到MySQL驱动类: " + e.getMessage());
                throw new SQLException("数据库驱动加载失败", e);
            }
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("数据库连接已关闭");
            } catch (SQLException e) {
                System.err.println("关闭数据库连接时出错: " + e.getMessage());
            }
        }
    }
}
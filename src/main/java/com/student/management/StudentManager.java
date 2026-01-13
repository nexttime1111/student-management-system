package com.student.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生管理类
 * 负责学生信息的数据库操作
 */
public class StudentManager {

    /**
     * 添加学生信息到数据库
     * @param student 学生对象
     * @return 是否添加成功
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, gender, class_name, math_score, java_score) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender());
            pstmt.setString(3, student.getClassName());
            pstmt.setDouble(4, student.getMathScore());
            pstmt.setDouble(5, student.getJavaScore());

            // 执行插入
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("添加学生失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 根据ID查询学生信息
     * @param id 学生ID
     * @return 学生对象，如果未找到返回null
     */
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }

        } catch (SQLException e) {
            System.err.println("查询学生失败: " + e.getMessage());
        }

        return null;
    }

    /**
     * 显示所有学生信息
     * @return 学生列表
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            System.err.println("获取所有学生失败: " + e.getMessage());
        }

        return students;
    }

    /**
     * 计算学生各科目的平均分数
     * @return 包含各科目平均分的数组 [数学平均分, Java平均分]
     */
    public double[] calculateAverageScores() {
        double[] averages = new double[2];
        String sql = "SELECT AVG(math_score) as avg_math, AVG(java_score) as avg_java FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                averages[0] = rs.getDouble("avg_math");
                averages[1] = rs.getDouble("avg_java");
            }

        } catch (SQLException e) {
            System.err.println("计算平均分失败: " + e.getMessage());
        }

        return averages;
    }

    /**
     * 将ResultSet映射为Student对象
     * @param rs ResultSet对象
     * @return Student对象
     * @throws SQLException SQL异常
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setGender(rs.getString("gender"));
        student.setClassName(rs.getString("class_name"));
        student.setMathScore(rs.getDouble("math_score"));
        student.setJavaScore(rs.getDouble("java_score"));
        student.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        student.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return student;
    }
}
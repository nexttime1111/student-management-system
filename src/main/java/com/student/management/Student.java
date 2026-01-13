package com.student.management;

import java.time.LocalDateTime;

/**
 * 学生实体类
 * 对应数据库中的students表结构
 */
public class Student {
    // 成员属性
    private int id;              // 学生ID（数据库自动生成）
    private String name;         // 姓名
    private String gender;       // 性别
    private String className;    // 班级名称
    private double mathScore;    // 数学成绩
    private double javaScore;    // Java成绩
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间

    /**
     * 无参构造函数
     */
    public Student() {
        // 留空，用于查询结果映射
    }

    /**
     * 带参数的构造函数（用于添加学生）
     * @param name 姓名
     * @param gender 性别
     * @param className 班级名称
     * @param mathScore 数学成绩
     * @param javaScore Java成绩
     */
    public Student(String name, String gender, String className,
                   double mathScore, double javaScore) {
        this.name = name;
        this.gender = gender;
        this.className = className;
        this.mathScore = mathScore;
        this.javaScore = javaScore;
    }

    // Getter 和 Setter 方法

    public int getId() {
        return id;
    }

    /**
     * 注意：id由数据库自动生成，不应在外部设置
     * 此方法主要用于查询结果的映射
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        // 验证性别输入
        if ("男".equals(gender) || "女".equals(gender) || "其他".equals(gender)) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("性别必须是'男'、'女'或'其他'");
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getMathScore() {
        return mathScore;
    }

    public void setMathScore(double mathScore) {
        // 验证成绩范围
        if (mathScore >= 0 && mathScore <= 100) {
            this.mathScore = mathScore;
        } else {
            throw new IllegalArgumentException("数学成绩必须在0-100之间");
        }
    }

    public double getJavaScore() {
        return javaScore;
    }

    public void setJavaScore(double javaScore) {
        // 验证成绩范围
        if (javaScore >= 0 && javaScore <= 100) {
            this.javaScore = javaScore;
        } else {
            throw new IllegalArgumentException("Java成绩必须在0-100之间");
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 计算总成绩
     * @return 总成绩
     */
    public double getTotalScore() {
        return mathScore + javaScore;
    }

    /**
     * 计算平均成绩
     * @return 平均成绩
     */
    public double getAverageScore() {
        return getTotalScore() / 2;
    }

    /**
     * 重写toString方法，方便输出学生信息
     */
    @Override
    public String toString() {
        return String.format("ID: %-3d 姓名: %-6s 性别: %-3s 班级: %-15s " +
                        "数学: %-6.2f Java: %-6.2f 平均: %-6.2f",
                id, name, gender, className,
                mathScore, javaScore, getAverageScore());
    }
}
package com.student.management;

import java.util.List;
import java.util.Scanner;

/**
 * 主程序类
 * 提供命令行交互界面
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManager studentManager = new StudentManager();

    public static void main(String[] args) {
        System.out.println("=== 学生管理系统 ===");

        try {
            // 测试数据库连接
            DatabaseConnection.getConnection();

            boolean running = true;
            while (running) {
                showMenu();
                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        queryStudentById();
                        break;
                    case 3:
                        showAllStudents();
                        break;
                    case 4:
                        calculateAverageScores();
                        break;
                    case 5:
                        System.out.println("感谢使用，再见！");
                        running = false;
                        break;
                    default:
                        System.out.println("无效选择，请重新输入！");
                }

                System.out.println(); // 空行分隔
            }

        } catch (Exception e) {
            System.err.println("程序运行出错: " + e.getMessage());
        } finally {
            // 关闭数据库连接
            DatabaseConnection.closeConnection();
            // 关闭Scanner
            scanner.close();
        }
    }

    /**
     * 显示菜单
     */
    private static void showMenu() {
        System.out.println("请选择操作:");
        System.out.println("1. 添加学生");
        System.out.println("2. 根据ID查询学生");
        System.out.println("3. 显示所有学生");
        System.out.println("4. 计算平均分");
        System.out.println("5. 退出");
        System.out.print("请输入选择 (1-5): ");
    }

    /**
     * 获取用户选择
     * @return 用户选择的数字
     */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // 表示无效输入
        }
    }

    /**
     * 添加学生
     */
    private static void addStudent() {
        System.out.println("--- 添加学生 ---");

        try {
            System.out.print("请输入姓名: ");
            String name = scanner.nextLine().trim();

            System.out.print("请输入性别 (男/女/其他): ");
            String gender = scanner.nextLine().trim();

            System.out.print("请输入班级名称: ");
            String className = scanner.nextLine().trim();

            System.out.print("请输入数学成绩 (0-100): ");
            double mathScore = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("请输入Java成绩 (0-100): ");
            double javaScore = Double.parseDouble(scanner.nextLine().trim());

            // 创建学生对象
            Student student = new Student(name, gender, className, mathScore, javaScore);

            // 添加到数据库
            if (studentManager.addStudent(student)) {
                System.out.println("学生添加成功！");
            } else {
                System.out.println("学生添加失败！");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("输入错误: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("添加学生时出错: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询学生
     */
    private static void queryStudentById() {
        System.out.println("--- 根据ID查询学生 ---");

        try {
            System.out.print("请输入学生ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentManager.getStudentById(id);

            if (student != null) {
                System.out.println("查询结果:");
                System.out.println(student);
            } else {
                System.out.println("未找到ID为 " + id + " 的学生");
            }

        } catch (NumberFormatException e) {
            System.err.println("请输入有效的数字ID");
        } catch (Exception e) {
            System.err.println("查询学生时出错: " + e.getMessage());
        }
    }

    /**
     * 显示所有学生
     */
    private static void showAllStudents() {
        System.out.println("--- 所有学生信息 ---");

        List<Student> students = studentManager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("暂无学生信息");
        } else {
            System.out.println("共 " + students.size() + " 名学生:");
            System.out.println("================================================================================");
            for (Student student : students) {
                System.out.println(student);
            }
            System.out.println("================================================================================");
        }
    }

    /**
     * 计算平均分
     */
    private static void calculateAverageScores() {
        System.out.println("--- 计算平均分 ---");

        double[] averages = studentManager.calculateAverageScores();

        System.out.printf("数学平均分: %.2f\n", averages[0]);
        System.out.printf("Java平均分: %.2f\n", averages[1]);
        System.out.printf("总平均分: %.2f\n", (averages[0] + averages[1]) / 2);
    }
}
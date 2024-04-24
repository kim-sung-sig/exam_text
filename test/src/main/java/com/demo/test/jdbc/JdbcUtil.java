package com.demo.test.jdbc;
// DB를 연결하고 닫아주는 기능을 담당할 클래스

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	// 자주 사용하면서 데이터가 없고 기능만 모아두는 클래스의 메서드들은 static으로 만들면 편하다.

	// DB 연결을 얻어오는 메서드
	public static Connection getConnection() { // 오라클 전용
		Connection conn = null;
		String driverClass = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mysql";
		String user = "exam";
		String password = "123456";
		try {
			// 1. 드라이버를 로드한다.
			Class.forName(driverClass);
			// 2. 연결한다
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getConnection(String driverClass, String url, String user, String password) { // 모든 DB용
		Connection conn = null;
		try {
			// 1. 드라이버를 로드한다.
			Class.forName(driverClass);
			// 2. 연결한다
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// 닫는 메서드 추가
	public static void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 명령을 취소시키는 메서드 추가
	public static void rollback(Connection conn) {
		try {
			if (conn != null) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.demo.test.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.demo.test.dao.TestDAO;
import com.demo.test.jdbc.JdbcUtil;

public class TestService {
	private static TestService instance = new TestService();
	
	private TestService() {}
	
	public static TestService getInstance() {
        return instance;
    }
	
	
	public String selectToday() {
		String result = null;
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);
			
			result = TestDAO.getInstance().selectToday(conn);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		
		return result;
	}
}

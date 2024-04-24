package com.demo.test.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.test.jdbc.JdbcUtil;

public class TestDAO {
	private static TestDAO instance = new TestDAO();
	
	private TestDAO() {};
	
    public static TestDAO getInstance() {
        return instance;
    }
    
    
	public String selectToday(Connection conn) throws SQLException {
		String result = null;
		String sql = "SELECT CURRENT_TIMESTAMP()";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()) {
			result = rs.getString(1);
		}
		
		JdbcUtil.close(rs);
		JdbcUtil.close(stmt);
		return result;
	}
}

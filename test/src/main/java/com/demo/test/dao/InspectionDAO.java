package com.demo.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.demo.test.jdbc.JdbcUtil;
import com.demo.test.vo.InspectionVO;

public class InspectionDAO {
    private static InspectionDAO instance = new InspectionDAO();
	
	private InspectionDAO() {};
	
    public static InspectionDAO getInstance() {
        return instance;
    }

    public void insert(Connection conn, InspectionVO vo) throws SQLException {
        String sql = """
        	INSERT INTO inspection (stationName, stationCode, PM, issuedTime)
        	VALUES(?, ?, ?, ?)		
        """;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, vo.getStationName());
        pstmt.setString(2, vo.getStationCode());
        pstmt.setString(3, vo.getPM());
        pstmt.setString(4, vo.getIssuedTime());
        pstmt.executeUpdate();

        JdbcUtil.close(pstmt);
    }
    
    public void dropTable(Connection conn) throws SQLException{
    	String sql = "drop table if exists inspection";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        
        JdbcUtil.close(pstmt);
    }
    
    public void createTable(Connection conn) throws SQLException{
    	String sql = """
    			create table inspection(
					idx int primary key auto_increment,
					stationName varchar(16) not null,
					stationCode varchar(16) not null,
					PM varchar(4) not null,
					issuedTime varchar(20) not null
				)
    			""";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        
        JdbcUtil.close(pstmt);
    }
}

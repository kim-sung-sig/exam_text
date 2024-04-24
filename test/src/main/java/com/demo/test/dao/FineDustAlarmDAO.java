package com.demo.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.test.jdbc.JdbcUtil;
import com.demo.test.vo.FineDustAlarmVO;

public class FineDustAlarmDAO {
	private static FineDustAlarmDAO instance = new FineDustAlarmDAO();
	
	private FineDustAlarmDAO() {};
	
    public static FineDustAlarmDAO getInstance() {
        return instance;
    }
    
    public void insert(Connection conn, FineDustAlarmVO vo) throws SQLException {
        String sql = """
        	INSERT INTO find_dust_alarm (stationName, stationCode, alertLevel, issuedTime)
        	VALUES(?, ?, ?, ?)		
        """;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, vo.getStationName());
        pstmt.setString(2, vo.getStationCode());
        pstmt.setInt(3, vo.getAlertLevel());
        pstmt.setString(4, vo.getIssuedTime());
        pstmt.executeUpdate();

        JdbcUtil.close(pstmt);
    }
    
    public void dropTable(Connection conn) throws SQLException{
    	String sql = "drop table if exists find_dust_alarm";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        
        JdbcUtil.close(pstmt);
    }
    
    public void createTable(Connection conn) throws SQLException{
    	String sql = """
    			create table find_dust_alarm(
					idx int primary key auto_increment,
					stationName varchar(16) not null,
					stationCode varchar(16) not null,
					alertLevel int not null,
					issuedTime varchar(20) not null
				)
    			""";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        
        JdbcUtil.close(pstmt);
    }
    
    public List<FineDustAlarmVO> getList(Connection conn) throws SQLException{
        List<FineDustAlarmVO> list = null;
        String sql = "select * from find_dust_alarm order by idx";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
        	list = new ArrayList<>();
        	do {
        		FineDustAlarmVO vo = FineDustAlarmVO.builder()
		            	.stationName(rs.getString("stationName"))
		            	.stationCode(rs.getString("stationCode"))
		            	.alertLevel(rs.getInt("alertLevel"))
		            	.issuedTime(rs.getString("issuedTime")).build();
        		list.add(vo);
        	} while(rs.next());
        }
        JdbcUtil.close(rs);
        JdbcUtil.close(pstmt);
        return list;
    }
}

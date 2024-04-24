package com.demo.test.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.demo.test.dao.InspectionDAO;
import com.demo.test.jdbc.JdbcUtil;
import com.demo.test.vo.InspectionVO;

public class InspectionService {
    private static InspectionService instance = new InspectionService();
	
	private InspectionService() {}
	
	public static InspectionService getInstance() {
        return instance;
    }

	/**
	 * 저장
	 * @param vo
	 */
    public void insert(InspectionVO vo) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);
			
			InspectionDAO.getInstance().insert(conn, vo);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
	}
    
    /**
     * 테이블 초기화
     */
    public void initTable() {
    	Connection conn = null;
        try {
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            
            InspectionDAO.getInstance().dropTable(conn);
            InspectionDAO.getInstance().createTable(conn);
            
            conn.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn);
        }
    }
}

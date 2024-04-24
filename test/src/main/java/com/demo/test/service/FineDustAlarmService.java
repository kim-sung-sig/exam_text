package com.demo.test.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.demo.test.dao.FineDustAlarmDAO;
import com.demo.test.jdbc.JdbcUtil;
import com.demo.test.vo.FineDustAlarmVO;

public class FineDustAlarmService {
    private static FineDustAlarmService instance = new FineDustAlarmService();
	
	private FineDustAlarmService() {}
	
	public static FineDustAlarmService getInstance() {
        return instance;
    }

	
	/**
	 * 저장
	 * @param vo
	 */
    public void insert(FineDustAlarmVO vo) {
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);
			
			FineDustAlarmDAO.getInstance().insert(conn, vo); // 저장
			
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
            
            FineDustAlarmDAO.getInstance().dropTable(conn); // 테이블 drop
            FineDustAlarmDAO.getInstance().createTable(conn); // 테이블 create
            conn.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn);
        }
    }
    
    public List<FineDustAlarmVO> getList(){
    	List<FineDustAlarmVO> list = null;
    	Connection conn = null;
    	try {
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            list = FineDustAlarmDAO.getInstance().getList(conn);
            conn.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn);
        }
    	return list;
    }
}

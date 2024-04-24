package com.demo.test.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.demo.test.Init;
import com.demo.test.service.FineDustAlarmService;
import com.demo.test.vo.FineDustAlarmVO;

public class AlarmServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			System.out.println("서버를 초기화 합니다.");
			Init.init();
			System.out.println("초기화 완료 서버를 실행합니다.");
			List<FineDustAlarmVO> list = FineDustAlarmService.getInstance().getList(); // 뿌릴 목록
			server = new ServerSocket(10004);
			System.out.println("서버 실행");
			while(true) {
				System.out.println("접속을 기다립니다.....");
				Socket socket = server.accept();
				System.out.println(socket + "이 접속 완료!!!!");
				System.out.println("목록 리스트를 제공합니다.");
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				
				for(FineDustAlarmVO vo : list) {
					String alermStr = switch (vo.getAlertLevel()) {
						case 1 -> "초미세먼지 경보";
						case 2 -> "미세먼지 경보";
						case 3 -> "초미세먼지 주의보";
						case 4 -> "미세먼지 주의보";
	        			default -> "";
	        		};
					pw.write(vo.getIssuedTime() + "시 " + vo.getStationName() + "에서 " + alermStr + "발령\n");
					pw.flush();
					try {
						Thread.sleep(100); // 0.1초 마다 1개 씩 주기
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("목록주기 완료");
				pw.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

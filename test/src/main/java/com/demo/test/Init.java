package com.demo.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.demo.test.service.FineDustAlarmService;
import com.demo.test.service.InspectionService;
import com.demo.test.vo.DataVO;
import com.demo.test.vo.FineDustAlarmVO;
import com.demo.test.vo.InspectionVO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 데이터를 읽어 시간별로 정렬 후 DB에 저장 해보자
 * JDBC 사용
 */
public class Init {
	
	public static void main(String[] args) {
		Init.init();
	}
	
    public static void init() {
        String filePath = "src/main/resources/2023년3월_서울시_미세먼지.json";
        List<DataVO> data = readDataFromFile(filePath);	// 전체데이터
        Map<String, List<DataVO>> groupData = groupDataByCode(data); // 날짜 별로 그룹화한 데이터
        
        // 테이블 초기화
        FineDustAlarmService.getInstance().initTable();
        InspectionService.getInstance().initTable();
        
        List<DataVO> preList1 = new ArrayList<>();	// 2시간 전 데이터를 담을 변수
        List<DataVO> preList2 = new ArrayList<>();	// 1시간 전 데이터를 담을 변수
        
        
        for(List<DataVO> curList : groupData.values()) { // 날짜 별로 반복
        	if(preList1.isEmpty() || preList2.isEmpty()) { // 2시간 전 데이터가 없다면
        		preList1 = preList2;
        		preList2 = curList;
        		continue;
        	}
        	for(var i = 0; i < preList1.size(); i++) { // 지역별로 반복
        		
        		DataVO preData1 = preList1.get(i);	// 2시간전 데이터
        		DataVO preData2 = preList2.get(i);	// 1시간전 데이터
        		DataVO curData = curList.get(i);	// 현재 데이터
        		
        		var pre1_PM2_5 = preData1.getPM2_5();
            	var pre2_PM2_5 = preData2.getPM2_5();
            	var cur_PM2_5 = curData.getPM2_5();
            	
        		var pre1_PM10 = preData1.getPM10();
            	var pre2_PM10 = preData2.getPM10();
            	var cur_PM10 = curData.getPM10();
            	
            	// 기기 점검인가?
            	if(cur_PM2_5 == null) {
            		cur_PM2_5 = 0;
            		curData.setPM2_5(0);
            		saveInspection(1, curData); // 점검 저장
            	}
            	if(cur_PM10 == null) {
            		cur_PM10 = 0;
            		curData.setPM10(0);
            		saveInspection(2, curData); // 점검 저장
            	}
            	
            	int alerm = 0; // 경보 단계
				// 경보가 있나?
            	if(cur_PM2_5 >= 150 && pre1_PM2_5 >= 150 && pre2_PM2_5 >= 150) {
            		alerm = 1;
            	} else if(cur_PM10 >= 300 && pre1_PM10 >= 300 && pre2_PM10 >= 300) {
            		alerm = 2;
            	} else if(cur_PM2_5 >= 75 && pre1_PM2_5 >= 75 && pre2_PM2_5 >= 75) {
            		alerm = 3;
            	} else if(cur_PM10 >= 150 && pre1_PM10 >= 150 && pre2_PM10 >= 150) {
            		alerm = 4;
            	}
            	
            	if(alerm != 0) { // 경보가 있으면
            		saveFineDust(alerm, curData); // 발령 저장
            	}
        	}
        	preList1 = preList2; // 2시간전 데이터로
        	preList2 = curList; // 1시간전 데이터로
        }
    }
    
    /**
     * JSON 파일 읽기
     * @param filePath
     * @return
     */
    private static List<DataVO> readDataFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DataVO> dataList = null;
        DataVO[] dataArray = null;
        try {
        	dataArray = objectMapper.readValue(new File(filePath), DataVO[].class);
        	dataList = new ArrayList<>(List.of(dataArray));
        	Collections.sort(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    
    /**
     * 날짜별로 구룹화 해서 리스트에 담기 
     * key => 날짜 , value => list
     * @param dataList
     * @return
     */
    private static Map<String, List<DataVO>> groupDataByCode(List<DataVO> dataList) {
        Map<String, List<DataVO>> groupedData = new LinkedHashMap<>();
        for (DataVO data : dataList) {
            String date = data.getDate();
            groupedData.computeIfAbsent(date, k -> new ArrayList<>()).add(data);
        }
        return groupedData;
    }
    
    /**
     * 미세먼지 발령 저장
     * @param level
     * @param vo
     */
    private static void saveFineDust(int level, DataVO vo) {
    	FineDustAlarmVO fineDustAlarmVO = FineDustAlarmVO.builder()
														 .stationName(vo.getLocation())
														 .stationCode(vo.getLocCode())
														 .alertLevel(level)
														 .issuedTime(vo.getDate()).build();
        FineDustAlarmService.getInstance().insert(fineDustAlarmVO);
    }
    
    /**
     * 점검 저장
     * @param level
     * @param vo
     */
    private static void saveInspection(int level, DataVO vo) {
    	String levelStr = switch (level) {
	        case 1 -> "2_5";
	        case 2 -> "10";
	        default -> "";
	    };
    	InspectionVO inspectionVO = InspectionVO.builder()
												.stationName(vo.getLocation())
												.stationCode(vo.getLocCode())
												.PM(levelStr)
												.issuedTime(vo.getDate()).build();
		InspectionService.getInstance().insert(inspectionVO);
    }
}
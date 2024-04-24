package com.demo.test.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 경보 발령
 */
@Data
public class FineDustAlarmVO {
	private int idx;
	private String stationName;			// 측정소
	private String stationCode;			// 측정소코드
	private int alertLevel;				// 경보단계
	private String issuedTime;			// 발생시간

	@Builder
	public FineDustAlarmVO(int idx, String stationName, String stationCode, int alertLevel, String issuedTime){
		this.idx = idx;
		this.stationName = stationName;
		this.stationCode = stationCode;
		this.alertLevel = alertLevel;
		this.issuedTime = issuedTime;
	}
}

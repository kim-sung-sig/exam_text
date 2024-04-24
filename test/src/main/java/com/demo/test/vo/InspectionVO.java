package com.demo.test.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 점검
 */
@Data
public class InspectionVO {
	private int idx;
	private String stationName;			// 측정소
	private String stationCode;			// 측정소코드
	private String PM;    				// 기준
	private String issuedTime;			// 발생시간
	
	@Builder
	public InspectionVO(String stationName, String stationCode, String PM, String issuedTime) {
		this.stationName = stationName;
		this.stationCode = stationCode;
		this.PM = PM;
		this.issuedTime = issuedTime;
	}
}

package com.demo.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DataVO implements Comparable<DataVO> {
	@JsonProperty("날짜")
    private String date;

    @JsonProperty("측정소명")
    private String location;

    @JsonProperty("측정소코드")
    private String locCode;

    @JsonProperty("PM10")
    private Integer PM10;

    @JsonProperty("PM2.5")
    private Integer PM2_5;

	@Override
	public int compareTo(DataVO o) {
		return this.location.compareTo(o.getLocation());
	}
}

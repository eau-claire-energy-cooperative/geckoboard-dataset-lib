package com.ecec.rweber.geckoboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DataType {

	@JsonProperty("date")
	DATE,
	@JsonProperty("datetime")
	DATETIME,
	@JsonProperty("number")
	NUMBER,
	@JsonProperty("percentage")
	PERCENTAGE,
	@JsonProperty("string")
	STRING,
	@JsonProperty("money")
	MONEY
}

package com.ef;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.ef.dto.BlockedIp;
import com.ef.enums.Parameter;

public class BlockedIpMapper implements RowMapper<BlockedIp> {

	private Map<Parameter, String> jobParameters;

	@Override
	public BlockedIp mapRow(ResultSet rs, int rowNum) throws SQLException {

		BlockedIp bIp = new BlockedIp();
		bIp.setIp(rs.getString("ip"));
		bIp.setComment(getComment());

		System.out.println("BlockedIp :: " + bIp.getIp());

		return bIp;
	}

	
	
	/**
	 * @return the jobParameters
	 */
	public Map<Parameter, String> getJobParameters() {
		return jobParameters;
	}



	/**
	 * @param jobParameters the jobParameters to set
	 */
	public void setJobParameters(Map<Parameter, String> jobParameters) {
		this.jobParameters = jobParameters;
	}



	private String getComment() {

		return "Exceed " + jobParameters.get(Parameter.threshold) + " request between " + jobParameters.get(Parameter.startDate)
				+ " and " + jobParameters.get(Parameter.endDate);
	}

}

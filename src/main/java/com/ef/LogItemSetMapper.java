package com.ef;

import java.text.ParseException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.ef.dto.LogItem;
import com.ef.util.DateUtil;

public class LogItemSetMapper implements FieldSetMapper<LogItem> {

	@Override
	public LogItem mapFieldSet(FieldSet fieldSet) throws BindException {
		
		LogItem item = new LogItem();
		
		try {
			item.setDate(DateUtil.parseDate(fieldSet.readString(0)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		item.setIp(fieldSet.readString(1));
		item.setRequest(fieldSet.readString(2));
		item.setStatus(fieldSet.readString(3));
		item.setUserAgent(fieldSet.readString(4));
		
		return item;
	}

}

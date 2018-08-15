package com.baihoo.blog.convertor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
/**
 * 自定义日期转换格式
 * @author Administrator
 *
 */
@Component
public class CustomDateConverter implements  Converter<String, Date>{
	@Nullable  //source 不能是空的
	@Override
	public Date convert(String source) {
		try {
			if(source !=null && !"".equals(source.trim())) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				return simpleDateFormat.parse(source);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

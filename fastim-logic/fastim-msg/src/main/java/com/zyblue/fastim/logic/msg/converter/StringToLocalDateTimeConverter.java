package com.zyblue.fastim.logic.msg.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author will
 * @date 2020/12/23 11:25
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private final static Logger log = LoggerFactory.getLogger(StringToLocalDateTimeConverter.class);

    public StringToLocalDateTimeConverter() {
    }

    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String dateStr) {
        LocalDateTime parse;
        try {
            parse = LocalDateTime.parse(dateStr, DF);
        }catch (Exception e){
            log.error("error:", e);
            throw new RuntimeException("日期解析错误");
        }
        log.info("LocalDateTime:{}", parse);
        return parse;
    }
}

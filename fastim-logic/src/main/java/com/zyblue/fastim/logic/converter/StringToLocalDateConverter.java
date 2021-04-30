package com.zyblue.fastim.logic.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author will
 * @date 2020/12/22 17:27
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private final static Logger log = LoggerFactory.getLogger(StringToLocalDateConverter.class);

    public StringToLocalDateConverter() {
    }

    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String dateStr) {
        LocalDate parse;
        try {
            parse = LocalDate.parse(dateStr, DF);
        }catch (Exception e){
            log.error("error:", e);
            throw new RuntimeException("日期解析错误");
        }
        log.info("LocalDateTime:{}", parse);
        return parse;
    }
}

package com.zyblue.fastim.common.pojo.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author will
 * @date 2020/12/23 15:47
 */
public class User implements Serializable {
    public String name;
    public LocalDate birthdayFirst;
    public LocalDateTime birthdayEnd;
    public Boolean boy;
    public Integer number1;
    public BigDecimal number2;
    public Float number3;
    public Long number4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdayFirst() {
        return birthdayFirst;
    }

    public void setBirthdayFirst(LocalDate birthdayFirst) {
        this.birthdayFirst = birthdayFirst;
    }

    public LocalDateTime getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(LocalDateTime birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public Boolean getBoy() {
        return boy;
    }

    public void setBoy(Boolean boy) {
        this.boy = boy;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public BigDecimal getNumber2() {
        return number2;
    }

    public void setNumber2(BigDecimal number2) {
        this.number2 = number2;
    }

    public Float getNumber3() {
        return number3;
    }

    public void setNumber3(Float number3) {
        this.number3 = number3;
    }

    public Long getNumber4() {
        return number4;
    }

    public void setNumber4(Long number4) {
        this.number4 = number4;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthdayFirst=" + birthdayFirst +
                ", birthdayEnd=" + birthdayEnd +
                ", boy=" + boy +
                ", number1=" + number1 +
                ", number2=" + number2 +
                ", number3=" + number3 +
                ", number4=" + number4 +
                '}';
    }
}

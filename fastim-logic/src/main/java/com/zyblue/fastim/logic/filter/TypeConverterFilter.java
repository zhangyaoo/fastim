package com.zyblue.fastim.logic.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static org.apache.dubbo.common.constants.CommonConstants.$INVOKE;
import static org.apache.dubbo.common.constants.CommonConstants.$INVOKE_ASYNC;

/**
 * 类型转换
 *
 * @author will
 * @date 2020/12/22 17:10
 */
@Activate(group = CommonConstants.PROVIDER, order = -20001)
public class TypeConverterFilter implements Filter {

    private static final String DEFAULT_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_LOCAL_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 是否是泛化调用（网关调用）
     */
    private boolean isGeneric(Invoker<?> invoker, Invocation inv){
        return (inv.getMethodName().equals($INVOKE) || inv.getMethodName().equals($INVOKE_ASYNC))
                && inv.getArguments() != null
                && inv.getArguments().length == 3
                && !GenericService.class.isAssignableFrom(invoker.getInterface());
    }

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, getDeserializerLocalDateTime());
        javaTimeModule.addDeserializer(LocalDate.class, getDeserializerLocalDate());
        MAPPER.registerModule(javaTimeModule);
        // 只支持下划线转驼峰
        MAPPER.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        if (isGeneric(invoker, inv)){
            String methodName = ((String)inv.getArguments()[0]).trim();
            Object[] args = (Object[]) inv.getArguments()[2];
            Method method;
            try {
                method = ReflectUtils.findMethodByMethodName(invoker.getInterface(), methodName);
            }catch (Exception e){
                throw new RpcException("get method failed", e);
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                Class<?> parameterType = parameterTypes[i];
                newArgs[i] = MAPPER.convertValue(arg, parameterType);
            }

            // 使用具体methodName不走GenericFilter
            RpcInvocation rpcInvocation = new RpcInvocation(methodName, invoker.getInterface().getName(), parameterTypes,
                    newArgs, inv.getAttachments(), inv.getInvoker(), inv.getAttributes());
            rpcInvocation.setTargetServiceUniqueName(inv.getTargetServiceUniqueName());
            return invoker.invoke(rpcInvocation);
        }
        return invoker.invoke(inv);
    }

    private static JsonDeserializer<LocalDateTime> getDeserializerLocalDateTime(){
        return new JsonDeserializer<LocalDateTime>(){
            @Override
            public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
                String pattern = getLocalDateTimeStringPattern(p.getValueAsString());
                return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
            }
        };
    }

    private static JsonDeserializer<LocalDate> getDeserializerLocalDate(){
        return new JsonDeserializer<LocalDate>(){
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
                String pattern = getLocalDateStringPattern(p.getValueAsString());
                return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
            }
        };
    }



    private static String getLocalDateTimeStringPattern(String pattern){


        boolean matches1 = Pattern.matches("^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\\2(?:29))\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
                pattern);
        if(matches1){
            return "yyyy-MM-dd HH:mm:ss";
        }

        boolean matches2 = Pattern.matches("^(?:(?!0000)[0-9]{4}([/]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([/]?)0?2\\2(?:29))\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
                pattern);
        if(matches2){
            return "yyyy/MM/dd HH:mm:ss";
        }

        boolean matches3 = Pattern.matches("^(?:(?!0000)[0-9]{4}()(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)()0?2\\2(?:29))\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
                pattern);
        if(matches3){
            return "yyyyMMdd HH:mm:ss";
        }

        boolean matches4 = Pattern.matches("^(?:(?!0000)[0-9]{4}([.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([.]?)0?2\\2(?:29))\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
                pattern);
        if(matches4){
            return "yyyy.MM.dd HH:mm:ss";
        }

        return DEFAULT_LOCAL_DATE_TIME_PATTERN;
    }

    private static String getLocalDateStringPattern(String pattern){
        boolean matches1 = Pattern.matches("^(?:(?!0000)[0-9]{4}()(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)()0?2\\2(?:29))$",
                pattern);
        if(matches1){
            return "yyyyMMdd";
        }

        boolean matches2 = Pattern.matches("^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\\2(?:29))$",
                pattern);
        if(matches2){
            return "yyyy-MM-dd";
        }

        boolean matches3 = Pattern.matches("^(?:(?!0000)[0-9]{4}([.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([.]?)0?2\\2(?:29))$",
                pattern);
        if(matches3){
            return "yyyy.MM.dd";
        }

        boolean matches4 = Pattern.matches("^(?:(?!0000)[0-9]{4}([/]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([/]?)0?2\\2(?:29))$",
                pattern);
        if(matches4){
            return "yyyy/MM/dd";
        }

        return DEFAULT_LOCAL_DATE_PATTERN;
    }

    public static void main(String[] args) {
        // “-”、“/”、“.” “没有” 其中之一
        boolean matches1 = Pattern.matches("^(?:(?!0000)[0-9]{4}()(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)()0?2\\2(?:29))$",
                "20201010");
        System.out.println(matches1);

        boolean matches2 = Pattern.matches("^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\\2(?:29))$",
                "2020-10-10");
        System.out.println(matches2);

        boolean matches3 = Pattern.matches("^(?:(?!0000)[0-9]{4}([.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([.]?)0?2\\2(?:29))$",
                "2020.10.10");
        System.out.println(matches3);

        boolean matches4 = Pattern.matches("^(?:(?!0000)[0-9]{4}([/]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([/]?)0?2\\2(?:29))$",
                "2020/10/10");
        System.out.println(matches4);

        boolean matches0 = Pattern.matches("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
                "2020/10/10 12:22:00");
        System.out.println(matches0);
    }
}

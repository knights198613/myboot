package com.jiangwei.springboottest.myboot.config.redis;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: weijiang
 * @date: 2021/5/10
 * @description: JackSon JSON 序列化工具
 **/

@Slf4j
@Component
public class JacksonCodec implements Serializable {
    private static final long serialVersionUID = -704969341620802592L;

    private ObjectMapper objMapper;


    public JacksonCodec() {
        this(new ObjectMapper());
    }

    public JacksonCodec(ObjectMapper objectMapper) {
        this.objMapper = objectMapper.copy();
        initConfig(objMapper);
        //initHandlerType(objMapper);
    }

    private void initConfig(ObjectMapper objMapper) {
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objMapper.setVisibility(objMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // warm up
        try {
            byte[] s = objMapper.writeValueAsBytes(1);
            objMapper.readValue(s, Object.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void initHandlerType(ObjectMapper objMapper) {
        TypeResolverBuilder<?> mapTyper = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {
            public boolean useForType(JavaType t) {
                switch (_appliesFor) {
                    case NON_CONCRETE_AND_ARRAYS:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                    case OBJECT_AND_NON_CONCRETE:
                        return (t.getRawClass() == Object.class) || !t.isConcrete();
                    case NON_FINAL:
                        while (t.isArrayType()) {
                            t = t.getContentType();
                        }
                        if (t.getRawClass() == Long.class) {
                            return true;
                        }
                        if (t.getRawClass() == XMLGregorianCalendar.class) {
                            return false;
                        }
                        return !t.isFinal(); // includes Object.class
                    default:
                        return (t.getRawClass() == Object.class);
                }
            }
        };
        mapTyper.init(JsonTypeInfo.Id.CLASS, null);
        mapTyper.inclusion(JsonTypeInfo.As.PROPERTY);
        objMapper.setDefaultTyping(mapTyper);
        objMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // warm up
        try {
            byte[] s = objMapper.writeValueAsBytes(1);
            objMapper.readValue(s, Object.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 将 Java object 序列化为String JSON
     * @param source
     * @return
     */
    public String encodeVal(Object source) {
        try {
           return objMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error("_JacksonCodec_encodeVal_ERROR||source={}", JSON.toJSONString(source), e);

        }
        return null;
    }

    /**
     * 将String JSON 反序列化成 Java Object
     * @param json
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V decodeVal(String json, TypeReference<V> typeReference) {
        V val = null;
        try {
            if(StringUtils.isNotEmpty(json)) {
                if(null != typeReference) {
                    val = objMapper.readValue(json, typeReference);
                }
            }else {
                return null;
            }
        } catch (Exception e) {
            log.error("_JacksonCodec_decodeVal_ERROR||json={}||type={}", json, typeReference, e);
        }
        return val;
    }


}

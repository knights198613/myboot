package com.jiangwei.springboottest.myboot.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.FlashGoodCheapDTO;
import org.junit.Test;

/**
 * @author: weijiang
 * @date: 2021/4/15
 * @description:
 **/
public class JSONTest extends MybootApplicationTests {


    @Test
    public void testJson() {
        String jsonStr = "{\n" +
                "      \"goodsId\": 3042562,\n" +
                "      \"price\": 99,\n" +
                "      \"goodsName\": \"胡豆角 500g±30g/份\",\n" +
                "      \"shortName\": \"1\",\n" +
                "      \"startTime\": 1618412400,\n" +
                "      \"linePrice\": 3.99\n" +
                "    }";

        ObjectMapper mapper = new ObjectMapper();
        FlashGoodCheapDTO dto = null;
        try {
            dto = mapper.readValue(jsonStr, FlashGoodCheapDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(dto.toString());
    }
}

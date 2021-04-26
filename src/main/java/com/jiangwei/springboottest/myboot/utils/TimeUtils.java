package com.jiangwei.springboottest.myboot.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: weijiang
 * @date: 2021/4/26
 * @description:
 **/
public class TimeUtils {

    private static final String DEFAULT_DATE_STRING_PATTERN = "YY-MM-dd HH:MM:ss";

    public static String getCurrentDateString(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_DATE_STRING_PATTERN);
        return sdf.format(date);
    }
}

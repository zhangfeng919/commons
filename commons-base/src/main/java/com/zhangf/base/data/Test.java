package com.zhangf.base.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;


public class Test {
	private static String genHeader(String head) {    
        String[] header = new String[3];    
        header[0] = StringUtils.repeat("*", 50);    
        header[1] = StringUtils.center("  " + head + "  ", 50, "^O^");    
        header[2] = header[0];    
        return StringUtils.join(header, "\n");    
    } 

	public static void main(String[] args) {
		System.out.println(genHeader("DateFormatUtilsDemo"));    
        System.out.println("格式化日期输出.");    
        System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));    
     
        System.out.println("秒表.");    
        StopWatch sw = new StopWatch();    
        sw.start();    
     
        for (Iterator iterator = DateUtils.iterator(new Date(), DateUtils.RANGE_WEEK_CENTER); iterator.hasNext();) {    
            Calendar cal = (Calendar) iterator.next();    
            System.out.println(DateFormatUtils.format(cal.getTime(), "yy-MM-dd HH:mm"));    
        }    
     
        sw.stop();    
        System.out.println("秒表计时:" + sw.getTime());     
	}


   
} 
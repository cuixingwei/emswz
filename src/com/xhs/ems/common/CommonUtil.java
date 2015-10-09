package com.xhs.ems.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CommonUtil {
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getSystemTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || text.length() == 0 || text.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 读取文件内容，返回字符串
	 * @param file
	 * @return
	 */
	public static String readContentFromFile(File file) {
		StringBuilder builder = new StringBuilder();
		if (file.exists() && file.isFile()) {
			try {
				InputStreamReader reader = new InputStreamReader(
						new FileInputStream(file), "utf-8");
				BufferedReader bufferedReader = new BufferedReader(reader);
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					builder.append(temp);
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
	
	/**
	 * 计算百分比
	 * @author CUIXINGWEI
	 * @param total
	 * @param portion
	 * @return
	 */
	public static String calculateRate(Integer total, Object portion) {
		Integer numerator = Integer.parseInt(String.valueOf(portion));
		
		NumberFormat percent=NumberFormat.getPercentInstance();
		percent.setMinimumFractionDigits(2);
		percent.setMaximumFractionDigits(2);
		
		return percent.format(total>0?(float)numerator/total:0);
	}
	
	/**
	 * 把秒格式化成几时几分几秒
	 * @author CUIXINGWEI
	 * @param second
	 * @return
	 */
	public static String formatSecond(Object second){
		 String  html="0秒";
         if(second!=null){
             int s=Integer.parseInt(second.toString());
             String format;
             Object[] array;
             Integer hours =(int) (s/(60*60));
             Integer minutes = (int) (s/60-hours*60);
             Integer seconds = (int) (s-minutes*60-hours*60*60);
             if(hours>0){
                 format="%1$,d时%2$,d分%3$,d秒";
                 array=new Object[]{hours,minutes,seconds};
             }else if(minutes>0){
                 format="%1$,d分%2$,d秒";
                 array=new Object[]{minutes,seconds};
             }else{
                 format="%1$,d秒";
                 array=new Object[]{seconds};
             }
             html= String.format(format, array);
         }
		
		return html;
		
	}
	
	
}

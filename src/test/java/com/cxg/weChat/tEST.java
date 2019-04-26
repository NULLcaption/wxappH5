package com.cxg.weChat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/3/15.
 */

public class tEST {
    public static void main(String[] arg0) throws ParseException {
        String time = "2019年04月16日";
		String time1 = "2019年04月17日";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    Date utilDate = sdf.parse(time);
	    System.out.println(utilDate);
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
	    Date utilDate1 = sdf1.parse(time1);
	    System.out.println(utilDate1);
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        Integer year = Integer.parseInt(strNow[0]);
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        String dateNow = year+"年"+month+"月"+day;
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");
        Date utilDate2 = sdf3.parse(dateNow);
        long a = utilDate.getTime();
	    long b = utilDate1.getTime();
        long c = utilDate2.getTime();
        if (c < a) {
            System.out.println("11");
        } else if (c > b) {
            System.out.println("22");
        } else {
            System.out.println("33");
        }
    }
}

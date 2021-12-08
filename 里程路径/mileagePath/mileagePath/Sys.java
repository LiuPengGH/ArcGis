package cm.mileagePath;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Sys {
	private final static Logger Logger=LoggerFactory.getLogger(Sys.class);
    public static int interval = 1000 * 60 * 60 * 3;

    public static String getYearMonth() {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);
        return tmp + mm;
    }

    public static String convertL2Date(long longDt) {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date(longDt));
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int day = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (day < 10)
            dd += "0";
        dd += String.valueOf(day);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return tmp + "-" + mm + "-" + dd + " " + hh + ":" + minute;
    }

    public static String convertL2YYMMDDHHMM(long longDt) {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date(longDt));
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int day = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (day < 10)
            dd += "0";
        dd += String.valueOf(day);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return tmp + mm + dd + hh + minute;
    }

    public static String getCtime() {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return tmp + mm + dd + hh + minute + ss;
    }

    public static String getCtime2() {//��������2008-3-13 15:51:23
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        dd += String.valueOf(d);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        ss += String.valueOf(s);

        return tmp + "-" + mm + "-" + dd + " " + hh + ":" + minute + ":" + ss;
    }

    public static String getDate(Date mydate) {//��������2008-3-13 15:51:23
        Calendar dt = Calendar.getInstance();
        dt.setTime(mydate);
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        dd += String.valueOf(d);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        ss += String.valueOf(s);

        return tmp + "-" + mm + "-" + dd;
    }

    public static String getCtime3() {//��������2008-3-13 15:51
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        dd += String.valueOf(d);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        ss += String.valueOf(s);
        if (mm.length() <= 1) {
            mm = "0" + mm;
        }
        if (dd.length() <= 1) {
            dd = "0" + dd;
        }
        if (minute.length() <= 1) {
            minute = "0" + minute;
        }

        return tmp + "-" + mm + "-" + dd + " " + hh + ":" + minute;
    }

    public static String getDateLate(int days) {//��ȡ��ǰ����days�������
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        dt.add(Calendar.DAY_OF_MONTH, days);
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);

        return tmp + mm + dd;
    }

    public static String getYYYYMMDD() {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);

        return tmp + mm + dd;
    }

    public static String getDateLateMMdd(int days) {//��ȡ��ǰ����days�������
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        dt.add(Calendar.DAY_OF_MONTH, days);
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);

        return mm + dd;
    }

    public static String getHHMMSS() {
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return hh + ":" + minute + ":" + ss;
    }

    public static String convertDtFormat(String strDt) {
        StringTokenizer st = new StringTokenizer(strDt, "-");
        String[] actions = new String[5];
        int i = 0;
        while (st.hasMoreElements() && i < 5) {
            actions[i++] = st.nextToken().trim();
        }
        if (actions.length < 3)
            return Sys.getYYYYMMDD();

        if (Integer.valueOf(actions[1]).intValue() < 10)
            actions[1] = "0" + actions[1];
        if (Integer.valueOf(actions[2]).intValue() < 10)
            actions[2] = "0" + actions[2];

        return actions[0] + actions[1] + actions[2];
    }

    public static String cCheckMobile(String mobile) {
        String flag = "ok";
        int slen = mobile.length();
        if (slen != 11) {
            return "�û�����λ���";
        }
        if (!Sys.isDigit(mobile)) {
            return "�û����뺬�����ֵ��ַ�";
        }
        //		String str3 = mobile.substring(0,3);
        if (mobile.startsWith("130") == true
                || mobile.startsWith("131") == true
                || mobile.startsWith("132") == true
                || mobile.startsWith("155") == true
                || mobile.startsWith("156") == true
                || mobile.startsWith("186") == true) {//�����ǰ��λ
            return "ok";
        } else {
            return "�ú��벻����ͨ�ֻ��û�";
        }
    }
    
    public static String cCheckUnicomMobile(String mobile) {
        String flag = "ok";
        int slen = mobile.length();
        if (slen != 11) {
            return "�û�����λ���";
        }
        if (!Sys.isDigit(mobile)) {
            return "�û����뺬�����ֵ��ַ�";
        }
        //      String str3 = mobile.substring(0,3);
        if (mobile.startsWith("130") == true
                || mobile.startsWith("131") == true
                || mobile.startsWith("132") == true
                || mobile.startsWith("155") == true
                || mobile.startsWith("156") == true
                || mobile.startsWith("185") == true
                || mobile.startsWith("186") == true) {//�����ǰ��λ
            return "ok";
        } else {
            return "�ú��벻����ͨ�ֻ��û�";
        }
    }

    public static boolean isNull(String str) {//�ж��Ƿ�Ϊ��ֵ
        //		System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {//�ж��Ƿ�Ϊ��ֵ
        //      System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null")) {
            return false;
        }
        return true;
    }
    
    public static String isCheckNull(String str) {//�ж��Ƿ�Ϊ��ֵ
        //      System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null") || str.equals("NULL")) {
            return "";
        }
        str=str.replaceAll("NULL", "").replaceAll("null", "");
        return str;
    }

    public static String checkPayDay(String stock1) {
        String flag = "ok";
        //System.out.println(stock1);
        int slen = stock1.length();
        if (slen != 8) {
            return "����λ���:" + stock1 + "</b>";
        }
        if (Integer.parseInt(stock1.substring(4, 6)) > 12) {
            return "�·���������:" + stock1 + "</b>";
        }
        if (Integer.parseInt(stock1.substring(6, 8)) > 31
                || stock1.substring(4, 8).equals("0230")
                || stock1.substring(4, 8).equals("0231")
                || stock1.substring(4, 8).equals("0431")
                || stock1.substring(4, 8).equals("0631")
                || stock1.substring(4, 8).equals("0931")
                || stock1.substring(4, 8).equals("1131"))
            return "������������:" + stock1 + "</b>";

        for (int j = 0; j < slen; j++) {
            String word = stock1.substring(j, j + 1);
            if (word.equals("0") == true || word.equals("1") == true
                    || word.equals("2") == true || word.equals("3") == true
                    || word.equals("4") == true || word.equals("5") == true
                    || word.equals("6") == true || word.equals("7") == true
                    || word.equals("8") == true || word.equals("9") == true) {
                //flag="ok";
            } else {
                return "���ں������ֵ��ַ�";
            }
        }
        return "ok";
    }

    public static String getTimeStr(String timestr) {//����ʱ���ʽ
        if (timestr == null) {
            timestr = "";
        }
        if (timestr.length() == 8) {//�����8λ��ʱ��
            return timestr.substring(0, 4) + "-" + timestr.substring(4, 6)
                    + "-" + timestr.substring(6);
        } else if (timestr.length() == 14) {
            return timestr.substring(0, 4) + "-" + timestr.substring(4, 6)
                    + "-" + timestr.substring(6, 8) + " "
                    + timestr.substring(8, 10) + ":"
                    + timestr.substring(10, 12) + ":"
                    + timestr.substring(12, 14);
        } else if (timestr.length() == 12) {//�����12λʱ��
            return timestr.substring(0, 4) + "-" + timestr.substring(4, 6)
                    + "-" + timestr.substring(6, 8) + " "
                    + timestr.substring(8, 10) + ":"
                    + timestr.substring(10, 12);
        } else {
            return timestr;
        }
    }

    public static boolean isDigit(String Str) {//�ж��Ƿ�Ϊ����
        for (int i = 0; i < Str.length(); i++) {
            if (!Character.isDigit(Str.charAt(i)))
                return false;

        }
        return true;

    }
    //���6λ�����
    public static String getRand() {
        //String ASCII = "AB2C3D4E5F6G7H8J9KLM2N34P5R6S7T8U9VWX2Y3Z4A5B6C7D8E9F";
        String ASCII = "1234567890";
        Date d = new Date();
        long lseed = d.getTime();
        java.util.Random r = new Random(lseed); // �����������
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int index = r.nextInt(100);
            index = index % ASCII.length();
            char c = ASCII.charAt(index);
            str.append(c); // ����������
        }
        String rand = str.toString();
        return rand;

    }
    //���numλ�����
    public static String getRand(int num) {
        if(num<= 0){num = 6;}
        //String ASCII = "AB2C3D4E5F6G7H8J9KLM2N34P5R6S7T8U9VWX2Y3Z4A5B6C7D8E9F";
        String ASCII = "1234567890";
        Date d = new Date();
        long lseed = d.getTime();
        java.util.Random r = new Random(lseed); // �����������
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int index = r.nextInt(100);
            index = index % ASCII.length();
            char c = ASCII.charAt(index);
            str.append(c); // ����������
        }
        String rand = str.toString();
        return rand;

    }
    
    //���numλ�����
    public static String getRand2(int num) {
        if(num<= 0){num = 6;}
        //String ASCII = "AB2C3D4E5F6G7H8J9KLM2N34P5R6S7T8U9VWX2Y3Z4A5B6C7D8E9F";
        String ASCII = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Date d = new Date();
        long lseed = d.getTime();
        java.util.Random r = new Random(lseed); // �����������
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int index = r.nextInt(100);
            index = index % ASCII.length();
            char c = ASCII.charAt(index);
            str.append(c); // ����������
        }
        String rand = str.toString();
        return rand;

    }

    public static java.util.Date TransStrToDate(String timestr) {//��14λ�ַ�ʱ��ת��Date����
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        java.util.Date mydate = new java.util.Date();
        try {
            mydate = df.parse(timestr);
        } catch (Exception ex) {
            ;
        }
        return mydate;
    }

    public static String str2YMD(String strdt) {
        if (null == strdt)
            return "";
        if (strdt.length() < 14)
            return strdt;
        int[] formatlen = { 4, 2, 2, 2, 2, 2 };
        String[] unit = { "��", "��", "��", "ʱ", "��", "��" };
        String tmp = strdt, result = "";
        if (tmp.length() > 14)
            tmp = tmp.substring(0, 14);
        int len = tmp.length();
        for (int i = 0; i < formatlen.length && 0 < len; i++) {
            int index = formatlen[i] <= len ? formatlen[i] : len;
            String dt = tmp.substring(0, index);
            result += dt + unit[i];
            tmp = tmp.substring(index, tmp.length());
            len = tmp.length();
        }
        return result;
    }

    public static String str2YM(String strdt) {
        if (null == strdt)
            return "";
        if (strdt.length() < 6)
            return strdt;
        int[] formatlen = { 4, 2 };
        String[] unit = { "��", "��" };
        String tmp = strdt, result = "";
        if (tmp.length() > 6)
            tmp = tmp.substring(0, 6);
        int len = tmp.length();
        for (int i = 0; i < formatlen.length && 0 < len; i++) {
            int index = formatlen[i] <= len ? formatlen[i] : len;
            String dt = tmp.substring(0, index);
            result += dt + unit[i];
            tmp = tmp.substring(index, tmp.length());
            len = tmp.length();
        }
        return result;
    }

    public static String str2date(String strdt) {
        if (null == strdt)
            return "";
        if (strdt.length() < 14)
            return strdt;
        int[] formatlen = { 4, 2, 2, 2, 2, 2 };
        String[] unit = { "/", "/", " ", ":", ":", " " };
        String tmp = strdt, result = "";
        if (tmp.length() > 14)
            tmp = tmp.substring(0, 14);
        int len = tmp.length();
        for (int i = 0; i < formatlen.length && 0 < len; i++) {
            int index = formatlen[i] <= len ? formatlen[i] : len;
            String dt = tmp.substring(0, index);
            result += dt + unit[i];
            tmp = tmp.substring(index, tmp.length());
            len = tmp.length();
        }
        return result;
    }

    static public String NullAs(String strSrc, String stead) {

        return (null == strSrc || strSrc.equals("") || strSrc
                .equalsIgnoreCase("NULL")) ? stead : strSrc;
    }

    public static String DateAdd(int field, int amonnt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(field, amonnt);
        int y = cal.get(Calendar.YEAR);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH) + 1;
        String yy = String.valueOf(y);
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);
        return yy + mm + dd;
    }

    public static String DateFristDay(int field, int amonnt) {
        Calendar cal = Calendar.getInstance();
        Calendar calPayDay = (Calendar) cal.clone();
        Calendar calThisMonth = (Calendar) cal.clone();
        calThisMonth.setTime(new Date());
        int tm = calThisMonth.get(Calendar.MONTH) + 1;
        //      System.out.print(tm);
        calPayDay.setTime(new Date());
        calPayDay.add(field, amonnt);
        int y = calPayDay.get(Calendar.YEAR);
        int d = calPayDay.get(Calendar.DAY_OF_MONTH);
        int m = calPayDay.get(Calendar.MONTH) + 1;
        String yy = String.valueOf(y);
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);
        //	    System.out.print(m);
        //	    System.out.print("------");
        if (String.valueOf(tm).equals(String.valueOf(m))) {
            return yy + mm + dd;
        } else {
            return String.valueOf(y) + mm + "01";
        }
    }

    public static java.sql.Timestamp StringToDate(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        java.sql.Timestamp date = new java.sql.Timestamp(calendar
                .getTimeInMillis());

        return date;
    }
    
    public static java.sql.Timestamp StringToDate2(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
            ;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        java.sql.Timestamp date = new java.sql.Timestamp(calendar
                .getTimeInMillis());

        return date;
    }
    
    public static java.sql.Timestamp StringToDate3(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
            ;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        java.sql.Timestamp date = new java.sql.Timestamp(calendar
                .getTimeInMillis());

        return date;
    }
    public static java.sql.Timestamp StringToDate4(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
            ;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        java.sql.Timestamp date = new java.sql.Timestamp(calendar
                .getTimeInMillis());

        return date;
    }
    
    
    public static Date stringToDate(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
            ;
        }
        return d;
    }

    public static java.sql.Timestamp StringToDate1(String StringDate) {
        if (StringDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(StringDate);
        } catch (ParseException e) {
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        java.sql.Timestamp date = new java.sql.Timestamp(calendar
                .getTimeInMillis());

        return date;
    }
    public static String date2str6(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("yyyyMMdd");
        return theFormat.format(aDate);
    }

    public static String time2str6(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("HHmmss");
        return theFormat.format(aDate);
    }

    public static String date2str(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return theFormat.format(aDate);
    }
    public static String date2strCN(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss");
        return theFormat.format(aDate);
    }

    public static String date2str14(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return theFormat.format(aDate);
    }
    public static String date2strMis(Date aDate) {
        if (aDate == null)
            return "";

        SimpleDateFormat theFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return theFormat.format(aDate);
    }

    public static String dataToString(Date theDate) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(theDate);
    }
    
    public static String dataToString1(Date theDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (theDate == null) {
            theDate = new Date();
        }
        return df.format(theDate);
    }

    public static String dataToString2(Date theDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (theDate == null) {
            theDate = new Date();
        }
        return df.format(theDate);
    }
    public static String dataToString3(Date theDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (theDate == null) {
            return null;
        }
        return df.format(theDate);
    }
    public static String getDateBefore(String date, int days) throws Exception {
        Calendar dt = Calendar.getInstance();
        String pattern = "MMdd";
        if (date.length() == 8)
            pattern = "yyyyMMdd";
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        //		String str2 = "2003-10-18 13:15:45";
        Date date2 = formater.parse(date);

        dt.setTime(date2);
        dt.add(Calendar.DAY_OF_MONTH, -days);
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);
        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);
        if (date.length() == 8)
            return date.substring(0, 4) + mm + dd;
        else
            return mm + dd;
    }

    /**
     * �����־����
     * @param
     * @param
     * @return��
     */
    public static String getSysLogContent(String content, Long operType) {
        
        StringBuffer buf = new StringBuffer();

        if (operType.equals(1L)) {
            buf.append("������");
            buf.append(content);
        } else if (operType.equals(2L)) {
            buf.append("ɾ��");
            buf.append(content);
        } else if (operType.equals(3L)) {
            buf.append("�޸ģ�");
            buf.append(content);
        } else if (operType.equals(4L)) {
            buf.append("��ѯ��");
            buf.append(content);
        }
        
        return buf.toString();

    }

    public static String checkDay(String stock1) {
        String flag = "ok";
        //System.out.println(stock1);
        int slen = stock1.length();
        if (slen != 4) {
            return "����λ���:" + stock1;
        }
        if (!Sys.isDigit(stock1))
            return "������������";
        if (Integer.parseInt(stock1.substring(0, 2)) > 12) {
            return "�·���������:" + stock1;
        }
        if (stock1.equals("0230") || stock1.equals("0231")
                || stock1.equals("0431") || stock1.equals("0631")
                || stock1.equals("0931") || stock1.equals("1131"))
            return "������������:" + stock1;
        return "ok";
    }
   
    public static String getBeforeYYYYMM(int beforem){
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        //dt.add(Calendar.DAY_OF_MONTH,-1);
        dt.add(Calendar.MONTH,-beforem);//��ǰbeforem���µ�����
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if(m < 10) mm += "0";
        mm += String.valueOf(m);
        /*
        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if(d < 10) dd += "0";
        dd += String.valueOf(d);
        */
        //ʱ���붼��Ĭ�ϵ�
        return tmp + mm;
    }
    
    public static String getTimeBeforeMinite(int diffmin){
        if(diffmin <= 0){diffmin = 0;}
        Date stime = new Date();
        stime.setMinutes(stime.getMinutes()-diffmin);
        
        Calendar dt = Calendar.getInstance();
        dt.setTime(stime);
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int d = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (d < 10)
            dd += "0";
        dd += String.valueOf(d);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return tmp + mm + dd + hh + minute + ss;
    }
    
    public static Long dateDiff(Date time1,Date time2){
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
        if(time1 == null){time1 = new Date();}
        if(time2 == null){time2 = new Date();}
        
        Long datediff = 0L;
        try {
            Long DAY = 24L * 60L * 60L * 1000L;
            Date d1 = df.parse(df.format(time1));
            Date d2 = df.parse(df.format(time2));
            datediff = (d1.getTime()-d2.getTime())/DAY;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        	Logger.error(e.getMessage(), e);
        }
        return datediff;
    }
    //����ָ�����ȵ���ֵ,���紫��num=1,length=4,�򷵻�0001
    public static String transNumtoStr(Long num,int length){
        String str = ""+num;
        while(str.length()<length){
            str = "0"+str;
        }
        return str;
    }
    //ת��ͼƬ�ߴ�(Դ�ļ�,Ŀ���ļ�,���,�߶�)
    public static String ZoomImage(String srcImg,String destImg,int width,int height){
        String result = "0";
        try {
            BufferedImage srcImage = null;
            File srcfile = new File(srcImg);
            if(srcfile.exists()){
                if(srcfile.canRead()){
                    srcImage =  ImageIO.read(srcfile);
                    if(srcImage != null){
                        int srcWidth = srcImage.getWidth();//ԴͼƬ���
                        int srcHeight = srcImage.getHeight();//ԴͼƬ�߶�
                        int destWidth = width;//Ŀ��ͼƬ���
                        int destHeight = srcHeight*destWidth/srcWidth;//Ŀ��ͼƬ�߶�
                        System.out.println("ԴͼƬ��:"+srcWidth+",��:"+srcHeight);
                        System.out.println("Ŀ��ͼƬ��:"+destWidth+",��:"+destHeight);
                        BufferedImage destImage = new BufferedImage(destWidth,destHeight,srcImage.getType());
                        Graphics g = destImage.getGraphics();
                        g.drawImage(srcImage, 0,0,width,height,null);
                        g.dispose();
                        
                        try {
                            //TODO: �������·����Ҫ�������Ӻ�һ��
                            ImageIO.write(destImage, "jpg", new File(destImg)); //�����޸ĺ��ͼ��,ȫ������ΪJPG��ʽ
                            result = "0";
                        } catch (IOException e) {
                            result = "1Ŀ��ͼƬ�����쳣:"+e.getMessage();
                            return result;
                        }
                    }else{
                        result = "1��ȡԴͼƬ����ʧ��";
                        return result;
                    }
                }else{
                    result = "1ԴͼƬ�������ȡ";
                    return result;
                }
            }else{
                result = "1ԴͼƬ������";
                return result;
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
            result = "1�쳣:"+e.getMessage();
            return result;
        }
        return result;
    }
    
    public static String transCalendarToString(Calendar dt) {
        if(dt == null){return null;}
        String tmp = String.valueOf(dt.get(Calendar.YEAR));
        int m = dt.get(Calendar.MONTH) + 1;
        String mm = "";
        if (m < 10)
            mm += "0";
        mm += String.valueOf(m);

        int day = dt.get(Calendar.DAY_OF_MONTH);
        String dd = "";
        if (day < 10)
            dd += "0";
        dd += String.valueOf(day);

        int h = dt.get(Calendar.HOUR_OF_DAY);
        String hh = "";
        if (h < 10)
            hh += "0";
        hh += String.valueOf(h);

        int min = dt.get(Calendar.MINUTE);
        String minute = "";
        if (min < 10)
            minute += "0";
        minute += String.valueOf(min);

        int s = dt.get(Calendar.SECOND);
        String ss = "";
        if (s < 10)
            ss += "0";
        ss += String.valueOf(s);

        return tmp + "-" + mm + "-" + dd + " " + hh + ":" + minute;
    }
    
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
    /**
     * 
     * @param date
     * @return ���ʱ��ĳ��ȣ��ж�ʹ��������ת������
     */
    public static java.sql.Timestamp checkStringToDate(String date){
        if(date.length()==19){
            return StringToDate(date);
        }
        if(date.length()==16){
            return StringToDate2(date);
        }
        if(date.length()==10){
            return StringToDate3(date);
        }
        if(date.length()==13){
            return StringToDate4(date);
        }
        return null;
    }
    
    
    //��Javaʵ�ֽ�js����escape����ת�����ַ����»�ԭ��
    public static String getescapeTochinese(String str) {
        String result = "";
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        String js = "function changeencode(t){var a = unescape(t);return a;}";
        try {
            engine.eval(js);
            Invocable inv = (Invocable) engine; 
            result = (String) inv.invokeFunction("changeencode", str);
        } catch (ScriptException e) {
            ;
        } catch (NoSuchMethodException e) {
            ;
        }  
        
        return result;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //		System.out.print(Sys.str2YMD("200703201430") + "\n");
        //		System.out.print(Sys.str2YM("20070") + "\n");
        //		System.out.print(Sys.str2YMD("2007032014") + "\n");
        //		System.out.print(Sys.str2YMD("200703201430197878") + "\n");
        //		System.out.print(Sys.str2YMD("20070320143019") + "\n");
        //		System.out.print(Sys.str2YMD("20070320143019") + "\n");
        //		System.out.print(Sys.str2YMD("20070320143019") + "\n");
        //		System.out.println(Sys.cCheckMobile("15212345678"));
        //		System.out.println(Sys.cCheckMobile1("152123"));
        //		System.out.println(Sys.DateFristDay(Calendar.MONTH,0));
        //		System.out.println(Sys.DateFristDay(Calendar.MONTH,1));
        //		System.out.println(Sys.DateFristDay(Calendar.MONTH,2));
        //		System.out.println(Sys.checkPayDay("20071910"));
        System.out.println(Sys.StringToDate("2007-01-01 00:00:00") + "ss");
    }
}
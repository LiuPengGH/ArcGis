package cm.mileage;


import com.alibaba.fastjson.JSONArray;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class test {


    public static void main(String[] args) throws UnsupportedEncodingException {

        BigDecimal b1 = new BigDecimal(1.013424234234);
        BigDecimal b2 = new BigDecimal(2.023442342343);
        System.out.println(b1.add(b2).doubleValue());

    }

}

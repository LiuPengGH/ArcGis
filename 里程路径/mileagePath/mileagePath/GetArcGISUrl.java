package cm.mileagePath;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GetArcGISUrl {

    private static final Logger logger = LoggerFactory.getLogger(GetArcGISUrl.class);


    public static String getArcGISUrl(String trailSql, String dataNew, String phone, ResultSet ReS, String userguid) throws SQLException, IOException, ParseException {

        ResultSet resultSet1 = MySQLHelper.GetSqlVal(trailSql);
        StringBuilder geometry = new StringBuilder();
        resultSet1.last();// 移动到最后
        if (resultSet1.getRow() < 2) {

            String upSql = " update  day_length set date = '" + dataNew + "'  WHERE mobile_phone = '" + phone + "'";
            MySQLHelper.UpdateMySQL(upSql);
            System.out.println("里程更新坐标量 = " + resultSet1.getRow());
            return "";
        }
        resultSet1.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据

        List<JavaBean> list = new ArrayList();

        while (resultSet1.next()) {

            String gisx = resultSet1.getString("gisx");
            String gisy = resultSet1.getString("gisy");
            String date = resultSet1.getString("date");//每个坐标的时间
            String mobilephone = resultSet1.getString("mobilephone");
            String type = resultSet1.getString("type");
            JavaBean javaBean = new JavaBean();

            javaBean.setDate(date);
            javaBean.setMobilephone(mobilephone);
            javaBean.setGisx(gisx);
            javaBean.setGisy(gisy);
            javaBean.setType(type);
            list.add(javaBean);

            geometry.append("{\"geometry\":{\"x\":").append(gisx).append(",\"y\":").append(gisy).append("},\"date\":\"").append(date).append("\"},");
        }
        System.out.println("listsize = " + list.size());

        for (int i = 0; i <= list.size() - 1; i++) {
            //System.out.println("i = " + i);
            if (i == 0) {
                if (ReS == null) {
                    System.out.println("前面没有坐标信息！！！！！！！！！！");
                } else {
                    String predate = ReS.getString("date");
                    String pregisx = ReS.getString("gisx");
                    String pregisy = ReS.getString("gisy");

                    if (pregisx.equals(list.get(i).getGisx()) && pregisy.equals(list.get(i).getGisy())) {
                        continue;
                    }
                    String features = "{\"features\":[" +
                            "{\"geometry\":{\"x\":" + pregisx + ",\"y\":" + pregisy + "},\"date\":\"" + predate + "\"}," +
                            "{\"geometry\":{\"x\":" + list.get(i).getGisx() + ",\"y\":" + list.get(i).getGisy() + "},\"date\":\"" + list.get(i).getDate() + "\"},"
                            + " ]}";
                    // System.out.println("fe = " + features);
                    String urlStr = URLEncoder.encode(features, "gb2312");
                    //String url = "http://58.211.255.58:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
                    String url = "http://10.1.176.8:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +

                            urlStr + "&outSR=4490&returnDirections=false&f=json";

                    String responseJson = HttpClienHelper.httpClienPost(url);  //根据url获取里程Total_Length
                    // System.out.println("返回数据 ： "+responseJson);
                    if (responseJson.contains("error")) {
                        logger.error("计算里程返回错误！");
                        System.out.println("返回错误 ！  ");
                        continue;
                    }

                    JSONObject jsonObject = JSONObject.parseObject(responseJson);

                    String paths = jsonObject.getJSONObject("routes")
                            .getJSONArray("features").getJSONObject(0)
                            .getJSONObject("geometry").getString("paths");

                    String Total_LengthNew = jsonObject.getJSONObject("routes")
                            .getJSONArray("features").getJSONObject(0)
                            .getJSONObject("attributes").getString("Total_Length");

                    Double d = Double.parseDouble(Total_LengthNew);
                    DecimalFormat df = new DecimalFormat("0.00");
                    String totalLength = df.format(d);
                    // System.out.println("totalLength = " + totalLength);
                    if (Double.parseDouble(totalLength) < 5.00) {
                        System.out.println(totalLength + " < 5");
                        String sql = "INSERT INTO day_length_path_type " +
                                " (mobile_phone,start_date,end_date,path) " +
                                " VALUES('" + phone + "','" + predate + "','" + list.get(i).getDate() + "','" + paths + "')";
                        MySQLHelper.UpdateMySQL(sql);

                    }

                    System.out.println(phone + " ++ " + predate + ":" + list.get(0).getDate() + " ++ " + totalLength + " ++ " + paths);
                    String sql = "INSERT INTO day_length_path_type " +
                            " (mobile_phone,start_date,end_date,path) " +
                            " VALUES('" + phone + "','" + predate + "','" + list.get(i).getDate() + "','" + paths + "')";
                    //System.out.println(sql);
                    MySQLHelper.UpdateMySQL(sql);
                    // System.out.println("paths = " + paths);
                    try {
                        HttpPostTrajectoryData2.postTrajectoryData(userguid, phone, predate, list.get(i).getDate(), paths);

                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                }
            }

            String date1 = list.get(i).getDate();
            String gisx1 = list.get(i).getGisx();
            String gisy1 = list.get(i).getGisy();
            //System.out.println("i = " + i);
            for (int k = i + 1; k <= list.size() - 1; k++) {

                if (gisx1.equals(list.get(k).getGisx()) && gisy1.equals(list.get(k).getGisy())) {
                    System.out.println(gisx1 + "=====" + list.get(k).getGisx());
                    System.out.println("坐标相同");
                    i = k;
                    continue;
                } else {

                    String features = "{\"features\":[" +
                            "{\"geometry\":{\"x\":" + gisx1 + ",\"y\":" + gisy1 + "},\"date\":\"" + date1 + "\"}," +
                            "{\"geometry\":{\"x\":" + list.get(k).getGisx() + ",\"y\":" + list.get(k).getGisy() + "},\"date\":\"" + list.get(k).getDate() + "\"},"
                            + " ]}";
                    //System.out.println("fe = " + features);

                    String urlStr = URLEncoder.encode(features, "gb2312");
                    //String url = "http://58.211.255.58:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
                    String url = "http://10.1.176.8:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
                            urlStr + "&outSR=4490&returnDirections=false&f=json";
                    System.out.println("url = " + url);

                    String responseJson = HttpClienHelper.httpClienPost(url);  //根据url获取里程Total_Length
                    if (responseJson.contains("error")) {
                        System.out.println("返回错误 ！  ");
                        continue;
                    }

                    JSONObject jsonObject = JSONObject.parseObject(responseJson);

                    String paths = jsonObject.getJSONObject("routes")
                            .getJSONArray("features").getJSONObject(0)
                            .getJSONObject("geometry").getString("paths");
                    System.out.println("paths : " + paths);

                    String Total_LengthNew = jsonObject.getJSONObject("routes")
                            .getJSONArray("features").getJSONObject(0)
                            .getJSONObject("attributes").getString("Total_Length");

                    Double d = Double.parseDouble(Total_LengthNew);
                    DecimalFormat df = new DecimalFormat("0.00");
                    String totalLength = df.format(d);
                    //System.out.println("totalLength = " + totalLength);
                    if (Double.parseDouble(totalLength) < 5.00) {
                        double[] double1 = GaussProjCal.gaussProjCal(Double.parseDouble(gisx1), Double.parseDouble(gisy1), 120);
                        double[] double2 = GaussProjCal.gaussProjCal(Double.parseDouble(list.get(k).getGisx()), Double.parseDouble(list.get(k).getGisy()), 120);
                        String path1 = "[[[" + double1[0] + "," + double1[1] + "],[" + double2[0] + "," + double2[1] + "]]]";
                        System.out.println(totalLength + " < 5");
                        // System.out.println(phone + " ++ " + date1 + ":" + list.get(k).getDate()  + " ++ " + totalLength + " ++ " + path1);
                        String sql = "INSERT INTO day_length_path_type " +
                                " (mobile_phone,start_date,end_date,path) " +
                                " VALUES('" + phone + "','" + date1 + "','" + list.get(k).getDate() + "','" + path1 + "')";
                        MySQLHelper.UpdateMySQL(sql);
                        // System.out.println("paths = " + path1);
                        try {
                            HttpPostTrajectoryData2.postTrajectoryData(userguid, phone, date1, list.get(k).getDate(), path1);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }

                        break;

                    } else {
                        //System.out.println(phone + " ++ " + date1 + ":" + list.get(k).getDate()  + " ++ " + totalLength + " ++ " + paths);
                        String sql = "INSERT INTO day_length_path_type " +
                                " (mobile_phone,start_date,end_date,path) " +
                                " VALUES('" + phone + "','" + date1 + "','" + list.get(k).getDate() + "','" + paths + "')";
                        MySQLHelper.UpdateMySQL(sql);
                        //System.out.println("paths = " + paths);

                        try {
                            HttpPostTrajectoryData2.postTrajectoryData(userguid, phone, date1, list.get(k).getDate(), paths);

                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        break;
                    }
                }
            }
        }
        String features = "{\"features\":[" + geometry + " ]}";
        String urlStr = URLEncoder.encode(features, "gb2312");
        //return  "http://58.211.255.58:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
        return "http://10.1.176.8:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
                urlStr + "&outSR=4490&returnDirections=false&f=json";
    }
}


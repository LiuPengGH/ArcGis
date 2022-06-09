package com.example.arcGIS.controller;

import com.example.arcGIS.request.CommunalRequest;
import com.example.arcGIS.response.CommunalResponse;
import com.example.arcGIS.service.ArcGISService;
import com.example.arcGIS.service.MysqlUpObjID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.arcGIS.service.MysqlUpObjID.*;


//请求的路径
@RequestMapping("/arcgis")
//定义接收器
@RestController

public class ArcGISController {
    //自动加载
    @Resource
    private ArcGISService arcGISService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISController.class);
    //List<String> list = new ArrayList<>();\
    String str = "&&";

    /**
     * 根据rowGuid更新数据
     *
     * @param communalRequest
     * @return CommunalResponse
     */
    //1.定义post方法 2.定义路径
    @PostMapping("/datasynchronization")
    public CommunalResponse arcGIS_Util(@RequestBody CommunalRequest communalRequest) {
        LOGGER.info("======================START=========================");
        LOGGER.info(communalRequest.getRowGuid());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {


            ResultSet rs = MysqlUpObjID.select("SELECT * FROM componentinfofront WHERE RowGuid = '" + communalRequest.getRowGuid() + "'");
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            System.out.println(columnCount);
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String synchronizationDate = df.format(new Date());

        LOGGER.info("======================" + communalRequest.getRowGuid() + "=========================");
        if (str.contains(communalRequest.getRowGuid())) {
            LOGGER.info("======================END=========================");

            return new CommunalResponse("600", "同步中,请等待", null);
        } else str += communalRequest.getRowGuid() + "&&";
        // System.out.println(str);

        try {
            String objid = arcGISService.getGISData(communalRequest);
            String endTime = df.format(new Date());
            String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                    + objid + "','" + synchronizationDate + "','200','同步成功','" + endTime + "','" + list + "') ";
            Insert(synchronizationSQL);
            str = str.replace(communalRequest.getRowGuid() + "&&", "");

            LOGGER.info("======================END=========================");

            return new CommunalResponse("200", "同步成功", objid);
        } catch (Exception e) {
            LOGGER.error("同步失败: {}", e.getMessage());
            if (e.getMessage().isEmpty()) {
                String endTime = df.format(new Date());
                str = str.replaceAll(communalRequest.getRowGuid() + "&&", "");
                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + "null" + "','" + synchronizationDate + "','400','参数错误','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);
                LOGGER.info("======================END=========================");

                return new CommunalResponse("400", "参数错误", null);
            } else if (e.getMessage().equals("参数错误")) {
                String endTime = df.format(new Date());

                str = str.replace(communalRequest.getRowGuid() + "&&", "");

                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + "null" + "','" + synchronizationDate + "','400','参数错误','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);

                LOGGER.info("======================END=========================");

                return new CommunalResponse("400", "参数错误", null);

            } else if (e.getMessage().split("-")[0].equals("已同步")) {
                String endTime = df.format(new Date());

                str = str.replace(communalRequest.getRowGuid() + "&&", "");
                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + e.getMessage().split("-")[1] + "','" + synchronizationDate + "','500','已同步','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);
                //System.out.println( e.getMessage().split("-")[1]);
                LOGGER.info("======================END=========================");

                return new CommunalResponse("500", "已同步", e.getMessage().split("-")[1]);

            } else if (e.getMessage().contains("坐标不同")) {

                String endTime = df.format(new Date());

                str = str.replace(communalRequest.getRowGuid() + "&&", "");
                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + "null" + "','" + synchronizationDate + "','100','坐标不同','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);
                //System.out.println( e.getMessage().split("-")[1]);
                LOGGER.info("======================END=========================");
                return new CommunalResponse("100", "坐标与原部件不一致，更新失败！", null);

            }else if (e.getMessage().contains("部件类型代码错误，新增失败")) {

                String endTime = df.format(new Date());

                str = str.replace(communalRequest.getRowGuid() + "&&", "");
                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + "null" + "','" + synchronizationDate + "','555','部件类型代码错误，新增失败','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);
                //System.out.println( e.getMessage().split("-")[1]);
                LOGGER.info("======================END=========================");
                return new CommunalResponse("555", "部件类型代码错误，新增失败!", null);
            } else {
                String endTime = df.format(new Date());

                str = str.replace(communalRequest.getRowGuid() + "&&", "");
                String synchronizationSQL = "INSERT INTO  synchronization_log VALUES('" + communalRequest.getRowGuid() + "','"
                        + "null" + "','" + synchronizationDate + "','300','同步失败','" + endTime + "','" + list + "') ";
                Insert(synchronizationSQL);

                LOGGER.info("======================END=========================");
                return new CommunalResponse("300", "同步失败", null);


            }
        }
    }
}

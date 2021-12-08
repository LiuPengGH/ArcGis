package com.example.arcGIS.service;

import com.alibaba.fastjson.JSONObject;
import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.*;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.system.EngineInitializer;
import com.example.arcGIS.dao.ArcGISDao;
import com.example.arcGIS.pojo.ArcGIS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ArcGISInsertAdd {

    @Resource
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISInsertAdd.class);
    private static ArcGISDao arcGISDao;


    /**
     * @param arcGIS
     * @param sOBJID
     * @param sROWGUID
     * @return
     * @throws Exception
     */

    public static String arcGISUpData(ArcGIS arcGIS, String sOBJID, String sROWGUID) throws Exception {


        initializeArcGISLicenses.InitializeArcGISLicenses();
        //初始化arcengine
        EngineInitializer.initializeEngine();

        String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\mc.properties";
        String gdbPath = PropertiesUtil.getPropertyParam("GDBPath", path);
        String[] sFlds1 = "ObjID,ObjName,DeptCode1,DeptName1,DeptCode2,DeptName2,DeptCode3,DeptName3,BGID,ObjState,ORDate,CHDate,DataSource,Note,Material,PicAddress,LocateDSC,ObjName,x,y,ldcode,gacode".split(",");
        String sBJType;
        //创建 GDB 工作空间对象
        FileGDBWorkspaceFactory pFileGDBWorkspaceFactoryClass = new FileGDBWorkspaceFactory();
        IWorkspace iWorkspace = pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);
        IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace) pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);

        String jsonString = arcGIS.toJson();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        IFeatureClass pFeaCls;

        String x = jsonObject.getString("x");
        String y = jsonObject.getString("y");
        LOGGER.info("新增数据 ：sOBJID = 【" + sOBJID + "】；ROWGUID【" + sROWGUID + "】");

        if (sOBJID.length() == 16) {
            //需要判断sOBJID长度为16时，GDB库中是否存在
            LOGGER.warn("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不需要更新！");
            throw new Exception("已同步-长度不正确");
        }
        if (x == null || y == null) {

            LOGGER.warn("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不需要更新！");
            throw new Exception("同步失败");
        }
        sBJType = sOBJID.substring(6, 10);
        pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);
        if (pFeaCls==null){

            throw new Exception("部件类型代码错误，新增失败");
        }
        int shapeType = pFeaCls.getShapeType();

        IGeometry iGeometry = null;
        if (shapeType == 1) {
            iGeometry = CreateIPoint.CreatePoint(Double.parseDouble(x), Double.parseDouble(y));
        } else if (shapeType == 3) {

            IPoint iPoint1 = CreateIPoint.CreatePoint(Double.parseDouble(x), Double.parseDouble(y));
            IPoint iPoint2 = CreateIPoint.CreatePoint(Double.parseDouble(x) + 0.0001, Double.parseDouble(y) + 0.0001);
            // iGeometry = CreateLine.createLine(iPoint1, iPoint2);
            List<IPoint> list = new ArrayList<>();
            list.add(iPoint1);
            list.add(iPoint2);
            iGeometry = CreatePolyline.CreatePolyline(list);

        } else if (shapeType == 4) {
            //throw new Exception("同步失败");
            iGeometry = CreatePolygon.CreatePolygon(Double.parseDouble(x), Double.parseDouble(x) + 0.00003, Double.parseDouble(y), Double.parseDouble(y) + 0.00003);
        }
        //System.out.println(iGeometry);

        IRow row = null;
        IFeature feature = null;
        String ojbID = null;

        if (sBJType.equals("0137")) {
            feature = CreateNewFeature.CreateNewFeature(pFeaCls, iGeometry);

            String maxOjbID = "499999";
            String ObjID6;
            ResultSet resultSet = MysqlUpObjID.select("SELECT SUBSTRING(ObjID,11,6) as maxOjb " +
                    "FROM `componentinfofront` " +
                    " where SUBSTRING(ObjID,1,10) = '" + sOBJID + "' AND length(ObjID)=16 ORDER" +
                    " BY SUBSTRING(ObjID,11,6) DESC LIMIT 1");

            if (resultSet.next()) {

                String maxOjb = resultSet.getString("maxOjb");
                LOGGER.info("库中最大OjbID为 ; " + maxOjb);

                if (Long.parseLong(maxOjb) > Long.parseLong(maxOjbID)) {
                    ObjID6 = maxOjb;
                } else {
                    ObjID6 = maxOjbID;
                }
            } else {
                ObjID6 = "499999";
            }

            long aLong;
            //获取最大id

            aLong = Long.parseLong(sOBJID + "000001") + (Integer.parseInt(ObjID6));

            LOGGER.info("新增OjbID为 ： " + aLong);

            String sValNew;
            row = feature;
            for (String s : sFlds1) {

                String sFld = s.trim();
                sValNew = jsonObject.getString(sFld);
                //针对特殊字段处理
                if (sValNew == null || sValNew.equals("")) {

                    continue;
                } else if (sFld.equals("CHDate")) {

                    sValNew = jsonObject.getString(sFld).split(" ")[0];
                }
                if (sFld.equals("ORDate")) {

                    sValNew = jsonObject.getString(sFld).split(" ")[0];
                }
                if (sFld.equals("PicAddress")) {

                    String[] split = jsonObject.getString(sFld).split(";");
                    String str = "";
                    if (split.length > 2) {
                        str = split[0].split("attachGuid=")[1] + "," + split[1].split("attachGuid=")[1];
                        System.out.println(str.length());
                        if ((str + split[2].split("attachGuid=")[1]).length() < 100) {
                            str = str + "," + str + split[2].split("attachGuid=")[1];
                        }
                    } else if (split.length == 1) {
                        if (split[0].split("attachGuid=").length > 1) {
                            str = split[0].split("attachGuid=")[1];
                        } else {
                            str = "";
                        }
                    } else if (split.length == 2) {
                        str = split[0].split("attachGuid=")[1] + ";" + split[1].split("attachGuid=")[1];
                    }

                    sValNew = str;
                }
                if (sFld.equals("ObjID")) {//拼接ObjID

                    sValNew = String.valueOf(aLong);

                    ojbID = sValNew;
                }

                if (sValNew.equals(""))
                    continue;
                SetValue.setValue(row, sFld, sValNew);
            }
        } else if (!jsonObject.getString("ObjName").equals("路灯")) {

            feature = CreateNewFeature.CreateNewFeature(pFeaCls, iGeometry);

            String maxOjbID = "499999";
            String ObjID6;
            ResultSet resultSet = MysqlUpObjID.select("SELECT SUBSTRING(ObjID,11,6) as maxOjb " +
                    "FROM `componentinfofront` " +
                    " where SUBSTRING(ObjID,1,10) = '" + sOBJID + "' " +
                    "AND length(ObjID)=16 ORDER BY SUBSTRING(ObjID,11,6) DESC LIMIT 1");

            if (resultSet.next()) {
                String maxOjb = resultSet.getString("maxOjb");

                if (Long.parseLong(maxOjb) > Long.parseLong(maxOjbID)) {

                    ObjID6 = maxOjb;
                    LOGGER.info("最大号为 : " + maxOjb);

                } else {
                    ObjID6 = maxOjbID;
                    LOGGER.info("最大号为 : " + maxOjbID);
                }
            } else {
                ObjID6 = "499999";
                LOGGER.info("最大号为 : " + 5000000);
            }

            long aLong;
            //获取最大id

            aLong = Long.parseLong(sOBJID + "000001") + (Integer.parseInt(ObjID6));

            LOGGER.info("新增OjbID为 ： " + aLong);
            String sValNew;
            row = feature;
            for (int in = 0; in < sFlds1.length - 2; in++) {

                String sFld = sFlds1[in].trim();
                sValNew = jsonObject.getString(sFld);
                // System.out.println(sValNew);
                //针对特殊字段处理
                if (sValNew == null || sValNew.equals("")) {
                    LOGGER.error(sFld + " = null");
                    continue;
                } else if (sFld.equals("CHDate")) {

                    sValNew = jsonObject.getString(sFld).split(" ")[0];
                }
                if (sFld.equals("ORDate")) {

                    sValNew = jsonObject.getString(sFld).split(" ")[0];
                }

                if (sFld.equals("PicAddress")) {

                    String[] split = jsonObject.getString(sFld).split(";");
                    String str = "";
                    if (split.length > 2) {
                        str = split[0].split("attachGuid=")[1] + "," + split[1].split("attachGuid=")[1];
                        System.out.println(str.length());
                        if ((str + split[2].split("attachGuid=")[1]).length() < 100) {
                            str = str + "," + str + split[2].split("attachGuid=")[1];
                        }
                    } else if (split.length == 1) {
                        if (split[0].split("attachGuid=").length > 1) {
                            str = split[0].split("attachGuid=")[1];
                        } else {
                            str = "";
                        }
                    } else if (split.length == 2) {
                        str = split[0].split("attachGuid=")[1] + ";" + split[1].split("attachGuid=")[1];
                    }

                    sValNew = str;
                }
                if (sFld.equals("ObjID")) {//拼接ObjID
                    sValNew = String.valueOf(aLong);

                    ojbID = sValNew;
                }
                Boolean aBoolean = SetValue.setValue(row, sFld, sValNew);
                if (!aBoolean){
                    throw  new Exception("同步失败");
                }

                //arcGISAddDao.setBoo(aBoolean);
            }

        } else {
            LOGGER.error("sOBJID = " + sOBJID + ",ObjName = 路灯。不符合规定");
        }

        if (row != null) {
            try {
                row.store();
                LOGGER.info("GDB属性保存成功！");
            } catch (Exception exception) {
                exception.getMessage();
                LOGGER.info("STORE 失败！");

                throw new Exception("同步失败");
            }
        } else {
            LOGGER.error("row执行失败");
            throw new Exception("同步失败");
        }

        LOGGER.info("根据工单【" + sROWGUID + "】更新属性成功！");
        LOGGER.info(":{}", "根据工单【" + sROWGUID + "】更新属性成功！");
        //更新shape 点坐标
        try {
            //获取Shepa 的 xy坐标
            String shape = GetShapeXY.getXY(feature);
            //获取status
            String status = GetStatus.getStatus(jsonObject);
            //获取更新数据Json字符串
            String newDatas = GetUpDataGDB.newDatas(feature, pFeaCls, shape, status, sOBJID);
            MysqlUpObjID.GetSqlVal("update  componentinfofront set Update_Data='" + newDatas + "' where RowGuid='" + sROWGUID + "'");

        } catch (Exception e) {
            System.out.println(2);
            return ojbID;
        }

        LOGGER.info("ojbID = " + ojbID);
        return ojbID;
    }
}

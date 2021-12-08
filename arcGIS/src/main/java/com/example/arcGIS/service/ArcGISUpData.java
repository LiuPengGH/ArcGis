package com.example.arcGIS.service;

import com.alibaba.fastjson.JSONObject;
import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.*;
import com.esri.arcgis.system.EngineInitializer;
import com.example.arcGIS.dao.ArcGISDao;
import com.example.arcGIS.pojo.ArcGIS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public class ArcGISUpData {

    @Resource
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISService.class);
    private static ArcGISDao arcGISDao;

    /**
     * 更新
     *
     * @param arcGIS
     * @param sOBJID
     * @param sROWGUID
     * @throws IOException
     */
    public static boolean arcGISUpData(ArcGIS arcGIS, String sOBJID, String sROWGUID) throws Exception {


        initializeArcGISLicenses.InitializeArcGISLicenses();
        //初始化arcengine
        EngineInitializer.initializeEngine();

        String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\mc.properties";
        String gdbPath = PropertiesUtil.getPropertyParam("GDBPath", path);
        String[] sFlds1 = "DeptCode1,DeptName1,DeptCode2,DeptName2,DeptCode3,DeptName3,BGID,ObjState,ORDate,CHDate,DataSource,Note,Material,PicAddress,LocateDSC,ObjName,x,y,ldcode,gacode".split(",");
        String sBJType;
        //创建 GDB 工作空间对象
        FileGDBWorkspaceFactory pFileGDBWorkspaceFactoryClass = new FileGDBWorkspaceFactory();
        IWorkspace iWorkspace = pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);
        IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace) pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);

        String jsonString = arcGIS.toJson();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        IFeatureClass pFeaCls;

        if (sOBJID.length() != 16) {
            LOGGER.error(":{}", "ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不正确！");
            throw new Exception("同步失败");
        }
        sBJType = sOBJID.substring(6, 10);
        pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);

        List<IFeature> pFeas = GetFeatures.GetFeatures(iWorkspace, pFeaCls, "ObjID='" + sOBJID + "'");
        if (pFeas.size() == 0) {
            LOGGER.error(":{}", "ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不存在！");
            throw new Exception("同步失败");
        }
        if (pFeas.size() > 1) {
            LOGGER.error("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】重复！");
            throw new Exception("同步失败");
        }
        IFeature pFea = pFeas.get(0);
        String arcY = pFea.getValue(24).toString();
        String arcX = pFea.getValue(23).toString();
        System.out.println(arcX+arcY);

        boolean b = false;
        String sValNew;

        if (sBJType.contains("0137")) {
            for (String s : sFlds1) {

                String sFld = s.trim();
                sValNew = jsonObject.getString(sFld);

                if (sFld.equals("x")){
                    if (!jsonObject.getString(sFld).substring(0,10).equals(arcX.substring(0,10))){
                        throw new Exception("坐标不同");
                    }
                }
                if (sFld.equals("y")){
                    if (!jsonObject.getString(sFld).substring(0,9).equals(arcY.substring(0,9))){
                        throw new Exception("坐标不同");
                    }
                }

                if (sValNew == null || sValNew.equals("")) {
                    // LOGGER.error(sFld + " = null");
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
                    }

                    sValNew = str;
                }
                b = SetValue.setValue(pFea, sFld, sValNew);
            }
        } else if (!jsonObject.getString("ObjName").equals("路灯")) {

            for (int i = 0; i < sFlds1.length - 2; i++) {
                String sFld = sFlds1[i].trim();

                if (sFld.equals("x")){
                    if (!jsonObject.getString(sFld).substring(0,10).equals(arcX.substring(0,10))){
                        LOGGER.error(jsonObject.getString(sFld).substring(0,10));
                        LOGGER.error(arcX.substring(0,10));
                        LOGGER.error("坐标不同");
                        throw new Exception("坐标不同");
                    }
                }
                if (sFld.equals("y")){
                    if (!jsonObject.getString(sFld).substring(0,9).equals(arcY.substring(0,9))){
                        LOGGER.error("坐标不同");
                        LOGGER.error(jsonObject.getString(sFld).substring(0,10));
                        LOGGER.error(arcY.substring(0,10));
                        throw new Exception("坐标不同");

                    }
                }
                sValNew = jsonObject.getString(sFld);
                if (sValNew == null || sValNew.equals("")) {
//                    System.out.println(sFld + " = null");
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
                       // System.out.println(str.length());
                        if ((str + split[2].split("attachGuid=")[1]).length() < 100) {
                            str = str + "," + str + split[2].split("attachGuid=")[1];
                        }
                    } else if (split.length == 1) {
                        if (split[0].split("attachGuid=").length > 1) {
                            str = split[0].split("attachGuid=")[1];
                        } else {
                            str = "";
                        }
                    }

                    sValNew = str;
                }

                b = SetValue.setValue(pFea, sFld, sValNew);
            }
        } else {
            LOGGER.error("sOBJID = " + sOBJID + ",ObjName = 路灯。不符合规定");
        }
//        System.out.println(b);
//        if (b) {
            pFea.store();
            LOGGER.info(":{}", "根据工单【" + sROWGUID + "】更新属性成功！");
            //获取Shepa 的 xy坐标
            String shape = GetShapeXY.getXY(pFea);
            //获取status
            String status = GetStatus.getStatus(jsonObject);
            //获取更新数据Json字符串
            String newDatas = GetUpDataGDB.newDatas(pFea, pFeaCls, shape, status,sOBJID);
            MysqlUpObjID.GetSqlVal("update  componentinfofront set Update_Data='" + newDatas + "' where RowGuid='" + sROWGUID + "'");
//            LOGGER.info("更新数据JSON字符串： " + newDatas);
//            //调用远程更新接口
//            HttpClientPost.httpClienPost(newDatas, path);
 //       [{"ObjState":"丢失","ORDate":"2021-09-07","CHDate":"2021-09-07","DataSource":"实测","Note":"夏市村第五组5号居民家门口的垃圾分类收集桶缺失。","LocateDSC":"苏州市张家港市福利村十四组东北约83米","Material":"其他","PicAddress":"bb5d7684-e3e1-4487-8b09-a76f7ba01c6d","ObjName":"垃圾桶","ObjID":"3205820305007077","BGID":"320582107234001","Shape":"POINT (120.60443141000007 31.74252972000005)","status":"02","SmallClass":"垃圾箱","LargeClass":" 市容环境设施"}]
//
//        } else {
//            throw new Exception("同步失败");
//        }
        return b;
    }
}

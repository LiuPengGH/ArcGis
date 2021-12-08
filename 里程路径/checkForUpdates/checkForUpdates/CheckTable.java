package cm.checkForUpdates;

import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.*;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.system.EngineInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckTable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTable.class);

    public static void main(String[] args) throws Exception {


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

        try {

        }catch (Exception exception){
            exception.getMessage();
            LOGGER.error("失败");
        }
        String logSQL = "SELECT * FROM `synchronization_log` WHERE `Code` ='200' and SUBSTRING(StartTime,1,10) = '2021-11-17'";
        ResultSet selectLog = MysqlUtil.select(logSQL);
        while (selectLog.next()){
            IFeatureClass pFeaCls;
            String sOBJID = selectLog.getString("ObjID");
            String sROWGUID = selectLog.getString("rowGuid");
          //  LOGGER.info("新增数据 ：sOBJID = 【" + sOBJID + "】；ROWGUID【" + sROWGUID + "】");

            LOGGER.info("sOBJID = " + sOBJID);
            sBJType = sOBJID.substring(6, 10);
            pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);
            List<IFeature> pFeas = GetFeatures.GetFeatures(iWorkspace, pFeaCls, "ObjID='" + sOBJID + "'");
            if (pFeas.size() == 0) {
                LOGGER.error(":{}", "ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不存在！");

                String sql = "SELECT * FROM `componentinfofront` WHERE RowGuid = '"+sROWGUID+"'";
                ResultSet insertData = MysqlUtil.select(sql);

                while (insertData.next()){
                    String x = insertData.getString("x");
                    String y = insertData.getString("y");

                    if (x == null || y == null) {

                        LOGGER.warn("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不需要更新！");
                        continue;
                    }
                    sBJType = sOBJID.substring(6, 10);
                    pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);
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

                    if (sBJType.equals("0137")) {
                        feature = CreateNewFeature.CreateNewFeature(pFeaCls, iGeometry);
                        assert feature != null;

                        String sValNew;
                        row = feature;
                        for (String s : sFlds1) {

                            String sFld = s.trim();
                            sValNew = insertData.getString(sFld);
                            //针对特殊字段处理
                            if (sValNew == null || sValNew.equals("")) {

                                continue;
                            } else if (sFld.equals("CHDate")) {

                                sValNew = insertData.getString(sFld).split(" ")[0];
                            }
                            if (sFld.equals("ORDate")) {

                                sValNew = insertData.getString(sFld).split(" ")[0];
                            }
                            if (sFld.equals("PicAddress")) {

                                String[] split = insertData.getString(sFld).split(";");
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

                                sValNew = sOBJID;

                            }

                            if (sValNew.equals(""))
                                continue;
                            SetValue.setValue(row, sFld, sValNew);
                        }
                    } else if (!insertData.getString("ObjName").equals("路灯")) {

                        feature = CreateNewFeature.CreateNewFeature(pFeaCls, iGeometry);


                        String sValNew;
                        row = feature;
                        for (int in = 0; in < sFlds1.length - 2; in++) {

                            String sFld = sFlds1[in].trim();
                            sValNew = insertData.getString(sFld);
                            // System.out.println(sValNew);
                            //针对特殊字段处理
                            if (sValNew == null || sValNew.equals("")) {
                                LOGGER.error(sFld + " = null");
                                continue;
                            } else if (sFld.equals("CHDate")) {

                                sValNew = insertData.getString(sFld).split(" ")[0];
                            }
                            if (sFld.equals("ORDate")) {

                                sValNew = insertData.getString(sFld).split(" ")[0];
                            }

                            if (sFld.equals("PicAddress")) {

                                String[] split = insertData.getString(sFld).split(";");
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
                                sValNew = sOBJID;

                            }
                            Boolean aBoolean = SetValue.setValue(row, sFld, sValNew);
                            if (!aBoolean){
                                LOGGER.error("set数据失败");
                            }

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
                            LOGGER.error("store 执行失败");
                        }
                    } else {
                        LOGGER.error("row执行失败");
                    }
                    LOGGER.info("根据工单【" + sROWGUID + "】更新属性成功！");
                    LOGGER.info(":{}", "根据工单【" + sROWGUID + "】更新属性成功！");
                    //更新shape 点坐标

                }


            }

        }



    }
}

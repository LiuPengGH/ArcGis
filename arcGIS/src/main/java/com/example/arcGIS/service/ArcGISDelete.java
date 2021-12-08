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
import java.util.List;

public class ArcGISDelete {

    @Resource
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISDelete.class);
    private static ArcGISDao arcGISDao;


    public static boolean arcGISUpData(ArcGIS arcGIS, String sOBJID, String sROWGUID) throws Exception {

        initializeArcGISLicenses.InitializeArcGISLicenses();
        //初始化arcengine
        EngineInitializer.initializeEngine();

        String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\mc.properties";
        String gdbPath = PropertiesUtil.getPropertyParam("GDBPath", path);
        String sBJType = "";
        //创建 GDB 工作空间对象
        FileGDBWorkspaceFactory pFileGDBWorkspaceFactoryClass = new FileGDBWorkspaceFactory();
        IWorkspace iWorkspace = pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);
        IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace) pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);

        String jsonString = arcGIS.toJson();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        IFeatureClass pFeaCls = null;
        if (jsonObject.isEmpty()) {
            LOGGER.error("没有要删除的数据");
            throw new Exception("同步失败");
        }
        if (sOBJID.length() != 16) {
            //需要判断sOBJID长度为16时，GDB库中是否存在
            LOGGER.warn("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不需要删除！");
            throw new Exception("同步失败");
        }
        if (sBJType != sOBJID.substring(6, 10)) {
            sBJType = sOBJID.substring(6, 10);
            pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);
        }

        List<IFeature> pFeas = GetFeatures.GetFeatures(iWorkspace, pFeaCls, "ObjID='" + sOBJID + "'");
        if (pFeas.size() == 0) {
            LOGGER.error("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】不存在！");
            throw new Exception("同步失败");
        }
        if (pFeas.size() > 1) {
            LOGGER.error("ROWGUID【" + sROWGUID + "】对应部件编号【" + sOBJID + "】重复！");
            throw new Exception("同步失败");
        }
        IFeature pFea = pFeas.get(0);
//        //获取Shepa 的 xy坐标
        String shape = GetShapeXY.getXY(pFea);
        String status = "3";
        //获取更新数据Json字符串
        String newDatas = GetUpDataGDB.newDatas(pFea, pFeaCls, shape, status,sOBJID);
        MysqlUpObjID.GetSqlVal("update  componentinfofront set Update_Data='" + newDatas + "' where RowGuid='" + sROWGUID + "'");

//        LOGGER.info("删除数据JSON字符串： " + newDatas);
        //HttpClientPost.httpClienPost(newDatas,path);
        //System.out.println(b);
        boolean b = DeleteFeature.deleteFeatureClass(iWorkspace, pFea);

        return b;
    }
}

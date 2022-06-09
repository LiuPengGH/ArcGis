package com.example.upDateData;

import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.system.EngineInitializer;
import com.example.arcGIS.service.PropertiesUtil;
import com.example.arcGIS.service.initializeArcGISLicenses;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpDateData {

    public static void main(String[] args) throws SQLException, IOException {
        String upDatasql = "SELECT * FROM synchronization_log";
        ResultSet resultSet = MysqlUtil.select(upDatasql);

        while (resultSet.next()){

            String rowGuid = resultSet.getString("rowGuid");
            String message = resultSet.getString("Message");

            if (!message.equals("同步成功")){
                System.out.println("不需要更新");
            }else{

                String sql = "SELECT * FROM componentinfofront where rowGuid ='"+rowGuid+"'";
                ResultSet select = MysqlUtil.select(sql);

                initializeArcGISLicenses.InitializeArcGISLicenses();
                //初始化arcengine
                EngineInitializer.initializeEngine();

                String path = "C:\\Users\\lp\\Documents\\javaJAR\\mc.properties";
                String gdbPath = PropertiesUtil.getPropertyParam("GDBPath", path);
                String[] sFlds1 = "DeptCode1,DeptName1,DeptCode2,DeptName2,DeptCode3,DeptName3,BGID,ObjState,ORDate,CHDate,DataSource,Note,Material,PicAddress,LocateDSC,ObjName,x,y,ldcode,gacode".split(",");
                String sBJType;
                //创建 GDB 工作空间对象
                FileGDBWorkspaceFactory pFileGDBWorkspaceFactoryClass = new FileGDBWorkspaceFactory();
                IWorkspace iWorkspace = pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);
                IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace) pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);







            }
        }
    }
}

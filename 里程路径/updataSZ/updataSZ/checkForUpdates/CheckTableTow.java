package cm.updataSZ.checkForUpdates;

import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IWorkspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

public class CheckTableTow {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTableTow.class);

    public static void main(String[] args) throws Exception {

        initializeArcGISLicenses.InitializeArcGISLicenses();
        String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\mc.properties";
        String gdbPath = PropertiesUtil.getPropertyParam("GDBPath", path);
        String sBJType;
        IFeatureClass pFeaCls;
        //创建 GDB 工作空间对象
        FileGDBWorkspaceFactory pFileGDBWorkspaceFactoryClass = new FileGDBWorkspaceFactory();
        IWorkspace iWorkspace = pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);
        IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace) pFileGDBWorkspaceFactoryClass.openFromFile(gdbPath, 0);


        String logSQL = "SELECT * FROM `componentinfofront` WHERE CHDate >'2021-09-16' AND CHDate < '2021-10-20 14:18:48' AND length(ObjID) = 16 ";
        ResultSet selectLog = MysqlUtil.select(logSQL);
        while (selectLog.next()){
            String sOBJID = selectLog.getString("ObjID");
            String rowGuid = selectLog.getString("RowGuid");
            System.out.println(rowGuid);
            sBJType = sOBJID.substring(6, 10);
            pFeaCls = GetFeaCls.GetFeaCls(iWorkspace, pFeatureWorkspace, sBJType);
            List<IFeature> pFeas = GetFeatures.GetFeatures(iWorkspace, pFeaCls, "ObjID='" + sOBJID + "'");
            IFeature pFea = pFeas.get(0);
            //获取Shepa 的 xy坐标
            String shape = GetShapeXY.getXY(pFea);
            //获取status
            String status = "\"" + "0" + selectLog.getString("ispassed") + "\"";

            //获取更新数据Json字符串
            String newDatas = GetUpDataGDB.newDatas(pFea, pFeaCls, shape, status,sOBJID);
            System.out.println(newDatas);
            boolean b1 = HttpInsertPost.httpClienPost(newDatas, path);

        }

    }
}

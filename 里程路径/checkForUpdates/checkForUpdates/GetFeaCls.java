package cm.checkForUpdates;

import com.esri.arcgis.geodatabase.*;

import java.io.IOException;

public class GetFeaCls {
    /**
     * @param iWorkspace
     * @param pFeatureWorkspace
     * @param sCode
     * @return
     */
    public static IFeatureClass GetFeaCls(IWorkspace iWorkspace, IFeatureWorkspace pFeatureWorkspace, String sCode) throws IOException {
        IFeatureClass pFeaCls = null;
        try {
            IEnumDataset pEDataset = iWorkspace.getDatasets(esriDatasetType.esriDTAny);
            IDataset pDataset = pEDataset.next();

            //  System.out.println(pDataset);
            while (pDataset != null) {
                if (pDataset.getType() == esriDatasetType.esriDTFeatureClass) {
                    if (pDataset.getName().contains(sCode)) {
                        pFeaCls = pFeatureWorkspace.openFeatureClass(pDataset.getName());

                        break;
                    }
                }
                //如果是数据集
                else if (pDataset.getType() == esriDatasetType.esriDTFeatureDataset) {
                    IEnumDataset pESubDataset = pDataset.getSubsets();
                    IDataset pSubDataset = pESubDataset.next();
                    while (pSubDataset != null) {
                        if (pSubDataset.getType() == esriDatasetType.esriDTFeatureClass) {
                            if (pSubDataset.getName().contains(sCode)) {
                                pFeaCls = pFeatureWorkspace.openFeatureClass(pDataset.getName());

                                break;
                            }
                        }
                        pSubDataset = pESubDataset.next();
                    }
                }
                pDataset = pEDataset.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LoggerClass.error(e.toString());
        }

        return pFeaCls;
    }

}

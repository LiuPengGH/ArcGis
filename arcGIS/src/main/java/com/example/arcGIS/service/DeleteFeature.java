package com.example.arcGIS.service;

import com.esri.arcgis.geodatabase.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DeleteFeature {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFeature.class);

    public static boolean deleteFeatureClass(IWorkspace iWorkspace, IFeature iFeature){
        boolean b =false;

        if ( iFeature ==null)
        {
            System.out.println("3333333333");
            b = false;
        }
        try {
            IEnumDataset pEDataset = iWorkspace.getDatasets(esriDatasetType.esriDTAny);
            IDataset pDataset = pEDataset.next();
            if (pDataset.canDelete()){
                iFeature.delete();
                System.out.println("1111111111");
                b = true;
            }else {
                LOGGER.error("----不可删除！");
            }
        } catch (IOException e)
        {
            b= false;
            System.out.println("2222222222222");
        }
        return b;
    }
}

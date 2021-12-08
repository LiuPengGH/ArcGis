package cm.updataSZ.checkForUpdates;


import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.esriLicenseProductCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class initializeArcGISLicenses {

    private static final Logger LOGGER = LoggerFactory.getLogger(initializeArcGISLicenses.class);

    public static void InitializeArcGISLicenses() {

       // LOGGER.info("---初始化ArcGISLicenses---");
        EngineInitializer.initializeEngine();
        com.esri.arcgis.system.AoInitialize aoInitialize = null;
        try {
            aoInitialize = new com.esri.arcgis.system.AoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("初始化异常 ： " + e.toString());

        }
        try {
            aoInitialize.initialize(esriLicenseProductCode.esriLicenseProductCodeAdvanced);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("初始化异常 ： " + e.toString());

        }
        try {
            aoInitialize.initialize(esriLicenseProductCode.esriLicenseProductCodeEngineGeoDB);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("初始化异常 ： " + e.toString());
        }
    }
}

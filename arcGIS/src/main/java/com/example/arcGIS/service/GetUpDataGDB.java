package com.example.arcGIS.service;

import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IRow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author lp
 * @version 1.00
 * @Date 2020.11.1
 */

public class GetUpDataGDB {

    /**
     * @param iRow
     * @param pFeaCls
     * @param shape
     * @return
     * @throws IOException
     */
    public static String newDatas(IRow iRow, IFeatureClass pFeaCls, String shape, String status, String objid) throws IOException {

        IFields fields = pFeaCls.getFields();
        int fieldCount = fields.getFieldCount();
        StringBuilder str = new StringBuilder("[{");

        for (int i = 2; i < fieldCount - 2; i++) {

            IField field1 = fields.getField(i);
            String name = field1.getName();
            Object value = iRow.getValue(i);

            if (value != null) {
                if (name.equals("objName")) {
                    name = "ObjName";
                }
                if (name.equals("Note_")) {
                    name = "Note";
                }
                if (name.equals("ORDate") || name.equals("CHDate")) {
                    String format1;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(value.toString());
                    format1 = format.format(date);
                    value = format1;
                }
                str.append(",\"").append(name).append("\":").append("\"").append(value).append("\"");
            }
        }
        String LargeClass = objid.substring(6, 8);

        switch (LargeClass) {
            case "01":
                LargeClass = "公用设施";
                break;
            case "02":
                LargeClass = "交通设施";
                break;
            case "03":
                LargeClass = " 市容环境设施";
                break;
            case "04":
                LargeClass = "园林绿化设施";
                break;
            default:
                LargeClass = "其他部件";
                break;
        }
        String SmaValue = pFeaCls.getAliasName().split("_")[1];

        return str.substring(0, 2) + str.substring(3) + "," + shape + "," + "\"status\":" + status + "," + "\"SmallClass\":\"" + SmaValue + "\"," + "\"LargeClass\":\"" + LargeClass + "\"}]";
    }

}

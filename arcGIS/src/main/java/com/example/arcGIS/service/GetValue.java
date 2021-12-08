package com.example.arcGIS.service;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFields;

import java.io.IOException;

public class GetValue {


    public static String GetValue(IFeature Feature, String FieldName) throws IOException {
        if ((Feature == null) || (FieldName == null))
        {
            return null;
        }
        IFields fields = Feature.getFields();
        int index = fields.findField(FieldName);
        if (index < 0)
        {
            return null;
        }
        try
        {
           // esriFieldType type = fields.getField(index).getType();
            return Feature.getValue(index).toString().trim();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}

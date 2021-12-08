package com.example.arcGIS.pojo;

import lombok.Data;

@Data
public class ArcGIS {

    private String BelongXiaQuCode;
    private String OperateUserName;
    private String OperateDate;
    private String Row_ID;
    private String YearFlag;
    private String RowGuid;
    private String ObjID;
    private String ObjName;
    private String DeptCode1;
    private String DeptName1;
    private String DeptCode2;
    private String DeptName2;
    private String DeptCode3;
    private String DeptName3;
    private String BGID;
    private String ObjState;
    private String ORDate;
    private String CHDate;
    private String DataSource;
    private String Note;
    private String Material;
    private String PicAddress;
    private String LocateDSC;
    private String ispassed;
    private String operation;
    private String ispassedSZ;
    private String x;
    private String y;
    private String ldcode;
    private String gacode;

    public String toJson() {
        return "{" +
                "\"" +
                "BelongXiaQuCode" +
                "\":\"" +
                BelongXiaQuCode +
                "\"," +
                "\"" +
                "OperateUserName" +
                "\":\"" +
                OperateUserName +
                "\"," +
                "\"" +
                "OperateDate" +
                "\":\"" +
                OperateDate +
                "\"," +
                "\"" +
                "Row_ID" +
                "\":\"" +
                Row_ID +
                "\"," +
                "\"" +
                "YearFlag" +
                "\":\"" +
                YearFlag +
                "\"," +
                "\"" +
                "RowGuid" +
                "\":\"" +
                RowGuid +
                "\"," +
                "\"" +
                "ObjID" +
                "\":\"" +
                ObjID +
                "\"," +
                "\"" +
                "ObjName" +
                "\":\"" +
                ObjName +
                "\"," +
                "\"" +
                "DeptCode1" +
                "\":\"" +
                DeptCode1 +
                "\"," +
                "\"" +
                "DeptName1" +
                "\":\"" +
                DeptName1 +
                "\"," +
                "\"" +
                "DeptCode2" +
                "\":\"" +
                DeptCode2 +
                "\"," +
                "\"" +
                "DeptName2" +
                "\":\"" +
                DeptName2 +
                "\"," +
                "\"" +
                "DeptCode3" +
                "\":\"" +
                DeptCode3 +
                "\"," +
                "\"" +
                "DeptName3" +
                "\":\"" +
                DeptName3 +
                "\"," +
                "\"" +
                "BGID" +
                "\":\"" +
                BGID +
                "\"," +
                "\"" +
                "ObjState" +
                "\":\"" +
                ObjState +
                "\"," +
                "\"" +
                "ORDate" +
                "\":\"" +
                ORDate +
                "\"," +
                "\"" +
                "CHDate" +
                "\":\"" +
                CHDate +
                "\"," +
                "\"" +
                "DataSource" +
                "\":\"" +
                DataSource +
                "\"," +
                "\"" +
                "Note" +
                "\":\"" +
                Note +
                "\"," +
                "\"" +
                "Material" +
                "\":\"" +
                Material +
                "\"," +
                "\"" +
                "PicAddress" +
                "\":\"" +
                PicAddress +
                "\"," +
                "\"" +
                "LocateDSC" +
                "\":\"" +
                LocateDSC +
                "\"," +
                "\"" +
                "operation" +
                "\":\"" +
                operation +
                "\"," +
                "\"" +
                "ispassedSZ" +
                "\":\"" +
                ispassedSZ +
                "\"," +
                "\"" +
                "ispassed" +
                "\":\"" +
                ispassed +
                "\"," +
                "\"" +
                "x" +
                "\":\"" +
                x +
                "\"," +
                "\"" +
                "y" +
                "\":\"" +
                y +
                "\"," +
                "\"" +
                "ldcode" +
                "\":\"" +
                ldcode +
                "\"," +
                "\"" +
                "gacode" +
                "\":\"" +
                gacode +
                "\"" +
                "}";
    }
}

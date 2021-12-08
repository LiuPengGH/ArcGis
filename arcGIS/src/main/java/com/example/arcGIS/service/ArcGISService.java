package com.example.arcGIS.service;

import com.alibaba.fastjson.JSONObject;
import com.example.arcGIS.dao.ArcGISDao;
import com.example.arcGIS.pojo.ArcGIS;
import com.example.arcGIS.request.CommunalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


//定义service
@Service
public class ArcGISService {

    //加载DAO
    @Resource
    private final ArcGISDao arcGISDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISService.class);

    public ArcGISService(ArcGISDao arcGISDao) {
        this.arcGISDao = arcGISDao;
    }

    public String getGISData(CommunalRequest communalRequest) throws Exception {


        String sROWGUID = communalRequest.getRowGuid();
        ArcGIS data = arcGISDao.getData(sROWGUID);
        String objid;
        String json = data.toJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String ispassed = jsonObject.getString("ispassed");
        String sOBJID = data.getObjID();
        String operation = data.getOperation();

        switch (operation) {

            case "1":

                if (!ispassed.equals("1") | ispassed.isEmpty()) {

                    String ojbID = ArcGISInsertAdd.arcGISUpData(data, sOBJID, sROWGUID);
                    LOGGER.error(sOBJID + "is = ");
                    int i = arcGISDao.upDataAdd(sROWGUID);
                    String sql = "update  componentinfofront set ObjID='" + ojbID + "' where RowGuid='" + sROWGUID + "'";
                   try {
                       MysqlUpObjID.GetSqlVal(sql);
                   }catch (Exception e){
                       throw new Exception("同步失败");
                   }

                    if (i == 1)
                        objid = ojbID;
                    else
                        throw new Exception("同步失败");
                } else {
                    throw new Exception("已同步-" + sOBJID);
                }
                break;

            case "2":

                if (!ispassed.equals("2") | ispassed.isEmpty()) {
                    boolean b = ArcGISUpData.arcGISUpData(data, sOBJID, sROWGUID);
                    if (b) {
                        int i = arcGISDao.upData(sROWGUID);
                        if (i == 1)
                            objid = sOBJID;
                        else
                            throw new Exception("同步失败");
                    } else {
                        throw new Exception("同步失败");
                    }

                } else
                    throw new Exception("已同步-" + sOBJID);
                break;

            case "3":

                if (!ispassed.equals("3") | ispassed.isEmpty()) {
                    boolean b = ArcGISDelete.arcGISUpData(data, sOBJID, sROWGUID);

                    if (b) {
                        int i = arcGISDao.upDataDelete(sROWGUID);
                        if (i == 1)
                            objid = sOBJID;
                        else
                            throw new Exception("同步失败");
                    } else {
                        throw new Exception("同步失败");
                    }
                } else {
                    throw new Exception("已同步-" + sOBJID);
                }
                break;
            default:
                throw new Exception("同步失败");
        }

        return objid;
    }
}

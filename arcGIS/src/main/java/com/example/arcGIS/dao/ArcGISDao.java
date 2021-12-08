package com.example.arcGIS.dao;

import com.example.arcGIS.pojo.ArcGIS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArcGISDao {

    /**
     * 根据RowGuid获取数据
     *
     * @param RowGuid
     * @return
     */
    @Select("<script>" +
            " select * from componentinfofront " +
            " where RowGuid=#{RowGuid}" +
            "</script>")
    ArcGIS getData(@Param("RowGuid") String RowGuid);

    /**
     * 更新状态
     *
     * @param RowGuid
     * @return
     */
    //update  componentinfofront_copy2 set ispassed=2 where rowguid=
    @Update("<script>" +
            " update  componentinfofront set" +
            " ispassed=2 " +
            " where RowGuid=#{RowGuid}" +
            "</script>")
    int upData(@Param("RowGuid") String RowGuid);

    /**
     * 新增更新状态
     *
     * @param RowGuid
     * @return
     */
    //update  componentinfofront_copy2 set ispassed=2 where rowguid=
    @Update("<script>" +
            " update  componentinfofront set" +
            " ispassed=1 " +
            " where RowGuid=#{RowGuid}" +
            "</script>")
    int upDataAdd(@Param("RowGuid") String RowGuid);


    /**
     * 删除状态更新
     *
     * @param RowGuid
     * @return
     */
    @Update("<script>" +
            " update  componentinfofront set" +
            " ispassed=3 " +
            " where RowGuid=#{RowGuid}" +
            "</script>")
    int upDataDelete(@Param("RowGuid") String RowGuid);


    /**
     * ObjID更新
     *
     * @param RowGuid
     * @return
     */
    @Update("<script>" +
            " update  componentinfofront set" +
            " ispassed=3 " +
            " where RowGuid=#{RowGuid}" +
            "</script>")
    int upObjID(@Param("RowGuid") String RowGuid);



    @Select("<script>" +
            " SELECT SUBSTRING(ObjID,11,6) as maxOjb " +
            " FROM `componentinfofront`  where SUBSTRING(ObjID,1,10) = #{ojbid} " +
            " AND length(ObjID)=16 ORDER BY SUBSTRING(ObjID,11,6) DESC LIMIT 1" +
            "</script>")
    String getMaxOjbID(@Param("ojbid") String ojbid);


}

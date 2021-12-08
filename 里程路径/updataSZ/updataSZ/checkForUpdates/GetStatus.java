package cm.updataSZ.checkForUpdates;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取status字段
 */

public class GetStatus {

    /**
     * @param
     * @return
     */
    public static String getStatus(JSONObject jsonObject) {

        String status;
        status = jsonObject.getString("operation");

        return "\"" + "0" + status + "\"";
    }
}

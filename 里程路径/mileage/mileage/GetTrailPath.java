package cm.mileage;

public class GetTrailPath {
    public static String getTrailPath(String sPhone, String sStratTime, String sStopTime) {

        String sSql = "";
        int iRe = CheckMobliePhone.CheckMobliePhoneV2(sPhone);
        if (iRe == 1)//电信
        {
            sSql = " select * from ( ";
            sSql += " select distinct mobile_phone,'LBS' as type, DATE_FORMAT(date, '%Y-%m-%d %H:%i:%s') as date,gisx,gisy  from t_mobile_user_position ";
            sSql += " where gisx>0 and mobile_phone='" + sPhone + "'";
            sSql += " and date  between '" + sStratTime + "' and  '" + sStopTime + "' ) as a";
        } else if (iRe == 2)//联通
        {
            sSql = " select * from ( ";
            sSql += " select distinct mobilephone,'LBS' as type, DATE_FORMAT(date, '%Y-%m-%d %H:%i:%s') as date,gisx,gisy  from mobile_userposition ";
            sSql += " where gisx>0 and mobilephone='" + sPhone + "'";
            sSql += " and date  between '" + sStratTime + "' and  '" + sStopTime + "') as a ";
        } else if (iRe == 3)//移动
        {
            sSql = " select * from ( ";
            sSql += " select distinct mobilephone,'LBS' as type, DATE_FORMAT(date, '%Y-%m-%d %H:%i:%s') as date,gisx,gisy  from cwt_position ";
            sSql += " where gisx>0 and mobilephone='" + sPhone + "'";
            sSql += " and date between '" + sStratTime + "' and  '" + sStopTime + "') as a";
        }

        sSql += " union all ";
        sSql += " select distinct mobilephone, 'GPS' as type, DATE_FORMAT(date, '%Y-%m-%d %H:%i:%s') as date,gisx,gisy  from mobile_userposition_mobilegis ";
        sSql += " where gisx>0 and mobilephone='" + sPhone + "'";
        sSql += " and date  between '" + sStratTime + "' and  '" + sStopTime + "' ";


        return sSql;
    }
}

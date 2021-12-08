package cm.mileagePath;

public class PreviousDate {
    public static String previousDate(String phone,String format,String dateNew){

        return GetTrailPath.getTrailPath(phone, format + " 08:30:00", dateNew);
    }

}

package cm.mileage;

public class CheckMobliePhone {


    public static int CheckMobliePhoneV2(String phone){

        int iRe = 0; ;
        //移动号段
        String[] Mobile = "134,135,136,137,138,139,147,150,151,152,157,158,159,178,182,183,184,187,188,1705".split(",");
        //联通号段
        String[] Unicom = "130,131,132,145,155,156,176,185,186,1707,1708,1709".split(",");
        //电信号段
        String[] Telecom = "133,153,173,177,180,181,189,199,1700".split(",");

        String type4 = phone.substring(0, 4);   //tel是用户输入的手机号,取前三位,就是号段
        String type3 = phone.substring(0, 3);   //tel是用户输入的手机号,取前三位,就是号段

        for(String s: Telecom){
            if (s.equals(type3) || s.equals(type4)) {
                iRe = 1;
                break;
            }
        }
        for(String s: Unicom){
            if (s.equals(type3)|| s.equals(type4)) {
                iRe = 2;
                break;
            }
        }
        for(String s: Mobile){
            if (s.equals(type3)|| s.equals(type4)) {
                iRe = 3;
                break;
            }
        }
        return iRe;
    }

}

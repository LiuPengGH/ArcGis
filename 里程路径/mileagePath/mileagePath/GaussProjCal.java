package cm.mileagePath;

public class GaussProjCal {

    //120
    public static  double[] gaussProjCal(double longitude, double latitude, double zyjx)
    {
        int ProjNo = 0; int ZoneWide;   ////带宽
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;
        double a, f, e2, ee, NN, T, C, A, M, iPI;
        iPI = 0.0174532925199433;   ////3.1415926535898/180.0;
        ZoneWide = 3;     ////3度带宽
        ////a = 6378245.0; f = 1.0 / 298.3;      //54年北京坐标系参数
        a = 6378140.0; f = 1 / 298.257;      //80年西安坐标系参数
        ProjNo = (int)(longitude / ZoneWide);
        longitude0 = zyjx;
        longitude0 = longitude0 * iPI;
        longitude1 = longitude * iPI;   //经度转换为弧度
        latitude1 = latitude * iPI;     //纬度转换为弧度
        e2 = 2 * f - f * f;
        ee = e2 * (1.0 - e2);
        NN = a / Math.sqrt((1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1)));
        T = Math.tan(latitude1) * Math.tan(latitude1);
        C = ee * Math.cos(latitude1) * Math.cos(latitude1);
        A = (longitude1 - longitude0) * Math.cos(latitude1);
        M = a * ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1 - (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2 / 1024) * Math.sin(2 * latitude1)
                + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024) * Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072) * Math.sin(6 * latitude1));
        xval = NN * (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A * A * A * A * A / 120);
        yval = M + NN * Math.tan(latitude1) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24
                + (61 - 58 * T + T * T + 600 * C - 330 * ee) * A * A * A * A * A * A / 720);
        X0 = 1000000L * (ProjNo + 1) + 500000L;
        X0 = 500000L; ///JJR地方坐标无大数
        Y0 = 0;
        xval = xval + X0; yval = yval + Y0;
        double xval1 = xval;
        double yval1 = yval;
        return new double[]{xval1,yval1};
    }
}

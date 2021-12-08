package cm.updataSZ.checkForUpdates;

import com.esri.arcgis.geometry.*;

public class CreatePolygon {

    /// <summary>
    /// 创建面IPolygon
    /// </summary>
    /// <param name="dXMin"></param>
    /// <param name="dXMax"></param>
    /// <param name="dYMin"></param>
    /// <param name="dYMax"></param>
    /// <returns></returns>
    public static IPolygon CreatePolygon(double dXMin, double dXMax, double dYMin, double dYMax) {

        try {

            IPolygon polygon = new Polygon();
            IPoint inPoint = new Point();
            inPoint.putCoords(dXMin, dYMin);
            ((IPointCollection) polygon).addPoint(inPoint, null, null);
            inPoint = new Point();
            inPoint.putCoords(dXMin, dYMax);
            ((IPointCollection) polygon).addPoint(inPoint, null, null);
            inPoint = new Point();
            inPoint.putCoords(dXMax, dYMax);
            ((IPointCollection) polygon).addPoint(inPoint, null, null);
            inPoint = new Point();
            inPoint.putCoords(dXMax, dYMin);
            ((IPointCollection) polygon).addPoint(inPoint, null, null);
            inPoint = new Point();
            inPoint.putCoords(dXMin, dYMin);
            ((IPointCollection) polygon).addPoint(inPoint, null, null);
            return polygon;

        }catch (Exception e ){
            System.out.println("null-----");
            return null;
        }
    }
}

package cm.checkForUpdates;

import com.esri.arcgis.geometry.*;

import java.io.IOException;
import java.util.List;

public class CreatePolyline  {

    public static IPolyline CreatePolyline(List<IPoint> PolylineList) throws IOException {
        ISegment pSegment;
        ILine pLine;
        //object o = Type.Missing;
        ISegmentCollection pPath = new Path();
        for (int i = 0; i < PolylineList.size() - 1; i++)
        {
            pLine =  CreateLine.createLine(PolylineList.get(i), PolylineList.get(i +1));
            pSegment =(ISegment)pLine;
            pPath.addSegment(pSegment, null, null);

        }
        IGeometryCollection pPolyline = new Polyline();
        pPolyline.addGeometry((IGeometry)pPath,null,null);
        return   (IPolyline)pPolyline;
    }

}

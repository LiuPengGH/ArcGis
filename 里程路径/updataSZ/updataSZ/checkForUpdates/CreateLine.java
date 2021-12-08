package cm.updataSZ.checkForUpdates;

import com.esri.arcgis.geometry.ILine;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.Line;

import java.io.IOException;

public class CreateLine {

    public static ILine createLine(IPoint from, IPoint to) throws IOException {
        ILine pLine = new Line();
        pLine.putCoords(from, to);
        return pLine;
    }

}

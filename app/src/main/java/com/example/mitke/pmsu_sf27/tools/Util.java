package com.example.mitke.pmsu_sf27.tools;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.sqrt;

/**
 * Created by mitke on 13-Jan-17.
 */

public class Util {
    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6371;
        double dLat = (lat2 - lat1) * PI / 180;
        double dLon = (lon2 - lon1) * PI / 180;
        lat1 = lat1 * PI / 180;
        lat2 = lat2 * PI / 180;

        double a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2);
        double c = 2 * atan2(sqrt(a), sqrt(1-a));
        double d = R * c;
        return d;
    }
}

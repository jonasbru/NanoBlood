package mapdetouring;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jammers
 */
public class MapDetouring {

    private static Set<String> ptsSet = new HashSet<String>();
    private final static double DIVIDER_Y = 0.5631250143051147;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: mapDetouring JSONFile");
            System.out.println(args.length);
            System.exit(0);
        }
//        BufferedImage im = ImageIO.read(new File(args[1]));
//        Raster r = im.getData();
//        for (int i = 0; i < im.get; i++) {
//            
//        }
        JSONParser parser = new JSONParser();

        try {

            //* Reading the JSON
            Object obj = parser.parse(new FileReader(args[0]));
            String fname = "result.map";

            JSONObject jsonObject = (JSONObject) obj;

            Object[] msg = ((JSONArray) jsonObject.get("rigidBodies")).toArray();

            JSONObject a = (JSONObject) msg[0];
            JSONArray pointsLst = (JSONArray) ((JSONArray) a.get("polygons"));
            if (pointsLst != null) {
                for (Object o : pointsLst) {
                    JSONArray points = (JSONArray) o;
                    for (Object point : points) {
                        JSONObject js = (JSONObject) point;
                        Object[] u = js.values().toArray();
                        double y = -1.0;
                        if (u[0] instanceof Long) {
                            y = ((Long) u[0]).doubleValue();
                        } else {
                            y = (Double) u[0];
                        }
                        double x = -1.0;
                        if (u[1] instanceof Long) {
                            y = ((Long) u[1]).doubleValue();
                        } else {
                            x = (Double) u[1];
                        }
                        ptsSet.add(y / DIVIDER_Y + " " + x);//"Y X"
                    }
                }
            }

            JSONArray pointsLst2 = (JSONArray) ((JSONArray) a.get("shapes"));
            if (pointsLst2 != null) {
                
                for (Object o : pointsLst2) {
                    JSONArray points = (JSONArray)((JSONObject)o).get("vertices");
                    for (Object point : points) {
                        JSONObject js = (JSONObject) point;
                        Object[] u = js.values().toArray();
                        double y = -1.0;
                        if (u[0] instanceof Long) {
                            y = ((Long) u[0]).doubleValue();
                        } else {
                            y = (Double) u[0];
                        }
                        double x = -1.0;
                        if (u[1] instanceof Long) {
                            y = ((Long) u[1]).doubleValue();
                        } else {
                            x = (Double) u[1];
                        }
                        ptsSet.add(y / DIVIDER_Y + " " + x);//"Y X"
                    }
                }
            }



            JSONArray pointsLst3 = (JSONArray) ((JSONArray) a.get("circles"));
            if (null != pointsLst3) {
                for (Object o : pointsLst3) {
                    JSONArray points = (JSONArray) o;
                    for (Object point : points) {
                        JSONObject js = (JSONObject) point;
                        Object[] u = js.values().toArray();
                        double y = -1.0;
                        if (u[0] instanceof Long) {
                            y = ((Long) u[0]).doubleValue();
                        } else {
                            y = (Double) u[0];
                        }
                        double x = -1.0;
                        if (u[1] instanceof Long) {
                            y = ((Long) u[1]).doubleValue();
                        } else {
                            x = (Double) u[1];
                        }
                        ptsSet.add(y / DIVIDER_Y + " " + x);//"Y X"
                    }
                }
            }

            //* Found the points, looping on them


            //* Writing the actual file
            FileWriter f = new FileWriter(fname);
            for (String s : ptsSet) {
                f.write(s + "\n");
            }
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

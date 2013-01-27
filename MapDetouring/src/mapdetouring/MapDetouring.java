package mapdetouring;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jammers
 */
public class MapDetouring {
    private static List<String> ptsList = new ArrayList<String>(1000);

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
            String fname = args[0].replace(".png", ".map");

            JSONObject jsonObject = (JSONObject) obj;

            Object[] msg = ((JSONArray) jsonObject.get("rigidBodies")).toArray();
            
            JSONObject a = (JSONObject) msg[0];
            JSONArray points = (JSONArray)((JSONArray) a.get("polygons")).get(0);
            //* Found the points, looping on them
            for (Object point : points) {
                JSONObject js = (JSONObject)point;
                Object[] u = js.values().toArray();
                ptsList.add((String)u[0] + " " + (String)u[1]);
            }
            
            //* Writing the actual file
            FileWriter f = new FileWriter("../sprites/" + fname);
            for (String s : ptsList) {
                f.write(s + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

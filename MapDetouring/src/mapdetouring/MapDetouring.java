package mapdetouring;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jammers
 */
public class MapDetouring {

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

            Object obj = parser.parse(new FileReader(args[0]));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("polygons");
            System.out.println(name);

//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);
//
//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("messages");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

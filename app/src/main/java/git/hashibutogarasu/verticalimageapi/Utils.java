package git.hashibutogarasu.verticalimageapi;

import java.util.*;

public class Utils {
    public static Map<String, String> getQueryMap(String query){
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String[] splitted = param.split("=");
            map.put(splitted[0], splitted[1]);
        }
        return map;
    }
}

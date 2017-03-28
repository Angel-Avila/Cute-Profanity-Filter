package Utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Angel on 3/28/17.
 */
public class Utils {

    public static String getShortenedDir(String dir) {

        if(dir.length() >= 52) {
            int toTrim = dir.length() - 55;
            StringBuilder temp = new StringBuilder();
            dir = dir.substring(toTrim, dir.length());
            temp.append("..." + dir);
            dir = temp.toString();
        }

        return dir;
    }

    public static String getTAFormattedString(String str) {
        StringBuilder sb = new StringBuilder();

        boolean jumped = false;

        for(int i = 0; i < str.length(); i++) {
            if(jumped && str.charAt(i) == ' ' && i+1 < str.length())
                i++;

            sb.append(str.charAt(i));

            if(i % 51 == 0 && i != 0) {
                if(Character.isLetter(str.charAt(i)))
                    sb.append("-\n");
                 else
                    sb.append("\n");

                jumped = true;
            } else
                jumped = false;


        }

        return sb.toString();
    }

    public static String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }
}

package systemdesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class TestFile {

    public static void main(String[] args) throws Exception {
        File f = new File("");

        System.out.println(f.getAbsolutePath());

        FileReader fr = new FileReader(f);

        BufferedReader br = new BufferedReader(fr);
        String line = "";
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }

    }

}

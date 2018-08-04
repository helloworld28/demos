import java.io.*;

/**
 * Created by Jim on 2017/11/3.
 */
public class ReadCmdOutTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:/cmd.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/cmd.txt"));
        String line = null;
        while (( line = bufferedReader.readLine() )!=null){
            System.out.println(line);
        }
    }
}

import java.io.File;

public class Test {
    public static void main(String[] args){
        File dir = new File("C:/Temp");

        File[] f = dir.listFiles();

        for (int i = 0; i < f.length; i++){
            if (f[i].length() > 1000 && f[i].getName().endsWith("exe")) {
                System.out.println(f[i].getName().substring(0, 3));
            }

        }
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {
    public static void main(String[] args) throws IOException {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter("test.txt"))) {
            Random rd = new Random();
            int c_nums = 10000;
            writter.write(c_nums + "\n");

            int[] array = rd.ints(c_nums, 1, 100000000).toArray();

//            int[] array = new int[c_nums];
//            for (int i = 0; i < c_nums; i++) {
//                array[i] = i + 1;
//            }

            for (int j = 0; j < c_nums; j++) {
                writter.write(array[j] + " ");
            }
        }

    }
}
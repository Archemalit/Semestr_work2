import java.io.*;

public class Main {
    public static void main(String[] args) {
        BinomialHeap binHeap = new BinomialHeap();

        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            try (BufferedWriter writter = new BufferedWriter(new FileWriter("result.txt"))) {
                int n = Integer.parseInt(reader.readLine());

                int [] array = new int[n];

                for (int i = 0; i < n; i++) {
                    array[i] = Integer.parseInt(reader.readLine());
                }
                long averageTimeOfInsert = 0;
                long averageIterationsOfInsert = 0;
                writter.write("Время добавления в наносекундах:\n");
                for (int i = 0; i < n; i++) {
                    long start = System.nanoTime();

                    binHeap.insert(array[i]);

                    long end = System.nanoTime();
                    long time = end - start;
                    averageTimeOfInsert += time;
                    averageIterationsOfInsert += binHeap.getIterations();
                    writter.write(time + " ");
                }
                averageTimeOfInsert = averageTimeOfInsert / n;
                averageIterationsOfInsert = averageIterationsOfInsert / n;

                writter.write("\nСреднее время добавления:\n");
                writter.write(averageTimeOfInsert + "");

                writter.write("\nСреднее кол-во итераций добавления:\n");
                writter.write(averageIterationsOfInsert + "");
                writter.write("\n");

                writter.write("Время нахождения и удаления минимвльного элемента в наносекундах:\n");

                long averageTimeOfExtractMin = 0;
                long averageIterationsOfExtractMin = 0;

                for (int i = 0; i < n / 10; i++) {
                    long start = System.nanoTime();

                    binHeap.extractMin();

                    long end = System.nanoTime();
                    long time = end - start;
                    averageTimeOfExtractMin += time;
                    averageIterationsOfExtractMin += binHeap.getIterations();
                    writter.write(time + " ");
                }
                averageTimeOfExtractMin = averageTimeOfExtractMin / (n / 10);
                averageIterationsOfExtractMin = averageIterationsOfExtractMin / (n / 10);

                writter.write("\nСреднее время удаления минимального элемента:\n");
                writter.write(averageTimeOfExtractMin + "");

                writter.write("\nСреднее кол-во операций удаления минимального элемента:\n");
                writter.write(averageIterationsOfExtractMin + "");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
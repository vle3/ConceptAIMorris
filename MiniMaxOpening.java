import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MiniMaxOpening {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: ProgramName input.txt output.txt depthNo");
        }

        String inputFile = args[0];
        String outputFile = args[1];
        int depth = Integer.parseInt(args[2]);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write("Input State: " + line +"\n");
                writer.write("Output State: " + line);
                writer.newLine();
                writer.write("depth: " + depth);
            }

            System.out.println("File has been successfully read and written to output.txt");

        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        }
    }
}
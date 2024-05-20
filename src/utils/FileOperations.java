package utils;
import java.io.*;

public class FileOperations {
    public static void writeIntegerToFile(String fileName, int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(Integer.toString(number));
            //System.out.println("Successfully written " + number + " to the file.");
        } catch (IOException e) {
            //System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static int readIntegerFromFile(String fileName) {
        int result = 0; // Default value if nothing is read

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line != null) {
                result = Integer.parseInt(line);
                //System.out.println("Successfully read " + result + " from the file.");
            } else {
                return -1;
                //System.err.println("The file is empty.");
            }
        } catch (IOException e) {
            //System.err.println("An error occurred while reading from the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            //System.err.println("The file does not contain a valid integer: " + e.getMessage());
        }

        return result;
    }


}

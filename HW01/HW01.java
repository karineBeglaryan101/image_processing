import ij.*;
import ij.process.*;
import ij.plugin.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class HW01 implements PlugIn {
    public void run(String arg) {
        int width = 682; 
    	//got the number from the first file, last line, didnt understand if code was needed so just manually took it
        int height = 682;

        ByteProcessor binaryProcessor = new ByteProcessor(width, height);
        binaryProcessor.setColor(255); 
        binaryProcessor.fill();

        String filePath = "/home/karine-beglaryan/Desktop/image_processing/image_processing/HW01/car-s-91.stu";
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            String line;


            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                String[] tokens = line.split(" ");

                if (tokens.length == 1) {
                    continue;
                }

                for (int i = 0; i < tokens.length-2; i += 2) {
                    int x = (int) Double.parseDouble(tokens[i]);
                    int y = (int) Double.parseDouble(tokens[i + 1]);


                    binaryProcessor.set(x, y, 0); 
                    binaryProcessor.set(y, x, 0);
                }
            }

            ImagePlus resultImage = new ImagePlus("Task 1 Image", binaryProcessor);
            resultImage.show();
            scanner.close();
        } catch (FileNotFoundException e) {
            IJ.log("Error reading the file: " + e.getMessage());
        }
    }
}


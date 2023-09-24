import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.Blitter;

public class Task2 implements PlugInFilter {

    public int setup(String args, ImagePlus im) {
        return DOES_ALL;
    }

    public void run(ImageProcessor inputIP) {
        int width = inputIP.getWidth();
        int height = inputIP.getHeight();

        int leftWidth = width / 2;
        int rightWidth = width / 2;
        int heightTopBottom = height / 2;

        ImageProcessor left = inputIP.duplicate();
        ImageProcessor right = inputIP.duplicate();

        left.setRoi(0, 0, leftWidth, height);
        right.setRoi(leftWidth, 0, rightWidth, height);

        left = left.crop();
        right = right.crop();

        swapPixels(left, right);

        ImageProcessor top = inputIP.duplicate();
        ImageProcessor bottom = inputIP.duplicate();

        top.setRoi(0, 0, width, heightTopBottom);
        bottom.setRoi(0, heightTopBottom, width, height - heightTopBottom);

        top = top.crop();
        bottom = bottom.crop();

        swapPixels(top, bottom);

        inputIP.copyBits(left, 0, 0, Blitter.COPY);
        inputIP.copyBits(right, leftWidth, 0, Blitter.COPY);
        inputIP.copyBits(top, 0, 0, Blitter.COPY);
        inputIP.copyBits(bottom, 0, heightTopBottom, Blitter.COPY);
    }

    private void swapPixels(ImageProcessor ip1, ImageProcessor ip2) {
        int width = ip1.getWidth();
        int height = ip1.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel1 = ip1.getPixel(i, j);
                int pixel2 = ip2.getPixel(i, j);
                ip1.putPixel(i, j, pixel2);
                ip2.putPixel(i, j, pixel1);
            }
        }
    }
}

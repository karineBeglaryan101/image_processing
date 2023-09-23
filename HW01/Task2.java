import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Task2 implements PlugInFilter {

    @Override
    public int setup(String arg, ImagePlus imp) {
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();

        int midX = width / 2;
        int midY = height / 2;

        ImageProcessor leftPanel = ip.duplicate();
        ImageProcessor rightPanel = ip.duplicate();
	if (width % 2 == 0) {
            leftPanel.setRoi(0, 0, midX + 1, height);
            rightPanel.setRoi(midX + 1, 0, midX - 1, height);
        } else {
            leftPanel.setRoi(0, 0, midX, height);
            rightPanel.setRoi(midX, 0, width - midX, height);
        }

        leftPanel = leftPanel.crop();
        rightPanel = rightPanel.crop();

        leftPanel.flipHorizontal();
        rightPanel.flipHorizontal();

        ImageProcessor topPanel = ip.duplicate();
        ImageProcessor bottomPanel = ip.duplicate();

        if (height % 2 == 0) {
            topPanel.setRoi(0, 0, width, midY + 1);
            bottomPanel.setRoi(0, midY + 1, width, midY - 1);
        } else {
            topPanel.setRoi(0, 0, width, midY);
            bottomPanel.setRoi(0, midY, width, height - midY);
        }

        topPanel = topPanel.crop();
        bottomPanel = bottomPanel.crop();

        topPanel.flipVertical();
        bottomPanel.flipVertical();

        ip.insert(leftPanel, 0, 0);
        ip.insert(rightPanel, midX, 0);
        ip.insert(topPanel, 0, 0);
        ip.insert(bottomPanel, 0, midY);
    }
}


package com.txsing.izoomer.zoomlogic;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ImageScaleTestStub extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1518574788794973574L;
    private BufferedImage rawImg;
    private BufferedImage modImg;
    private Dimension mySize;
    private int ratio;
    private Raster raster;


    public BufferedImage ImageZoom(File f, int ratio) {
        try {
            rawImg = ImageIO.read(f);
            this.ratio = ratio;
            ColorModel cMD = rawImg.getColorModel();
            int[] inPixelsData = new int[rawImg.getWidth() * rawImg.getHeight()];
            getRGB(rawImg, 0, 0, rawImg.getWidth(), rawImg.getHeight(), inPixelsData);
            modImg = new BufferedImage(rawImg.getColorModel(), 
                    cMD.createCompatibleWritableRaster(rawImg.getWidth() * ratio, 
                            rawImg.getHeight() * ratio), cMD.isAlphaPremultiplied(), null);
            raster = rawImg.getRaster();
            ImageEffect imgScale = new BiCubicInterpolationScale(raster);
            int[] outPixelsData = imgScale.imgProcess(inPixelsData, rawImg.getWidth(),
                    rawImg.getHeight(), rawImg.getWidth() * ratio, rawImg.getHeight() * ratio);
            setRGB(modImg, 0, 0, modImg.getWidth(), modImg.getHeight(), outPixelsData);

        } catch (IOException e) {
            System.err.printf(e.getMessage());
        }
        return modImg;

    }

    public Dimension getPreferredSize() {
        return mySize;
    }

    public Dimension getMinimumSize() {
        return mySize;
    }

    public Dimension getMaximumSize() {
        return mySize;
    }

    public void getRGB(BufferedImage img, int x, int y, int width, int height, int[] pixelsData) {
        int type = img.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            img.getRaster().getDataElements(x, y, width, height, pixelsData);
        } else {
            img.getRGB(x, y, width, height, pixelsData, 0, img.getWidth());
        }
    }

    public void setRGB(BufferedImage img, int x, int y, int width, int height, int[] pixelsData) {
        int type = img.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            img.getRaster().setDataElements(x, y, width, height, pixelsData);
        } else {
            img.setRGB(x, y, width, height, pixelsData, 0, width);
        }
    }
}

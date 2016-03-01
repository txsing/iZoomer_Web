package com.txsing.izoomer.zoomlogic;
// http://www.paulinternet.nl/?page=bicubic
// http://en.wikipedia.org/wiki/Bicubic_interpolation

import java.awt.image.Raster;

public class BiCubicInterpolationScale implements ImageEffect {

    private int srcWidth;
    private int srcHeight;
    private Raster raster;
    private float maxGradient;
    private int threshold;

    public BiCubicInterpolationScale(Raster aRaster) {
        raster = aRaster;
        threshold = 80;
    }

    @Override
    public int[] imgProcess(int[] inPixelsData, int srcW, int srcH, int destW, int destH) {
        double[][][] input3DData = processOneToThreeDeminsion(inPixelsData, srcH, srcW);
        int[][][] outputThreeDeminsionData = new int[destH][destW][4];
        double[][] tempPixels = new double[4][4];
        float rowRatio = ((float) srcH) / ((float) destH);
        float colRatio = ((float) srcW) / ((float) destW);
        this.srcWidth = srcW;
        this.srcHeight = srcH;
        maxGradient = GradientM(srcW, srcH);
        for (int row = 0; row < destH; row++) {
            double srcRow = ((float) row) * rowRatio;
            double j = Math.floor(srcRow);
            double t = srcRow - j;
            for (int col = 0; col < destW; col++) {
                double srcCol = ((float) col) * colRatio;
                double k = Math.floor(srcCol);
                double u = srcCol - k;
                for (int i = 0; i < 4; i++) {
                    tempPixels[0][0] = getRGBValue(input3DData, j - 1, k - 1, i);
                    tempPixels[0][1] = getRGBValue(input3DData, j - 1, k, i);
                    tempPixels[0][2] = getRGBValue(input3DData, j - 1, k + 1, i);
                    tempPixels[0][3] = getRGBValue(input3DData, j - 1, k + 2, i);

                    tempPixels[1][0] = getRGBValue(input3DData, j, k - 1, i);
                    tempPixels[1][1] = getRGBValue(input3DData, j, k, i);
                    tempPixels[1][2] = getRGBValue(input3DData, j, k + 1, i);
                    tempPixels[1][3] = getRGBValue(input3DData, j, k + 2, i);

                    tempPixels[2][0] = getRGBValue(input3DData, j + 1, k - 1, i);
                    tempPixels[2][1] = getRGBValue(input3DData, j + 1, k, i);
                    tempPixels[2][2] = getRGBValue(input3DData, j + 1, k + 1, i);
                    tempPixels[2][3] = getRGBValue(input3DData, j + 1, k + 2, i);

                    tempPixels[3][0] = getRGBValue(input3DData, j + 2, k - 1, i);
                    tempPixels[3][1] = getRGBValue(input3DData, j + 2, k, i);
                    tempPixels[3][2] = getRGBValue(input3DData, j + 2, k + 1, i);
                    tempPixels[3][3] = getRGBValue(input3DData, j + 2, k + 2, i);

                    if (isBorder((int) k, (int) j)) {
                        outputThreeDeminsionData[row][col][i] = getPixelValue(getBorderValue(tempPixels, t, u));
                    } else {
                        outputThreeDeminsionData[row][col][i] = getPixelValue(getValue(tempPixels, t, u));
                    }
                }
            }
        }
        return convertToOneDim(outputThreeDeminsionData, destW, destH);
    }

    public double getRGBValue(double[][][] input3DData, double row, double col, int index) {
        if (col >= srcWidth) {
            col = srcWidth - 1;
        }

        if (col < 0) {
            col = 0;
        }

        if (row >= srcHeight) {
            row = srcHeight - 1;
        }

        if (row < 0) {
            row = 0;
        }
        return input3DData[(int) row][(int) col][index];
    }

    public int getPixelValue(double pixelValue) {
        return pixelValue < 0 ? 0 : pixelValue > 255.0d ? 255 : (int) pixelValue;
    }

    public double getBorderValue(double p[], double x) {
        double m = 0, n = 0;
        double r = Math.abs(p[1] - p[2]);
        if (r >= 33) {
            m = 1.23;
            n = 1.23;
        }
        if ((r >= 10) || (r < 33)) {
            m=n=1.23+(0.10 / 23.0) * (r - 10);
        }
        if (r < 10) {
            m = n = 1 + 0.023* r;
        }
        return p[1] + 0.5 * x * (m * (p[2] - p[0]) + x * (2 * m * p[0] + (n - 6) 
                * p[1] + (6 - 2 * m) * p[2] - n * p[3] + x * ((4 - n) * p[1] - m 
                * p[0] + (m - 4) * p[2] + n * p[3])));
    }

    public double getBorderValue(double p[][], double x, double y) {
        double[] arr = new double[4];
        arr[0] = getBorderValue(p[0], y);
        arr[1] = getBorderValue(p[1], y);
        arr[2] = getBorderValue(p[2], y);
        arr[3] = getBorderValue(p[3], y);
        return getBorderValue(arr, x);
    }

    public double getValue(double p[], double x) {
        return p[1] + 0.5 * x * (p[2] - p[0] + x * (2.0 * p[0] - 5.0 * p[1] + 4.0 
                * p[2] - p[3] + x * (3.0 * (p[1] - p[2]) + p[3] - p[0])));
    }

    public double getValue(double p[][], double x, double y) {
        double[] arr = new double[4];
        arr[0] = getValue(p[0], y);
        arr[1] = getValue(p[1], y);
        arr[2] = getValue(p[2], y);
        arr[3] = getValue(p[3], y);
        return getValue(arr, x);
    }

    /* <p> The purpose of this method is to convert the data in the 3D array of ints back into </p>
     * <p> the 1d array of type int. </p>
     * 
     */
    public int[] convertToOneDim(int[][][] data, int imgCols, int imgRows) {
        // Create the 1D array of type int to be populated with pixel data
        int[] oneDPix = new int[imgCols * imgRows * 4];

        // Move the data into the 1D array. Note the
        // use of the bitwise OR operator and the
        // bitwise left-shift operators to put the
        // four 8-bit bytes into each int.
        for (int row = 0, cnt = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                oneDPix[cnt] = ((data[row][col][0] << 24) & 0xFF000000)
                        | ((data[row][col][1] << 16) & 0x00FF0000)
                        | ((data[row][col][2] << 8) & 0x0000FF00)
                        | ((data[row][col][3]) & 0x000000FF);
                cnt++;
            }// end for loop on col

        }// end for loop on row

        return oneDPix;
    }// end convertToOneDim

    private double[][][] processOneToThreeDeminsion(int[] oneDPix2, int imgRows, int imgCols) {
        double[][][] tempData = new double[imgRows][imgCols][4];
        for (int row = 0; row < imgRows; row++) {

            // per row processing
            int[] aRow = new int[imgCols];
            for (int col = 0; col < imgCols; col++) {
                int element = row * imgCols + col;
                aRow[col] = oneDPix2[element];
            }

            // convert to three dimension data
            for (int col = 0; col < imgCols; col++) {
                tempData[row][col][0] = (aRow[col] >> 24) & 0xFF; // alpha
                tempData[row][col][1] = (aRow[col] >> 16) & 0xFF; // red
                tempData[row][col][2] = (aRow[col] >> 8) & 0xFF;  // green
                tempData[row][col][3] = (aRow[col]) & 0xFF;       // blue
            }
        }
        return tempData;
    }

    @Override
    public int[] imgEffect(int[] inPixels, int srcW, int srcH) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isBorder(int x, int y) {
        int i;
        float scaleFactor = 255.0f / maxGradient;
        float gradient = (float) (Gradient(x, y));

        if (Math.round(scaleFactor * gradient) >= threshold) {
            i = 1;
        } else {
            i = 0;
        }
        return i == 1 ? true : false;
    }

    protected float GradientM(int w, int h) {
        int gx;
        float mag = 0, temp;
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                gx = Gradient(x, y);
                temp = (float) gx;
                mag = (temp > mag ? temp : mag);
            }
        }

        return mag;
    }

    protected final int Gradient(int x, int y) {
        int ir;
        int ig;
        int ib;
        
        if (x < 1) {
            x = 1;
        }
        if (x > srcWidth - 2) {
            x = srcWidth - 2;
        }
        if (y < 1) {
            y = 1;
        }
        if (y > srcHeight - 2) {
            y = srcHeight - 2;
        }
        
        ir = Math.abs(raster.getSample(x, y, 0)
                - raster.getSample(x + 1, y + 1, 0))
                + Math.abs(raster.getSample(x + 1, y, 0)
                - raster.getSample(x, y + 1, 0));
        ig = Math.abs(raster.getSample(x, y, 1)
                - raster.getSample(x + 1, y + 1, 1))
                + Math.abs(raster.getSample(x + 1, y, 1)
                - raster.getSample(x, y + 1, 1));
        ib = Math.abs(raster.getSample(x, y, 2)
                - raster.getSample(x + 1, y + 1, 2))
                + Math.abs(raster.getSample(x + 1, y, 2)
                - raster.getSample(x, y + 1, 2));
        return Math.abs(ir) + Math.abs(ig) + Math.abs(ib);
    }
}

package com.txsing.izoomer.logic;

public interface ImageEffect {
	public int[] imgProcess(int[] inPixelsData, int srcW, int srcH, int destW, int destH);
	public int[] imgEffect(int[] inPixels, int srcW, int srcH);
}

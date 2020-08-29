package com.nabiki.chart4j.parts;

import com.nabiki.chart4j.parts.exceptions.*;

import java.util.Objects;

public class Axis {
    private double priceMax, priceMin, blankPortion;
    private int pixelWidth;
    private int pixelHeight;
    private int pixelMarginTop;
    private int pixelMarginRight;
    private int pixelMarginBottom;
    private int pixelMarginLeft;

    private double[] verticalLabels = new double[0];
    private String[] horizontalLabels = new String[0];
    private double[] open = new double[0];
    private double[] high = new double[0];
    private double[] low = new double[0];
    private double[] close = new double[0];

    Axis() {
    }

    public void setOpen(double[] open) {
        this.open = open;
    }

    public void setHigh(double[] high) {
        this.high = high;
    }

    public void setLow(double[] low) {
        this.low = low;
    }

    public void setClose(double[] close) {
        this.close = close;
    }

    public double[] getOpen() {
        return open;
    }

    public double[] getHigh() {
        return high;
    }

    public double[] getLow() {
        return low;
    }

    public double[] getClose() {
        return close;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(double priceMax) {
        this.priceMax = priceMax;
        this.verticalLabels = null;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
        this.verticalLabels = null;
    }

    public double getBlankPortion() {
        return blankPortion;
    }

    public void setBlankPortion(double portion) throws InvalidPortionException {
        if (portion >= 1.0D || portion <= 0.0D)
            throw new InvalidPortionException("invalid portion: " + portion);
        this.blankPortion = portion;
    }

    private boolean priceCountMatch() {
        return horizontalLabels.length == open.length
                && open.length == high.length
                && high.length == low.length
                && low.length == close.length;
    }

    public int getCount() {
        if (horizontalLabels == null)
            throw new NullHorizontalLabelException("null horizontal labels");
        if (!priceCountMatch())
            throw new PriceCountNotMatchException("prices count not match");
        return horizontalLabels.length;
    }

    public void setHorizontalLabels(String[] labels) {
        this.horizontalLabels = labels;
    }

    public String[] getHorizontalLabels() {
        return this.horizontalLabels;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth(int pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public int getPixelMarginTop() {
        return pixelMarginTop;
    }

    public void setPixelMarginTop(int pixelMarginTop) {
        this.pixelMarginTop = pixelMarginTop;
    }

    public int getPixelMarginRight() {
        return pixelMarginRight;
    }

    public void setPixelMarginRight(int pixelMarginRight) {
        this.pixelMarginRight = pixelMarginRight;
    }

    public int getPixelMarginBottom() {
        return pixelMarginBottom;
    }

    public void setPixelMarginBottom(int pixelMarginBottom) {
        this.pixelMarginBottom = pixelMarginBottom;
    }

    public int getPixelMarginLeft() {
        return pixelMarginLeft;
    }

    public void setPixelMarginLeft(int pixelMarginLeft) {
        this.pixelMarginLeft = pixelMarginLeft;
    }

    public void pack() throws InvalidPriceRangeException {
        if (getCount() < 1)
            return;
        if (!validatePriceRange(priceMin, priceMax))
            throw new InvalidPriceRangeException("invalid price range [" + priceMin + ", " + priceMax + "]");
        this.verticalLabels = Utils.getLabels(this.priceMin, this.priceMax, 7);
    }

    public double[] getVerticalLabels() throws NullVerticalLabelException {
        Objects.requireNonNull(verticalLabels, "null vertical labels");
        return this.verticalLabels;
    }

    public double getMinLabelV() {
        Objects.requireNonNull(verticalLabels, "null vertical labels");
        return verticalLabels[0];
    }

    public double getMaxLabelV() {
        Objects.requireNonNull(verticalLabels, "null vertical labels");
        return verticalLabels[verticalLabels.length - 1];
    }

    private int fitPrice(double price) throws MarginOverflowException {
        double portion = Math.abs(getMaxLabelV() - price) / (getMaxLabelV() - getMinLabelV());
        return (int)Math.round(portion * getFitPixelHeight());
    }

    int getFitPixelHeight() throws MarginOverflowException {
        int fitPixelHeight = pixelHeight - pixelMarginTop - pixelMarginBottom;
        if (fitPixelHeight <= 0)
            throw new MarginOverflowException("y-margin overflow");
        return fitPixelHeight;
    }

    int getFitPixelWidth() throws MarginOverflowException {
        int fitPixelWidth = pixelWidth - pixelMarginLeft - pixelMarginRight;
        if (fitPixelWidth <= 0)
            throw new MarginOverflowException("x-margin overflow");
        return fitPixelWidth;
    }

    private boolean validatePriceRange(double min, double max) {
        return min < max;
    }

    private boolean validatePrice(double price) {
        return getMinLabelV() <= price && price <= getMaxLabelV();
    }

    public int y(double price) throws PriceOverflowException, MarginOverflowException {
        if (!validatePrice(price))
            throw new PriceOverflowException(
                    price + " not in [" + priceMin + ", " + priceMax + "]");
        return fitPrice(price) + pixelMarginTop;
    }

    private double getBlockWidth() throws MarginOverflowException {
        return 1.0D * getFitPixelWidth() / getCount();
    }

    public int fromX(int index) throws MarginOverflowException, IndexOverflowException {
        if (index >= getCount())
            throw new IndexOverflowException(index + ">=" + getCount());
        return (int)Math.round(getBlockWidth() * index) + getPixelMarginLeft();
    }

    public int toX(int index) throws MarginOverflowException, IndexOverflowException {
        return fromX(index) + (int)Math.round(getBlockWidth() * (1 - this.blankPortion));
    }
}

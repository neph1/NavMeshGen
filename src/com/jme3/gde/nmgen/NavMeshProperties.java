/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.nmgen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.critterai.nmgen.IntermediateData;
import org.critterai.nmgen.NavmeshGenerator;

/**
 *
 * @author rickard
 */
public class NavMeshProperties extends Properties{
    
    
    private org.critterai.nmgen.NavmeshGenerator nmgen;
    private float cellSize = 1f;
    private float cellHeight = 1.5f;
    private float minTraversableHeight = 7.5f;
    private float maxTraversableStep = 1f;
    private float maxTraversableSlope = 48.0f;
    private boolean clipLedges = false;
    private float traversableAreaBorderSize = 1.2f;
    private int smoothingThreshold = 2;
    private boolean useConservativeExpansion = true;
    private int minUnconnectedRegionSize = 3;
    private int mergeRegionSize = 10;
    private float maxEdgeLength = 0;
    private float edgeMaxDeviation = 2.4f;
    private int maxVertsPerPoly = 6;
    private float contourSampleDistance = 25;
    private float contourMaxDeviation = 25;

    public NavMeshProperties fromFile(String file) throws FileNotFoundException, IOException{
        load(new FileInputStream(file));
        cellSize = Float.parseFloat(getProperty("cellSize", "1.0"));
        cellHeight = Float.parseFloat(getProperty("cellHeight", "1.5"));
        minTraversableHeight = Float.parseFloat(getProperty("minTraversableHeight", "7.5"));
        maxTraversableStep = Float.parseFloat(getProperty("maxTraversableStep", "1"));
        maxTraversableSlope = Float.parseFloat(getProperty("maxTraversableSlope", "48"));
        clipLedges = Boolean.parseBoolean(getProperty("clipLedges", "false"));
        traversableAreaBorderSize = Float.parseFloat(getProperty("traversableAreaBorderSize", "1.2"));
        smoothingThreshold = Integer.parseInt(getProperty("smoothingThreshold", "2"));
        useConservativeExpansion = Boolean.parseBoolean(getProperty("useConservativeExpansion", "true"));
        minUnconnectedRegionSize = Integer.parseInt(getProperty("minUnconnectedRegionSize", "3"));
        mergeRegionSize = Integer.parseInt(getProperty("mergeRegionSize", "10"));
        maxEdgeLength = Float.parseFloat(getProperty("maxEdgeLength", "0"));
        edgeMaxDeviation = Float.parseFloat(getProperty("edgeMaxDeviation", "2.4"));
        maxVertsPerPoly = Integer.parseInt(getProperty("maxVertsPerPoly", "6"));
        contourSampleDistance = Float.parseFloat(getProperty("contourSampleDistance", "25"));
        contourMaxDeviation = Float.parseFloat(getProperty("contourMaxDeviation", "25"));
        return this;
    }
    
    public void toFile(String file){
        
    }

    public float getCellSize() {
        return cellSize;
    }

    public float getCellHeight() {
        return cellHeight;
    }

    public float getMinTraversableHeight() {
        return minTraversableHeight;
    }

    public float getMaxTraversableStep() {
        return maxTraversableStep;
    }

    public float getMaxTraversableSlope() {
        return maxTraversableSlope;
    }

    public boolean isClipLedges() {
        return clipLedges;
    }

    public float getTraversableAreaBorderSize() {
        return traversableAreaBorderSize;
    }

    public int getSmoothingThreshold() {
        return smoothingThreshold;
    }

    public boolean isUseConservativeExpansion() {
        return useConservativeExpansion;
    }

    public int getMinUnconnectedRegionSize() {
        return minUnconnectedRegionSize;
    }

    public int getMergeRegionSize() {
        return mergeRegionSize;
    }

    public float getMaxEdgeLength() {
        return maxEdgeLength;
    }

    public float getEdgeMaxDeviation() {
        return edgeMaxDeviation;
    }

    public int getMaxVertsPerPoly() {
        return maxVertsPerPoly;
    }

    public float getContourSampleDistance() {
        return contourSampleDistance;
    }

    public float getContourMaxDeviation() {
        return contourMaxDeviation;
    }

    public void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    public void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setMinTraversableHeight(float minTraversableHeight) {
        this.minTraversableHeight = minTraversableHeight;
    }

    public void setMaxTraversableStep(float maxTraversableStep) {
        this.maxTraversableStep = maxTraversableStep;
    }

    public void setMaxTraversableSlope(float maxTraversableSlope) {
        this.maxTraversableSlope = maxTraversableSlope;
    }

    public void setClipLedges(boolean clipLedges) {
        this.clipLedges = clipLedges;
    }

    public void setTraversableAreaBorderSize(float traversableAreaBorderSize) {
        this.traversableAreaBorderSize = traversableAreaBorderSize;
    }

    public void setSmoothingThreshold(int smoothingThreshold) {
        this.smoothingThreshold = smoothingThreshold;
    }

    public void setUseConservativeExpansion(boolean useConservativeExpansion) {
        this.useConservativeExpansion = useConservativeExpansion;
    }

    public void setMinUnconnectedRegionSize(int minUnconnectedRegionSize) {
        this.minUnconnectedRegionSize = minUnconnectedRegionSize;
    }

    public void setMergeRegionSize(int mergeRegionSize) {
        this.mergeRegionSize = mergeRegionSize;
    }

    public void setMaxEdgeLength(float maxEdgeLength) {
        this.maxEdgeLength = maxEdgeLength;
    }

    public void setEdgeMaxDeviation(float edgeMaxDeviation) {
        this.edgeMaxDeviation = edgeMaxDeviation;
    }

    public void setMaxVertsPerPoly(int maxVertsPerPoly) {
        this.maxVertsPerPoly = maxVertsPerPoly;
    }

    public void setContourSampleDistance(float contourSampleDistance) {
        this.contourSampleDistance = contourSampleDistance;
    }

    public void setContourMaxDeviation(float contourMaxDeviation) {
        this.contourMaxDeviation = contourMaxDeviation;
    }
    
    
    
}

package com.logdata.backend.model;

public class MemoryVO {
    private long totalMemory;
    private long availMemory;
    private float memoryPercentage;
    private long threshold;
    private boolean lowMemory;
    private int dalvikPss;
    private int nativePss;
    private int otherPss;
    private int totalPss;

    public MemoryVO() {

    }

    public MemoryVO(long totalMemory, long availMemory, float memoryPercentage, long threshold, boolean lowMemory, int dalvikPss, int nativePss, int otherPss, int totalPss) {
        this.totalMemory = totalMemory;
        this.availMemory = availMemory;
        this.memoryPercentage = memoryPercentage;
        this.threshold = threshold;
        this.lowMemory = lowMemory;
        this.dalvikPss = dalvikPss;
        this.nativePss = nativePss;
        this.otherPss = otherPss;
        this.totalPss = totalPss;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getAvailMemory() {
        return availMemory;
    }

    public void setAvailMemory(long availMemory) {
        this.availMemory = availMemory;
    }

    public float getMemoryPercentage() {
        return memoryPercentage;
    }

    public void setMemoryPercentage(float memoryPercentage) {
        this.memoryPercentage = memoryPercentage;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public boolean isLowMemory() {
        return lowMemory;
    }

    public void setLowMemory(boolean lowMemory) {
        this.lowMemory = lowMemory;
    }

    public int getDalvikPss() {
        return dalvikPss;
    }

    public void setDalvikPss(int dalvikPss) {
        this.dalvikPss = dalvikPss;
    }

    public int getNativePss() {
        return nativePss;
    }

    public void setNativePss(int nativePss) {
        this.nativePss = nativePss;
    }

    public int getOtherPss() {
        return otherPss;
    }

    public void setOtherPss(int otherPss) {
        this.otherPss = otherPss;
    }

    public int getTotalPss() {
        return totalPss;
    }

    public void setTotalPss(int totalPss) {
        this.totalPss = totalPss;
    }

//    @Override
//    public String toString() {
//        return "Total Memory : " + getTotalMemory() + "\n" +
//                "Avail Memory : " + getAvailMemory() + "\n" +
//                "Memory Percentage : " + getMemoryPercentage() + "\n" +
//                "Threshold : " + getThreshold() + "\n" +
//                "Low Memory : " + isLowMemory() + "\n" +
//                "Dalvik Pss : " + getDalvikPss() + "\n" +
//                "Native Pss : " + getNativePss() + "\n" +
//                "Other Pss : " + getOtherPss() + "\n" +
//                "Total Pss : " + getTotalPss() + "\n";
//    }
}


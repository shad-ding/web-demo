package com.example.webdemo.constants;

public class DemoConstants {
    private DemoConstants() throws Exception { throw new Exception("禁止创建实例对象"); }

    public static final int BYTE = 8;
    public static final int SCALE = 1024;
    public static final int KB = BYTE * SCALE;
    public static final int MB = BYTE * SCALE * SCALE;
    public static final int GB = BYTE * SCALE * SCALE * SCALE;

}

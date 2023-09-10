package com.example.webdemo.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class ApplicationProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

    /** 项目的resources绝对地址 */
    private static String absolutePath;

    /** resources文件夹下所有文件的名称 */
    private static List<String> filenames;

    /** 存储所有来自.sd3文件的键值对 */
    private static Map<String, String> props = new HashMap<>();

    static {
        Properties properties = System.getProperties();
        String bootDir = (String) properties.get("user.dir");
        absolutePath = bootDir + "\\src\\main\\resources";
        File folder = new File(absolutePath);
        String[] list = folder.list((dir, name) -> !new File(dir, name).isDirectory() && name.toLowerCase().endsWith(".sd3"));

        if (list != null)
            filenames = Arrays.asList(list);

        try {
            ApplicationProperties.loadFiles();
        } catch (Exception e) {
            LOGGER.error("出现异常：" + e);
        }
    }

    private ApplicationProperties() throws Exception {
        throw new Exception();
    }

    private static void loadFiles() throws Exception {
        for (String filename : filenames) {
            ApplicationProperties.loadSingleFile(filename);
        }
    }

    public static void getProps() {
        Map<String, String> props = ApplicationProperties.props;
        if (props.isEmpty())
            System.out.println("No key-value stored in Props");

        Set<String> keys = props.keySet();
        for (String key : keys)
            System.out.println(key + " => " + props.get(key));
    }

    private static void loadSingleFile(String filename) throws Exception {
        if (filename == null || !filenames.contains(filename))
            return;

        BufferedReader in = new BufferedReader(new FileReader(absolutePath + "/" + filename));
        String line;
        while ((line = in.readLine()) != null) {
            if(line.contains("=")) {
                String[] key_value = line.split("=");
                String key = key_value[0].trim();
                String value = key_value[1].trim();
                ApplicationProperties.props.put(key, value);
            } else {
                throw new Exception();
            }
        }
    }

    public static String get(String key) {
        return props.getOrDefault(key, null);
    }
}

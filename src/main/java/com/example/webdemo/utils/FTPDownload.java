package com.example.webdemo.utils;

import com.example.webdemo.constants.DemoConstants;
import com.example.webdemo.properties.ApplicationProperties;
import com.example.webdemo.service.impl.BasicServiceImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPDownload {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final int BUFFER_SIZE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);

    public static boolean downloadFiles(String url) {
        downloadFiles(url, ApplicationProperties.get("download-path"));
    }

    public static boolean downloadFiles(String url, String downloadPath) {
        if (!isUrl(url))
            return false;
        String filename = url.substring(url.lastIndexOf("/") + 1);
        Request req = createRequest(url);
        Response resp = call(req);

        ResponseBody body = resp.body();
        if (body != null) {
            try (BufferedInputStream in = new BufferedInputStream(body.byteStream());
                 FileOutputStream out = new FileOutputStream(downloadPath + "/" + filename);) {
                byte[] buffer = new byte[BUFFER_SIZE * DemoConstants.MB];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                LOGGER.info("文件下载完成");
                return true;
            } catch (FileNotFoundException fe) {
                LOGGER.error("没有找到文件：" + fe);
                return false;
            } catch (IOException ie) {
                LOGGER.error("读取或写入文件错误：" + ie);
                return false;
            }
        } else {
            LOGGER.error("返回体为空 或 返回体不包含文件");
            return false;
        }
    }

    private static Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    private static Response call(Request req) {
        Response resp = null;
        try {
            resp = HTTP_CLIENT.newCall(req).execute();
            if (!resp.isSuccessful())
                throw new IOException("下载失败：" + resp);
            return resp;
        } catch (IOException e) {
            LOGGER.error("下载过程出错", e);
        }
        return null;
    }

    private static boolean isUrl(String url) {
        return url.startsWith("http");
    }
}

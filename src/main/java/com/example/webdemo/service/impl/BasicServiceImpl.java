package com.example.webdemo.service.impl;

import com.example.webdemo.constants.DemoConstants;
import com.example.webdemo.service.IBasicService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BasicServiceImpl implements IBasicService {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final int BUFFER_SIZE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);

    public static boolean downloadFiles(String url, String downloadPath) {
        Request req = createRequest(url);
        Response resp = call(req);

        ResponseBody body = resp.body();
        if (body != null) {
            BufferedInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new BufferedInputStream(body.byteStream());
                out = new FileOutputStream(downloadPath);
                byte[] buffer = new byte[BUFFER_SIZE * DemoConstants.MB];
                for (int i = 0; ; i++) {
                    int read = in.read(buffer);
                    out.write(buffer, i * BUFFER_SIZE * DemoConstants.MB, buffer.length);
                    if (read == -1)
                        break;
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
}

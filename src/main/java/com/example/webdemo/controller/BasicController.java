/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.webdemo.controller;

import com.example.webdemo.dao.domain.User;
import com.example.webdemo.dao.service.IUserService;
import com.example.webdemo.service.IBasicService;
import com.example.webdemo.service.impl.BasicServiceImpl;
import com.example.webdemo.utils.FTPDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
public class BasicController {
    @Autowired
    private IUserService userService;

    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return "Hello " + name;
    }

    // http://127.0.0.1:8080/user/userId=1
    @RequestMapping("/user")
    @ResponseBody
    public User user(@RequestParam(name = "userId") Integer userId) {
        return this.userService.findByUserId(userId);
    }

//    http://localhost:8080/download
    @PostMapping("/download")
    @ResponseBody
    public String download(@RequestBody Map<String, Object> map) {
        Object obj = map.get("url");
        Object obj2 = map.get("download-path");
        if (!(obj instanceof String) || (obj2 != null && !(obj2 instanceof String)))
            return "传递参数类型错误";
        String url = (String) obj;
        String downloadPath = (String) obj2;
        boolean success = FTPDownload.downloadFiles(url, downloadPath);
        if (success)
            return "下载成功";
        else
            return "下载失败";
    }
}

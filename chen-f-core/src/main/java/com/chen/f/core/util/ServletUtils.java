package com.chen.f.core.util;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author chen
 * @since 2017/10/30 13:17.
 */
public class ServletUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 获取当前request ip
     *
     * @param request request
     * @return ip
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        return request.getRemoteAddr();

    }

    /**
     * 是否是Ajax请求
     *
     * @param request request
     * @return 是|否
     */
    public static boolean isAjax(HttpServletRequest request) {
        if (request == null) {
            throw new NullPointerException("request == null");
        }
        return Objects.equals(request.getHeader("x-requested-with"), "XMLHttpRequest");
    }

    /**
     * 是否不是Ajax请求
     *
     * @param request request
     * @return 是|否
     */
    public static boolean isNotAjax(HttpServletRequest request) {
        return !isAjax(request);
    }

    /**
     * 读取请求体中的字符串
     *
     * @param request request
     * @return 请求体中的字符串
     */
    public static String readRequestToString(HttpServletRequest request) throws IOException {
        if (request == null) {
            throw new NullPointerException("request == null");
        }

        try (
                InputStream inputStream = request.getInputStream();
        ) {
            return IOUtils.toString(inputStream, DEFAULT_CHARSET);
        }
    }

    /**
     * 读取请求中的文件
     *
     * @param request   request
     * @param fieldName 字段名
     * @return 请求中的文件
     */
    public static MultipartFile readRequestToMultipartFile(HttpServletRequest request, final String fieldName) {
        if (request == null) {
            throw new NullPointerException("request == null");
        }
        if (StringUtils.isBlank(fieldName)) {
            throw new NullPointerException("fieldName == null");
        }
        Map<String, MultipartFile> multipartFileMap = readRequestToFieldNameMultipartFileMap(request);
        if (MapUtils.isNotEmpty(multipartFileMap)) {
            return multipartFileMap.get(fieldName);
        }
        return null;
    }

    public static Map<String, List<FileItem>> readRequestToFieldNameFileItemListMap(HttpServletRequest request) {
        if (request == null) {
            throw new NullPointerException("request == null");
        }
        ServletFileUpload servletFileUpload = new ServletFileUpload();
        try {
            return servletFileUpload.parseParameterMap(request);
        } catch (FileUploadException e) {
            return null;
        }
    }

    /**
     * 读取请求中的所有文件
     *
     * @param request request
     * @return 请求中的所有文件
     */
    public static Map<String, MultipartFile> readRequestToFieldNameMultipartFileMap(HttpServletRequest request) {
        if (request == null) {
            throw new NullPointerException("request == null");
        }
        MultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);
        return mRequest.getFileMap();
    }

    /**
     * 响应json
     *
     * @param response response
     * @param json     json字符串
     */
    public static void responseJson(HttpServletResponse response, String json) throws IOException {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        if (json == null) {
            throw new NullPointerException("json == null");
        }
        response.setContentType("application/json;charset=UTF-8");
        //response.setLocale(Locale.CHINA);
        response.setCharacterEncoding(DEFAULT_CHARSET.name());
        response.setContentLength(json.getBytes(DEFAULT_CHARSET).length);
        try (
                PrintWriter writer = response.getWriter();
        ) {
            writer.write(json);
            writer.flush();
        }

    }

    /**
     * 响应json
     *
     * @param response response
     * @param o        对象(会转成json字符串
     */
    public static void responseJson(HttpServletResponse response, Object o) throws IOException {
        responseJson(response, JacksonUtils.toJsonString(o));
    }

    /**
     * 响应图片
     *
     * @param response  response
     * @param imageFile 图片
     */
    public static void responseImage(HttpServletResponse response, File imageFile) throws IOException {
        if (imageFile == null) {
            throw new NullPointerException("imageFile == null");
        }

        responseImage(response, ImageIO.read(imageFile));
    }

    /**
     * 响应图片
     *
     * @param response      response
     * @param bufferedImage 图片
     */
    public static void responseImage(HttpServletResponse response, BufferedImage bufferedImage) throws IOException {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        if (bufferedImage == null) {
            throw new NullPointerException("bufferedImage == null");
        }
        response.setContentType("image/png");
        //将图片转成png格式
        BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferedImage1.getGraphics().drawImage(bufferedImage, 0, 0, null);
        try (
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            final boolean isWrite = ImageIO.write(bufferedImage, "png", outputStream);
            if (!isWrite) {
                throw new RuntimeException("写入图片失败");
            }
            outputStream.flush();
        }
    }

    /**
     * 响应下载文件
     *
     * @param response response
     * @param fileName 文件名
     * @param file     文件
     */
    public static void responseDownload(HttpServletResponse response, String fileName, File file) throws IOException {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        if (fileName == null) {
            throw new NullPointerException("fileName == null");
        }
        if (file == null || !file.exists()) {
            throw new NullPointerException("file == null || !file.exists()");
        }
        //response.setContentType("application/force-download");
        //response.setContentType("application/download");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLengthLong(FileUtils.sizeOf(file));
        try (
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            FileUtils.copyFile(file, outputStream);
            outputStream.flush();
        }
    }

    /**
     * 响应下载文件
     *
     * @param response      response
     * @param fileName      文件名
     * @param fileByteArray 文件
     */
    public static void responseDownload(HttpServletResponse response, String fileName, byte[] fileByteArray) throws IOException {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        if (fileName == null) {
            throw new NullPointerException("fileName == null");
        }
        if (fileByteArray == null) {
            throw new NullPointerException("fileByteArray == null");
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLengthLong(fileByteArray.length);
        try (
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            IOUtils.write(fileByteArray, outputStream);
            outputStream.flush();
        }
    }

    /**
     * 响应下载文件
     *
     * @param response    response
     * @param fileName    文件名
     * @param inputStream 文件流
     */
    public static void responseDownload(HttpServletResponse response, String fileName, InputStream inputStream) throws IOException {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        if (fileName == null) {
            throw new NullPointerException("fileName == null");
        }
        if (inputStream == null) {
            throw new NullPointerException("inputStream == null");
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        //response.setContentLengthLong(IOUtils.toByteArray(inputStream).length);
        try (
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            long length = IOUtils.copyLarge(inputStream, outputStream);
            //设置响应长度
            response.setContentLengthLong(length);
            outputStream.flush();
        }
    }

}

package com.chen.f.core.util;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * SFTP工具类
 * <p>
 * 基于jsch 0.1.55
 *
 * @author chen
 * @since 2019/9/9 14:05.
 */
public class SftpUtils {

    /**
     * 创建SFTP通道
     * <p>
     * 使用完后必须调用{@link #destroyChannelSftp(ChannelSftp)}方法销毁
     *
     * @param host     ip
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return SFTP通道
     */
    public static ChannelSftp createChannelSftp(String host, int port, String username, String password) throws IOException {
        Channel channel = null;
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            throw new IOException(e);
        }
        return ((ChannelSftp) channel);
    }

    /**
     * 销毁SFTP通道
     *
     * @param channelSftp SFTP通道
     */
    public static void destroyChannelSftp(ChannelSftp channelSftp) throws IOException {
        if (channelSftp != null) {
            channelSftp.disconnect();
            try {
                channelSftp.getSession().disconnect();
            } catch (JSchException e) {
                throw new IOException(e);
            }
        }
    }

    /**
     * 上传文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     * @param inputStream 文件流
     */
    public static void uploadFile(ChannelSftp channelSftp, String path, String fileName, InputStream inputStream) throws IOException {
        try {
            channelSftp.mkdir(path);
        } catch (SftpException e) {
            //忽略该异常(大多数时候是因为文件夹已存在,而判断文件夹是否存在逻辑比较复杂,这样比较省事)
        }

        try {
            channelSftp.put(inputStream, fileName);
        } catch (SftpException e) {
            throw new IOException(e);
        }

    }


    /**
     * 删除文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     */
    public static void deleteFile(ChannelSftp channelSftp, String path, String fileName) throws IOException {
        try {
            channelSftp.rm(path + (path.endsWith("/") ? "" : "/") + fileName);
        } catch (SftpException e) {
            throw new IOException(e);
        }
    }

    /**
     * 获取文件
     *
     * @param channelSftp  SFTP通道
     * @param path         路径
     * @param fileName     文件名称
     * @param outputStream 文件流
     */
    public static void getFile(ChannelSftp channelSftp, String path, String fileName, OutputStream outputStream) throws IOException {
        try {
            channelSftp.get(path + (path.endsWith("/") ? "" : "/") + fileName, outputStream);
        } catch (SftpException e) {
            throw new IOException(e);
        }
    }

    /**
     * 获取文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     * @return 文件流
     */
    public static InputStream getFile(ChannelSftp channelSftp, String path, String fileName) throws IOException {
        try {
            return channelSftp.get(path + (path.endsWith("/") ? "" : "/") + fileName);
        } catch (SftpException e) {
            throw new IOException(e);
        }
    }

}

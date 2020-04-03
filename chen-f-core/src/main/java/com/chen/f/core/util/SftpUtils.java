package com.chen.f.core.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

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
     * 使用完后必须调用{@link #destroyChannelSftp(com.jcraft.jsch.ChannelSftp)}方法销毁
     *
     * @param host     ip
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return SFTP通道
     */
    public static ChannelSftp createChannelSftpByPassword(String host, int port, String username, String password) throws JSchException {
        Channel channel = null;

        JSch jSch = new JSch();
        Session session = jSch.getSession(username, host, port);
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();

        return ((ChannelSftp) channel);
    }

    /**
     * 创建SFTP通道
     * <p>
     * 使用完后必须调用{@link #destroyChannelSftp(com.jcraft.jsch.ChannelSftp)}方法销毁
     *
     * @param host       ip
     * @param port       端口
     * @param username   用户名
     * @param name       密钥名称
     * @param prvkey     私钥
     * @param pubkey     公钥
     * @param passphrase 私钥密码
     * @return SFTP通道
     */
    public static ChannelSftp createChannelSftpByKey(String host, int port, String username, String name, byte[] prvkey, byte[] pubkey, byte[] passphrase) throws JSchException {
        Channel channel = null;

        JSch jSch = new JSch();
        jSch.addIdentity(name, prvkey, pubkey, passphrase);
        Session session = jSch.getSession(username, host, port);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();

        return ((ChannelSftp) channel);
    }

    /**
     * 创建SFTP通道
     * <p>
     * 使用完后必须调用{@link #destroyChannelSftp(com.jcraft.jsch.ChannelSftp)}方法销毁
     *
     * @param host       ip
     * @param port       端口
     * @param username   用户名
     * @param prvkey     私钥
     * @param passphrase 私钥密码
     * @return SFTP通道
     */
    public static ChannelSftp createChannelSftpByKey(String host, int port, String username, byte[] prvkey, byte[] passphrase) throws JSchException {
        return createChannelSftpByKey(host, port, username, username, prvkey, null, passphrase);
    }

    /**
     * 创建SFTP通道
     * <p>
     * 使用完后必须调用{@link #destroyChannelSftp(com.jcraft.jsch.ChannelSftp)}方法销毁
     *
     * @param host     ip
     * @param port     端口
     * @param username 用户名
     * @param prvkey   私钥
     * @return SFTP通道
     */
    public static ChannelSftp createChannelSftpByKey(String host, int port, String username, byte[] prvkey) throws JSchException {
        return createChannelSftpByKey(host, port, username, username, prvkey, null, null);
    }


    /**
     * 销毁SFTP通道
     *
     * @param channelSftp SFTP通道
     */
    public static void destroyChannelSftp(ChannelSftp channelSftp) throws JSchException {
        if (channelSftp != null) {
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
        }
    }

    /**
     * 上传文件
     *
     * @param channelSftp SFTP通道
     * @param src         资源地址
     * @param inputStream 文件流
     */
    public static void uploadFile(ChannelSftp channelSftp, String src, InputStream inputStream) throws JSchException, SftpException {
        final String path = FilenameUtils.getFullPath(src);
        if (!isExistFile(channelSftp, path)) {
            channelSftp.mkdir(path);
        }
        channelSftp.put(inputStream, src);
    }

    /**
     * 上传文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     * @param inputStream 文件流
     */
    public static void uploadFile(ChannelSftp channelSftp, String path, String fileName, InputStream inputStream) throws JSchException, SftpException {
        if (!isExistFile(channelSftp, path)) {
            channelSftp.mkdir(path);
        }
        channelSftp.put(inputStream, path + (path.endsWith("/") ? "" : "/") + fileName);
    }


    /**
     * 删除文件
     *
     * @param channelSftp SFTP通道
     * @param src         资源地址
     */
    public static void deleteFile(ChannelSftp channelSftp, String src) throws JSchException, SftpException {
        channelSftp.rm(src);
    }

    /**
     * 删除文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     */
    public static void deleteFile(ChannelSftp channelSftp, String path, String fileName) throws JSchException, SftpException {
        channelSftp.rm(path + (path.endsWith("/") ? "" : "/") + fileName);
    }

    /**
     * 获取文件
     *
     * @param channelSftp  SFTP通道
     * @param src          资源地址
     * @param outputStream 文件流
     */
    public static void getFile(ChannelSftp channelSftp, String src, OutputStream outputStream) throws JSchException, SftpException {
        channelSftp.get(src, outputStream);
    }

    /**
     * 获取文件
     *
     * @param channelSftp SFTP通道
     * @param src         资源地址
     * @return 文件流
     */
    public static InputStream getFile(ChannelSftp channelSftp, String src) throws JSchException, SftpException {
        return channelSftp.get(src);
    }

    /**
     * 获取文件
     *
     * @param channelSftp  SFTP通道
     * @param path         路径
     * @param fileName     文件名称
     * @param outputStream 文件流
     */
    public static void getFile(ChannelSftp channelSftp, String path, String fileName, OutputStream outputStream) throws JSchException, SftpException {
        channelSftp.get(path + (path.endsWith("/") ? "" : "/") + fileName, outputStream);
    }

    /**
     * 获取文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     * @return 文件流
     */
    public static InputStream getFile(ChannelSftp channelSftp, String path, String fileName) throws JSchException, SftpException {
        return channelSftp.get(path + (path.endsWith("/") ? "" : "/") + fileName);
    }


    /**
     * 是否存在文件
     *
     * @param channelSftp SFTP通道
     * @param path        路径
     * @param fileName    文件名称
     * @return 是/否
     */
    public static boolean isExistFile(ChannelSftp channelSftp, String path, String fileName) throws SftpException {
        return isExistFile(channelSftp, path + (path.endsWith("/") ? "" : "/") + fileName);
    }

    /**
     * 是否存在文件
     *
     * @param channelSftp SFTP通道
     * @param src         资源地址
     * @return 是/否
     */
    public static boolean isExistFile(ChannelSftp channelSftp, String src) throws SftpException {

        String[] splitPathArray = StringUtils.split(src, "/");

        if (ArrayUtils.isEmpty(splitPathArray)) {
            return true;
        }

        if (StringUtils.startsWith(src, "/")) {
            splitPathArray = ArrayUtils.insert(0, splitPathArray, "/");
        } else {
            if (!StringUtils.startsWith(src, "./")) {
                splitPathArray = ArrayUtils.insert(0, splitPathArray, ".");
            }
        }

        String currentPath = "";
        String currentFilename = null;
        int currentIndex = 0;
        final int length = splitPathArray.length;

        for (String splitPath : splitPathArray) {
            if (currentIndex == length - 1) {
                break;
            }

            currentPath = currentPath + (currentPath.endsWith("/") || splitPath.endsWith("/") ? "" : "/") + splitPath;
            currentFilename = splitPathArray[currentIndex + 1];

            if (!isExistFile0(channelSftp, currentPath, currentFilename)) {
                return false;
            }

            currentIndex++;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static boolean isExistFile0(ChannelSftp channelSftp, String path, String fileName) throws SftpException {
        final Vector<Object> vector = channelSftp.ls(path);
        if (CollectionUtils.isEmpty(vector)) {
            return true;
        }

        return vector.stream()
                .filter(ChannelSftp.LsEntry.class::isInstance)
                .map(ChannelSftp.LsEntry.class::cast)
                .anyMatch((lsEntry) -> StringUtils.equals(lsEntry.getFilename(), fileName));
    }
}


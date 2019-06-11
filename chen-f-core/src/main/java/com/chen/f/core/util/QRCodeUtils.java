package com.chen.f.core.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * QRCodeUtils.java<br>
 * <b>功能：</b><br>
 *
 * @author chen  Email:1@1.com
 * Time:2018/4/24 15:30
 */
public class QRCodeUtils {

    public static final BarcodeFormat DEFAULT_BARCODE_FORMAT = BarcodeFormat.QR_CODE;
    public static final ErrorCorrectionLevel DEFAULT_ERROR_CORRECTION_LEVEL = ErrorCorrectionLevel.L;
    public static final String DEFAULT_CHARACTER_SET = "UTF-8";
    public static final Integer DEFAULT_IMAGE_SIZE = 300;
    public static final Integer DEFAULT_IMAGE_MARGIN = 2;

    public static final String IMAGE_FORMAT = "png";


    /**
     * 创建二维码到BitMatrix
     *
     * @param contents             二维码内容
     * @param barcodeFormat        二维码类型
     * @param imageSize            二维码大小
     * @param errorCorrectionLevel 二维码错误级别
     * @param characterSet         编码
     * @return BitMatrix
     */
    public static BitMatrix createQRCodeToBitMatrix(final String contents, final BarcodeFormat barcodeFormat, final Integer imageSize, final ErrorCorrectionLevel errorCorrectionLevel, final String characterSet, final Integer imageMargin) {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel != null ? errorCorrectionLevel : DEFAULT_ERROR_CORRECTION_LEVEL);
        hints.put(EncodeHintType.CHARACTER_SET, characterSet != null ? characterSet : DEFAULT_CHARACTER_SET);
        hints.put(EncodeHintType.MARGIN, imageMargin != null ? imageMargin : DEFAULT_IMAGE_MARGIN);

        try {
            return new MultiFormatWriter()
                    .encode(contents, barcodeFormat != null ? barcodeFormat : DEFAULT_BARCODE_FORMAT, imageSize != null ? imageSize : DEFAULT_IMAGE_SIZE, imageSize != null ? imageSize : DEFAULT_IMAGE_SIZE, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static BitMatrix createQRCodeToBitMatrix(final String contents, final Integer imageSize) {
        return createQRCodeToBitMatrix(contents, null, imageSize, null, null, null);
    }

    public static BitMatrix createQRCodeToBitMatrix(final String contents) {
        return createQRCodeToBitMatrix(contents, null, null, null, null, null);
    }


    /**
     * 创建二维码到BufferedImage
     *
     * @param contents             二维码内容
     * @param barcodeFormat        二维码类型
     * @param imageSize            二维码大小
     * @param errorCorrectionLevel 二维码错误级别
     * @param characterSet         编码
     * @return BufferedImage
     */
    public static BufferedImage createQRCodeToBufferedImage(final String contents, final BarcodeFormat barcodeFormat, final Integer imageSize, final ErrorCorrectionLevel errorCorrectionLevel, final String characterSet, final Integer imageMargin) {
        BitMatrix qrCodeToBitMatrix = createQRCodeToBitMatrix(contents, barcodeFormat, imageSize, errorCorrectionLevel, characterSet, imageMargin);
        return MatrixToImageWriter.toBufferedImage(qrCodeToBitMatrix);
    }

    public static BufferedImage createQRCodeToBufferedImage(final String contents, final Integer imageSize) {
        BitMatrix qrCodeToBitMatrix = createQRCodeToBitMatrix(contents, null, imageSize, null, null, null);
        return MatrixToImageWriter.toBufferedImage(qrCodeToBitMatrix);
    }

    public static BufferedImage createQRCodeToBufferedImage(final String contents) {
        BitMatrix qrCodeToBitMatrix = createQRCodeToBitMatrix(contents, null, null, null, null, null);
        return MatrixToImageWriter.toBufferedImage(qrCodeToBitMatrix);
    }

    /**
     * 创建二维码并把二维码保存到输出流中
     *
     * @param contents             二维码内容
     * @param barcodeFormat        二维码类型
     * @param imageSize            二维码大小
     * @param errorCorrectionLevel 二维码错误级别
     * @param characterSet         编码
     * @param outputStream         保存二维码图片的流
     * @return 成功、失败
     */
    public static boolean createQRCodeToStream(final String contents, final BarcodeFormat barcodeFormat, final int imageSize, final ErrorCorrectionLevel errorCorrectionLevel, final String characterSet, final Integer imageMargin, final OutputStream outputStream) {
        BufferedImage qrCodeToBufferedImage = createQRCodeToBufferedImage(contents, barcodeFormat, imageSize, errorCorrectionLevel, characterSet, imageMargin);
        try {
            return ImageIO.write(qrCodeToBufferedImage, IMAGE_FORMAT, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean createQRCodeToStream(final String contents, final Integer imageSize, final OutputStream outputStream) {
        BufferedImage qrCodeToBufferedImage = createQRCodeToBufferedImage(contents, null, imageSize, null, null, null);
        try {
            return ImageIO.write(qrCodeToBufferedImage, IMAGE_FORMAT, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean createQRCodeToStream(final String contents, final OutputStream outputStream) {
        BufferedImage qrCodeToBufferedImage = createQRCodeToBufferedImage(contents, null, null, null, null, null);
        try {
            return ImageIO.write(qrCodeToBufferedImage, IMAGE_FORMAT, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取二维码图片内容
     *
     * @param bufferedImage 二维码图片
     * @param characterSet  编码
     * @return 解码结果
     */
    public static Result readQRCodeToResult(final BufferedImage bufferedImage, final String characterSet) {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, characterSet != null ? characterSet : DEFAULT_CHARACTER_SET);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        try {
            return new MultiFormatReader().decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取二维码图片内容
     *
     * @param inputStream  二维码图片
     * @param characterSet 编码
     * @return 解码结果
     */
    public static Result readQRCodeToResult(final InputStream inputStream, final String characterSet) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return readQRCodeToResult(bufferedImage, characterSet);
    }

    /**
     * 读取二维码图片内容
     *
     * @param imageInputStream 二维码图片
     * @param characterSet     编码
     * @return 解码结果
     */
    public static Result readQRCodeToResult(final ImageInputStream imageInputStream, final String characterSet) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return readQRCodeToResult(bufferedImage, characterSet);
    }

    /**
     * 读取二维码图片内容
     *
     * @param file         二维码图片
     * @param characterSet 编码
     * @return 解码结果
     */
    public static Result readQRCodeToResult(final File file, final String characterSet) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return readQRCodeToResult(bufferedImage, characterSet);
    }
}

package com.chen.f.core.util;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.ShadowGimpy;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author chen
 * @since 2018/6/30 13:42.
 */
public class KaptchaUtils {

    private static final Producer producer;

    static {
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "150");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "50");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "5");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"BLACK" );
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, DefaultNoise.class.getName());
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, ShadowGimpy.class.getName());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789abcdefghijklmnopqrstuvwxyz");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "1");

        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        producer = kaptcha;
    }

    public static String generateCaptchaText() {
        return producer.createText();
    }

    public static BufferedImage generateCaptchaImage(final String captchaText) {
        return producer.createImage(captchaText);
    }

    public static boolean generateCaptchaImageAndWriteOutputStream(final String captchaText, OutputStream outputStream) {
        BufferedImage image = producer.createImage(captchaText);
        try (OutputStream outputStream1 = outputStream){
            return ImageIO.write(image, "PNG", outputStream1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

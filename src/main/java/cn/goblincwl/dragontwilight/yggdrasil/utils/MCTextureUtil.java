package cn.goblincwl.dragontwilight.yggdrasil.utils;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 首先创建一个长度为 (width * height * 4 + 8) 字节的缓冲区，其中 width 和 height 为图像的长和宽
 * 填充该缓冲区
 * 0~3 字节为 width，以大端序存储
 * 4~7 字节为 height，以大端序存储
 * 对于每一个像素，设其坐标为 (x, y)，其首地址 offset 为 ((y + x * height) * 4 + 8)
 * 第 (offset + 0)、(offset + 1)、(offset + 2)、(offset + 3) 个字节分别为该像素的 Alpha、Red、Green、Blue 分量
 * 若 Alpha 分量为 0x00（透明），则 RGB 分量皆作为 0x00 处理
 * 计算以上缓冲区内数据的 SHA-256，作为材质的 hash
 */
public class MCTextureUtil {
    /**
     * 通过传入图像来计算图像的哈希
     * @param img 传入图像
     * @return 返回这个图像的哈希值
     * @throws NoSuchAlgorithmException 无法找到 SHA-256 算法
     */
    public static String textureHash(BufferedImage img) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int width = img.getWidth();
        int height = img.getHeight();
        byte[] buf = new byte[4096];

        putInt(buf, 0, width); // 0~3: width(big-endian)
        putInt(buf, 4, height); // 4~7: height(big-endian)
        int pos = 8;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // pos+0: alpha
                // pos+1: red
                // pos+2: green
                // pos+3: blue
                putInt(buf, pos, img.getRGB(x, y));
                if (buf[pos + 0] == 0) {
                    // the pixel is transparent
                    buf[pos + 1] = buf[pos + 2] = buf[pos + 3] = 0;
                }
                pos += 4;
                if (pos == buf.length) {
                    // buffer is full
                    pos = 0;
                    digest.update(buf, 0, buf.length);
                }
            }
        }
        if (pos > 0) {
            // flush
            digest.update(buf, 0, pos);
        }

        byte[] sha256 = digest.digest();
        return String.format("%0" + (sha256.length << 1) + "x", new BigInteger(1, sha256)); // to hex
    }

    // put an int into the array in big-endian
    private static void putInt(byte[] array, int offset, int x) {
        array[offset + 0] = (byte) (x >> 24 & 0xff);
        array[offset + 1] = (byte) (x >> 16 & 0xff);
        array[offset + 2] = (byte) (x >> 8 & 0xff);
        array[offset + 3] = (byte) (x >> 0 & 0xff);
    }
}

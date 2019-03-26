package encoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Encoder {
    private static final float QUALITY = (float) 0.3;
    private static final String FORMAT = "jpg";

    public static byte[] encode(BufferedImage image){
        if(image == null){
            return null;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = null;
        try{
            imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageWriter writer = ImageIO.getImageWritersByFormatName(FORMAT).next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(QUALITY);

            writer.setOutput(imageOutputStream);
            writer.write(null, new IIOImage(image, null , null), param);
            writer.dispose();
        }catch (IOException e){
            try {
                ImageIO.write(image, FORMAT, outputStream);
            }catch (IOException ex){
            }
            return outputStream.toByteArray();
        }finally {
            if(imageOutputStream != null){
                try {
                    imageOutputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return outputStream.toByteArray();
    }
}

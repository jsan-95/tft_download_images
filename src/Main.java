

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main{

    static final File dir = new File("/Volumes/Seagate Expansion Drive/TFT/Conjunto de datos/public_images");

    public static void main(String[] args) {



        System.out.println("Que comiencen los juegos del hambre"+ new Date());
        double width;
        double height;
        double percentage;
        double widthTarget;
        double heightTarget;
        int i = 0;
        if (dir.isDirectory()) {
            for (final File f : dir.listFiles()) {
                BufferedImage img = null;
                BufferedImage outputImage = null;
                try {
                    img = ImageIO.read(f);
                    outputImage = new BufferedImage(1000, 1000, img.getType());
                    Graphics2D g2d = outputImage.createGraphics();

                    width = img.getWidth();
                    height = img.getHeight();
                    widthTarget = 980;
                    heightTarget = 980;
                    if(width > height){
                        percentage = width/height;
                        heightTarget = 980 / percentage;
                        g2d.drawImage(img, 10, (int)(1000-heightTarget)/2, (int) widthTarget, (int) heightTarget, null);
                    }else{
                        percentage = height/width;
                        widthTarget = 980 / percentage;
                        g2d.drawImage(img, (int)(1000-widthTarget)/2, 10, (int) widthTarget, (int) heightTarget, null);
                    }

                    g2d.dispose();
                    ImageIO.write(outputImage, "jpg",
                            new File("/Volumes/Seagate Expansion Drive/TFT/Conjunto de datos/resized_images/"+f.getName()));

                    if(i%100 == 0){
                        System.out.println("Iteración "+i);
                    }
                    i++;

                } catch (final IOException e) { }
            }
        }

        System.out.println("Los jeugos han finalizado, Katniss es la ganadora: "+new Date());
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[1024];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }



}

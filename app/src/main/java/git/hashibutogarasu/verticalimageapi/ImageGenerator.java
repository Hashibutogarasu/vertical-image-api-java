package git.hashibutogarasu.verticalimageapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageGenerator extends ResponseData {
    ImageGenerator(String json, String width) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<FileInfo[]> type = new TypeReference<>() {};
        FileInfo[] list = mapper.readValue(json, type);

        this.width = Integer.parseInt(width);

        BufferedImage img = new BufferedImage(this.width, this.width*list.length, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        int num = 0;

        for (FileInfo fileInfo: list) {
            byte[] bytes = Base64.getDecoder().decode(fileInfo.fileData);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            g.drawImage(bufferedImage, 0, img.getWidth()*num, null);

            num++;
        }

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);

        this.width = img.getWidth();
        this.height = img.getHeight();
        this.data = Base64.getEncoder().encodeToString(os.toByteArray());;
        this.ext = "png";
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}

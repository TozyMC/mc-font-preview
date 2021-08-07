package xyz.tozymc.mcfontpreview.resourcepack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

public class ResourcePack {
  private final Map<String, File> fontImageFiles;

  public ResourcePack(Map<String, File> fontImageFiles) {this.fontImageFiles = fontImageFiles;}

  public BufferedImage getImage(String name) {
    if (fontImageFiles.containsKey(name)) {
      try {
        return ImageIO.read(fontImageFiles.get(name));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  public Map<String, File> getFontImageFiles() {
    return fontImageFiles;
  }
}

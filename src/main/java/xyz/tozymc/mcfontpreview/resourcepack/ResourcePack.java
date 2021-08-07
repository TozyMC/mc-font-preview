package xyz.tozymc.mcfontpreview.resourcepack;

import java.awt.image.BufferedImage;
import java.util.Map;

public class ResourcePack {
  private final Map<String, BufferedImage> fontImageFiles;

  public ResourcePack(Map<String, BufferedImage> fontImageFiles) {
    this.fontImageFiles = fontImageFiles;
  }

  public BufferedImage getImage(String name) {
    return fontImageFiles.getOrDefault(name, null);
  }

  public Map<String, BufferedImage> getFontImageFiles() {
    return fontImageFiles;
  }
}

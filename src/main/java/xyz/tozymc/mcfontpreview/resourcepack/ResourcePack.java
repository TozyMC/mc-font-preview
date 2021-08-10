package xyz.tozymc.mcfontpreview.resourcepack;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import xyz.tozymc.mcfontpreview.resourcepack.loader.ResourcePackFileLoader;
import xyz.tozymc.mcfontpreview.resourcepack.loader.ResourcePackLoader;

public class ResourcePack {
  private final Map<String, BufferedImage> fontImageFiles;

  public ResourcePack(Map<String, BufferedImage> fontImageFiles) {
    this.fontImageFiles = fontImageFiles;
  }

  public static ResourcePack loadFromFile(File file) {
    Preconditions.checkNotNull(file, "File cannot be null");
    ResourcePackLoader loader = ResourcePackFileLoader.createLoader(file);
    return loader.load();
  }

  public BufferedImage getImage(String name) {
    return fontImageFiles.getOrDefault(name, null);
  }

  public Map<String, BufferedImage> getFontImageFiles() {
    return fontImageFiles;
  }
}

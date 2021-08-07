package xyz.tozymc.mcfontpreview.util;

import com.google.common.base.Preconditions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Images {
  private Images() {}

  public static BufferedImage readImage(File file) {
    Preconditions.checkNotNull(file, "File cannot be null");
    try {
      return ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}

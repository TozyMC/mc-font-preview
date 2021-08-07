package xyz.tozymc.mcfontpreview.splitter;

import com.google.common.base.Preconditions;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

public interface ImageSplitter {
  int CHAR_PER_IMAGE = 16;
  int CHUNKS = 256;

  Stream<BufferedImage> split(BufferedImage image);

  class ImageSplitterImpl implements ImageSplitter {

    @Override
    public Stream<BufferedImage> split(BufferedImage image) {
      Preconditions.checkNotNull(image, "Image cannot be null");

      int chunkHeight = image.getHeight() / CHAR_PER_IMAGE;
      int chunkWidth = image.getWidth() / CHAR_PER_IMAGE;
      int count = 0;
      BufferedImage[] images = new BufferedImage[CHUNKS];
      for (int x = 0; x < CHAR_PER_IMAGE; x++) {
        for (int y = 0; y < CHAR_PER_IMAGE; y++) {
          //Initialize the image array with image chunks
          images[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

          // draws the image chunk
          Graphics2D gr = images[count++].createGraphics();
          gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
              chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
          gr.dispose();
        }
      }
      return Arrays.stream(images);
    }
  }
}

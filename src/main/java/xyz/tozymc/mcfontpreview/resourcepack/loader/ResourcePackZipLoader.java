package xyz.tozymc.mcfontpreview.resourcepack.loader;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import xyz.tozymc.mcfontpreview.util.Archives;

public class ResourcePackZipLoader extends ResourcePackFileLoader {
  protected ResourcePackZipLoader(File file) {
    super(file);
  }

  @Override
  protected File loadFile() {
    Path tempDir = Preconditions.checkNotNull(createTempDir());
    File target = tempDir.toFile();
    Archives.unzip(this.file, target);
    return target;
  }

  private Path createTempDir() {
    try {
      return Files.createTempDirectory(file.getName() + "-" + UUID.randomUUID());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}

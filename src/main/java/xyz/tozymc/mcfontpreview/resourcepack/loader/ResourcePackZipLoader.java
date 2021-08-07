package xyz.tozymc.mcfontpreview.resourcepack.loader;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ResourcePackZipLoader extends ResourcePackFileLoader {
  protected ResourcePackZipLoader(File file) {
    super(file);
  }

  @Override
  protected File loadFile() {
    Path tempDir = Preconditions.checkNotNull(createTempDir());
    File file = tempDir.toFile();
    ZipFile zipFile = new ZipFile(this.file);
    try {
      zipFile.extractAll(file.getAbsolutePath());
    } catch (ZipException e) {
      e.printStackTrace();
    }
    return file;
  }

  private Path createTempDir() {
    try {
      return Files.createTempDirectory(file.getName() + "-" + System.currentTimeMillis());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}

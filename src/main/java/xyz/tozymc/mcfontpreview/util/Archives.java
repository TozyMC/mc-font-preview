package xyz.tozymc.mcfontpreview.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class Archives {
  private Archives() {}

  public static void unzip(File zipFile, File destination) {
    byte[] buffer = new byte[1024];
    ZipEntry zipEntry;
    try (ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile))) {
      while ((zipEntry = in.getNextEntry()) != null) {
        File newFile = newFile(destination, zipEntry);
        if (zipEntry.isDirectory()) {
          if (!newFile.isDirectory() && !newFile.mkdirs()) {
            throw new IOException("Failed to create directory " + newFile);
          }
        } else {
          // fix for Windows-created archives
          File parent = newFile.getParentFile();
          if (!parent.isDirectory() && !parent.mkdirs()) {
            throw new IOException("Failed to create directory " + parent);
          }

          // write file content
          try (FileOutputStream out = new FileOutputStream(newFile)) {
            int len;
            while ((len = in.read(buffer)) > 0) {
              out.write(buffer, 0, len);
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destination = new File(destinationDir, zipEntry.getName());

    String destinationPath = destinationDir.getCanonicalPath();
    String destinationFilePath = destination.getCanonicalPath();

    if (!destinationFilePath.startsWith(destinationPath + File.separator)) {
      throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destination;
  }
}

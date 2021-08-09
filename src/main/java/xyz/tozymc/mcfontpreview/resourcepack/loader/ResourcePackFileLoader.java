package xyz.tozymc.mcfontpreview.resourcepack.loader;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import xyz.tozymc.mcfontpreview.resourcepack.ResourcePack;
import xyz.tozymc.mcfontpreview.util.Images;

public abstract class ResourcePackFileLoader implements ResourcePackLoader {
  private static final String ZIP_EXTENSION = ".zip";
  private static final ResourcePack EMPTY_RESOURCE_PACK = new ResourcePack(new HashMap<>());
  private static final FilenameFilter PNG_FILE_FILTER;
  private static final String MINECRAFT_FONT_TEXTURES_FOLDER;

  static {
    PNG_FILE_FILTER = (dir, name) -> name.endsWith(".png");
    MINECRAFT_FONT_TEXTURES_FOLDER = Joiner.on(File.separator)
        .join(new String[]{"assets", "minecraft", "textures", "font"});
  }

  protected final File file;

  protected ResourcePackFileLoader(File file) {this.file = file;}

  public static ResourcePackLoader createLoader(File file) {
    Preconditions.checkNotNull(file, "File cannot be null");
    //noinspection UnstableApiUsage
    String extension = Files.getFileExtension(file.getName());
    if (extension.equalsIgnoreCase(ZIP_EXTENSION)) {
      return new ResourcePackZipLoader(file);
    }
    return new ResourcePackFolderLoader(file);
  }

  protected abstract File loadFile();

  @Override
  public ResourcePack load() {
    File file = loadFile();
    if (!file.exists()) {
      return EMPTY_RESOURCE_PACK;
    }
    File fontFolder = new File(file, MINECRAFT_FONT_TEXTURES_FOLDER);
    if (!fontFolder.exists()) {
      return EMPTY_RESOURCE_PACK;
    }
    File[] files = fontFolder.listFiles(PNG_FILE_FILTER);
    if (files == null) {
      return EMPTY_RESOURCE_PACK;
    }
    Map<String, BufferedImage> images = Arrays.stream(files)
        .collect(Collectors.toMap(File::getName,
            imageFile -> Objects.requireNonNull(Images.readImage(imageFile))));
    return new ResourcePack(images);
  }

  public File getFile() {
    return file;
  }
}

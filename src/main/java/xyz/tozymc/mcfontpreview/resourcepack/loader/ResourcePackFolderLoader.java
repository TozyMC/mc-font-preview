package xyz.tozymc.mcfontpreview.resourcepack.loader;

import java.io.File;

public class ResourcePackFolderLoader extends ResourcePackFileLoader {
  protected ResourcePackFolderLoader(File file) {
    super(file);
  }

  @Override
  protected File loadFile() {
    return file;
  }
}

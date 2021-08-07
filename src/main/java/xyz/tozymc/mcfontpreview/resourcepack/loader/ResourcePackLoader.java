package xyz.tozymc.mcfontpreview.resourcepack.loader;

import java.io.File;
import xyz.tozymc.mcfontpreview.resourcepack.ResourcePack;

public interface ResourcePackLoader {

  ResourcePack load();

  File getFile();
}

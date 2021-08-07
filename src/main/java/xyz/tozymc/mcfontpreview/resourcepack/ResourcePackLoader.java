package xyz.tozymc.mcfontpreview.resourcepack;

import java.io.File;

public interface ResourcePackLoader {

  ResourcePack load();

  File getFile();
}

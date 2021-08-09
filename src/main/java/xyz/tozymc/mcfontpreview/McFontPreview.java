package xyz.tozymc.mcfontpreview;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import xyz.tozymc.mcfontpreview.resourcepack.ResourcePack;
import xyz.tozymc.mcfontpreview.splitter.ImageSplitter;
import xyz.tozymc.mcfontpreview.splitter.ImageSplitter.ImageSplitterImpl;

public final class McFontPreview {
  public static final int ROW = ImageSplitter.CHAR_PER_IMAGE;
  public static final int COLUMN = ImageSplitter.CHAR_PER_IMAGE;
  public static final int SPACING = 1;

  public static final ImageSplitter imageSplitter = new ImageSplitterImpl();

  private final ResourcePack resourcePack;
  private JComboBox<String> fontFilesComboBox;
  private JPanel fontPreviewPanel;

  McFontPreview(ResourcePack resourcePack) {
    this.resourcePack = resourcePack;
  }

  public void registerFontFilesComboBox(JComboBox<String> fontFilesComboBox) {
    this.fontFilesComboBox = fontFilesComboBox;
    this.fontFilesComboBox.removeAllItems();
    this.fontFilesComboBox.addActionListener(new RenderImageAction());
    resourcePack.getFontImageFiles().keySet().forEach(this.fontFilesComboBox::addItem);
  }

  public void registerPreviewPanel(JPanel fontPreviewPanel) {
    this.fontPreviewPanel = fontPreviewPanel;
    this.fontPreviewPanel.setLayout(new GridLayout(ROW, COLUMN, SPACING, SPACING));
  }

  public JComboBox<String> getComboBox() {
    return fontFilesComboBox;
  }

  public JPanel getFontPreviewPanel() {
    return fontPreviewPanel;
  }

  public ResourcePack getResourcePack() {
    return resourcePack;
  }

  class RenderImageAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (fontPreviewPanel == null) {
        return;
      }
      fontPreviewPanel.removeAll();

      String fileName = fontFilesComboBox.getItemAt(fontFilesComboBox.getSelectedIndex());
      BufferedImage fontImage = resourcePack.getImage(fileName);
      imageSplitter.split(fontImage)
          .map(BufferedImage::getSource)
          .map(Toolkit.getDefaultToolkit()::createImage)
          .map(ImageIcon::new)
          .forEach(icon -> fontPreviewPanel.add(new JLabel(icon)));
      fontPreviewPanel.updateUI();
    }
  }
}

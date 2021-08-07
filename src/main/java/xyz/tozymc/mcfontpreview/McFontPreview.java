package xyz.tozymc.mcfontpreview;

import java.awt.Dimension;
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

  private static final Dimension COMBO_BOX_SIZE = new Dimension(100, 50);
  private static final Dimension FONT_PREVIEW_PANEL_MINIMUM_SIZE = new Dimension(270, 270);

  private final ResourcePack resourcePack;
  private JComboBox<String> fontFilesComboBox;
  private JPanel fontPreviewPanel;

  public McFontPreview(ResourcePack resourcePack) {
    this.resourcePack = resourcePack;
    initialComponents();
  }

  private void initialComponents() {
    fontFilesComboBox = new JComboBox<>();
    fontFilesComboBox.setSize(COMBO_BOX_SIZE);
    fontFilesComboBox.addActionListener(new RenderImageAction());
    resourcePack.getFontImageFiles().keySet().forEach(fontFilesComboBox::addItem);

    fontPreviewPanel = new JPanel(new GridLayout(ROW, COLUMN, SPACING, SPACING));
    fontPreviewPanel.setMinimumSize(FONT_PREVIEW_PANEL_MINIMUM_SIZE);
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

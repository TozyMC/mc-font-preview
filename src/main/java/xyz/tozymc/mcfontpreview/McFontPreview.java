package xyz.tozymc.mcfontpreview;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import xyz.tozymc.mcfontpreview.resourcepack.CharPage;
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

  private static class IconCharLabel extends JLabel {
    private int charCode;

    private IconCharLabel(ImageProducer source) {
      super(new ImageIcon(Toolkit.getDefaultToolkit().createImage(source)));
      addMouseListener(new ClickToCopyListener());
    }

    private void addChar(int charCode) {
      this.charCode = charCode;
      setToolTipText("u+" + Integer.toHexString(charCode));
    }
  }

  private static class ClickToCopyListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
      if (!(e.getSource() instanceof IconCharLabel)) {
        return;
      }

      IconCharLabel source = (IconCharLabel) e.getSource();
      StringSelection selection = new StringSelection(Integer.toHexString(source.charCode));
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

  }

  private class RenderImageAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (fontPreviewPanel == null) {
        return;
      }
      fontPreviewPanel.removeAll();

      String fileName = fontFilesComboBox.getItemAt(fontFilesComboBox.getSelectedIndex());
      CharPage page = fileName.startsWith("ascii")
          ? CharPage.createAsciiPage()
          : CharPage.createUnicodePage(fileName);
      BufferedImage fontImage = resourcePack.getImage(fileName);
      imageSplitter.split(fontImage)
          .map(BufferedImage::getSource)
          .map(IconCharLabel::new)
          .peek(label -> label.addChar(page.next()))
          .forEach(fontPreviewPanel::add);
      fontPreviewPanel.updateUI();
    }
  }
}

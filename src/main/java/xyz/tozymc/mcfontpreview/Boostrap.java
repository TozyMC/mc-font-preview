package xyz.tozymc.mcfontpreview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import xyz.tozymc.mcfontpreview.layout.SingleComponentAspectRatioKeeperLayout;

public class Boostrap extends JFrame {
  private static final String TITLE = "Minecraft Font Preview";
  private static final int MIN_WIDTH = 390;
  private static final int MIN_HEIGHT = 460;

  private static final Dimension PREVIEW_PANEL_MINIMUM_SIZE = new Dimension(270, 270);
  private static final Dimension MINIMUM_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);
  private static final Dimension COMBO_BOX_SIZE = new Dimension(200, 25);
  private static final Dimension BUTTON_SIZE = new Dimension(150, 25);

  private JPanel previewPanel;
  private JComboBox<String> fontFilesComboBox;
  private JFileChooser resourcePackChooser;

  Boostrap() throws HeadlessException {
    this.frameInit();
  }

  @Override
  protected void frameInit() {
    super.frameInit();

    this.setTitle(TITLE);
    this.setMinimumSize(MINIMUM_SIZE);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.initialComponents();
    this.setResizable(true);
    this.pack();
    this.setVisible(true);
  }

  private void initialFontFilesComboBox() {
    fontFilesComboBox = new JComboBox<>();
    fontFilesComboBox.setRenderer(new FontFileComboBoxRenderer());
    fontFilesComboBox.setMaximumSize(COMBO_BOX_SIZE);
    fontFilesComboBox.setPreferredSize(COMBO_BOX_SIZE);
  }

  private void initialPreviewPanel() {
    previewPanel = new JPanel();
    previewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    previewPanel.setMinimumSize(PREVIEW_PANEL_MINIMUM_SIZE);
  }

  private void initialResourcePackChooser() {
    resourcePackChooser = new JFileChooser();
    resourcePackChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  }

  private void initialComponents() {
    initialFontFilesComboBox();
    initialPreviewPanel();
    initialResourcePackChooser();

    JLabel label = new JLabel(TITLE);
    label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
    label.setBorder(new EmptyBorder(10, 20, 10, 20));

    JButton button = new JButton("Choose resource pack...");
    button.addActionListener(new ResourcePackChoiceListener());
    button.setMaximumSize(BUTTON_SIZE);
    button.setPreferredSize(BUTTON_SIZE);

    GroupLayout layout = new GroupLayout(getContentPane());
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    JPanel wrapperPanel = new JPanel(new SingleComponentAspectRatioKeeperLayout());
    wrapperPanel.add(previewPanel);

    Group hGroup = layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(Alignment.CENTER)
            .addComponent(label)
            .addComponent(button)
            .addComponent(fontFilesComboBox)
            .addComponent(wrapperPanel));
    layout.setHorizontalGroup(hGroup);

    Group vGroup = layout.createSequentialGroup()
        .addComponent(label)
        .addComponent(button)
        .addComponent(fontFilesComboBox)
        .addPreferredGap(ComponentPlacement.RELATED, 10, 20)
        .addComponent(wrapperPanel);
    layout.setVerticalGroup(vGroup);

    getContentPane().setLayout(layout);
  }

  private static class FontFileComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
    private static final String NON_SELECTABLE_ITEM = "Select your font image...";

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value,
        int index, boolean isSelected, boolean cellHasFocus) {
      setText(index == -1 && value == null ? NON_SELECTABLE_ITEM : value);
      return this;
    }
  }

  private class ResourcePackChoiceListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (resourcePackChooser.showOpenDialog(Boostrap.this) != JFileChooser.APPROVE_OPTION) {
        return;
      }
      File file = resourcePackChooser.getSelectedFile();
    }
  }

}

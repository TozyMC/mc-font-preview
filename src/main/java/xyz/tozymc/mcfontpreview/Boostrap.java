package xyz.tozymc.mcfontpreview;

import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class Boostrap extends JFrame {
  private static final String TITLE = "Minecraft Font Preview";
  private static final int MIN_WIDTH = 640;
  private static final int MIN_HEIGHT = 360;
  private static final Dimension MINIMUM_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);

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

  private void initialComponents() {
  }
}

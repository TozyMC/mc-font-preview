package xyz.tozymc.mcfontpreview;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      setSystemLookAndFeel();
      new Boostrap();
    });
  }

  private static void setSystemLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  }
}

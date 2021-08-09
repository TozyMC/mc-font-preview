package xyz.tozymc.mcfontpreview.layout;

import com.google.common.base.Preconditions;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A Swing Layout that will shrink or enlarge keep the content of a container while keeping it's
 * aspect ratio. The caveat is that only a single component is supported or an exception will be
 * thrown. This is the component's getPreferredSize() method that must return the correct ratio. The
 * preferredSize will not be preserved but the ratio will.
 *
 * @author TozyMC (Create by francoismarot)
 * @see <a href="https://gist.github.com/fmarot/f04346d0e989baef1f56ffd83bbf764d">https://gist.github.com/fmarot/f04346d0e989baef1f56ffd83bbf764d</a>
 */
public class SingleComponentAspectRatioKeeperLayout implements LayoutManager {
  @Override
  public void addLayoutComponent(String arg0, Component arg1) {
  }

  @Override
  public void removeLayoutComponent(Component parent) {
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    return getSingleComponent(parent).getPreferredSize();
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    return preferredLayoutSize(parent);
  }

  @Override
  public void layoutContainer(Container parent) {
    Component component = getSingleComponent(parent);
    Insets insets = parent.getInsets();
    int maxWidth = parent.getWidth() - (insets.left + insets.right);
    int maxHeight = parent.getHeight() - (insets.top + insets.bottom);

    Dimension preferredSize = component.getPreferredSize();
    Dimension targetDim = getScaledDimension(preferredSize, new Dimension(maxWidth, maxHeight));

    double targetWidth = targetDim.getWidth();
    double targetHeight = targetDim.getHeight();

    double hGap = (maxWidth - targetWidth) / 2;
    double vGap = (maxHeight - targetHeight) / 2;

    // Set the single component's size and position.
    component.setBounds((int) hGap, (int) vGap, (int) targetWidth, (int) targetHeight);
  }

  private Component getSingleComponent(Container parent) {
    int parentComponentCount = parent.getComponentCount();
    Preconditions.checkArgument(parentComponentCount <= 1,
        getClass().getSimpleName() + " can not handle more than one component");
    return parent.getComponent(0);
  }

  private Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
    double widthRatio = boundary.getWidth() / imageSize.getWidth();
    double heightRatio = boundary.getHeight() / imageSize.getHeight();
    double ratio = Math.min(widthRatio, heightRatio);
    return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
  }
}
package xyz.tozymc.mcfontpreview.resourcepack;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class CharPage implements Iterator<Integer> {
  private final int start;
  private final int end;

  private int current;

  private CharPage(int start, int end) {
    this.start = start;
    this.end = end;
    this.current = start;
  }

  public static CharPage createUnicodePage(String pageName) {
    String page = pageName.substring(pageName.lastIndexOf('_') + 1, pageName.lastIndexOf('.'));
    page += "00";
    int start = 0x0;
    try {
      start = Integer.parseInt(page, 16);
    } catch (NumberFormatException ignored) {
    }
    return new CharPage(start, start + 0xff);
  }

  public static CharPage createAsciiPage() {
    return new CharPage(0x0, 0xff);
  }

  @Override
  public boolean hasNext() {
    return current <= end;
  }

  @Override
  public Integer next() {
    Preconditions.checkState(current <= end);
    return current++;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEachRemaining(Consumer<? super Integer> action) {
    IntStream.rangeClosed(start, end).forEach(action::accept);
  }
}

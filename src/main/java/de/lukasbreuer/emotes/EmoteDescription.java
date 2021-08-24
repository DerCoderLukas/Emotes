package de.lukasbreuer.emotes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor
public final class EmoteDescription {
  enum Type {
    STATIC,
    ANIMATED
  }

  public EmoteDescription(String name, String path, Type type) {
    this.name = name;
    this.path = path;
    this.type = type;
  }

  @Setter
  private String name;
  private String path;
  private Type type;
  @Accessors(fluent = true)
  private BufferedImage emoteImage;
  @Accessors(fluent = true)
  private List<BufferedImage> emoteGif;

  public void setPath(String path) throws Exception {
    this.path = path;
    loadIfPossible();
  }

  public void setType(Type type) throws Exception {
    this.type = type;
    loadIfPossible();
  }

  public boolean isStatic() {
    return type == Type.STATIC;
  }

  public boolean isAnimated() {
    return type == Type.ANIMATED;
  }

  private void loadIfPossible() throws Exception {
    if (path.equals("") || type == null) {
      return;
    }
    if (isStatic()) {
      emoteImage = loadEmoteImage();
      return;
    }
    emoteGif = loadEmoteGif();
  }

  private BufferedImage loadEmoteImage() throws Exception {
    return ImageIO.read(new File(path));
  }

  private List<BufferedImage> loadEmoteGif() throws Exception {
    return Gif.create(new File(path)).loadFrames();
  }
}

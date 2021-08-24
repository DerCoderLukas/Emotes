package de.lukasbreuer.emotes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Node;

@RequiredArgsConstructor(staticName = "create")
public final class Gif {
  private final File file;

  public List<BufferedImage> loadFrames() throws Exception {
    var frames = Lists.<BufferedImage>newArrayList();
    var reader = ImageIO.getImageReadersByFormatName("gif").next();
    reader.setInput(ImageIO.createImageInputStream(file), false);
    var count = reader.getNumImages(true);
    var precedingFrame = new AtomicReference<BufferedImage>();
    for (var i = 0; i < count; i++) {
      var rawImage = reader.read(i);
      iterateChildNodes(reader, precedingFrame, rawImage, i);
      var precedingFrameImage = precedingFrame.get();
      var frame = new BufferedImage(precedingFrameImage.getWidth(),
        precedingFrameImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
      frame.setData(precedingFrameImage.getData());
      frames.add(frame);
    }
    return frames;
  }

  private static final String IMAGEIO_TREE_NAME = "javax_imageio_gif_image_1.0";

  private void iterateChildNodes(
    ImageReader reader, AtomicReference<BufferedImage> precedingFrame,
    BufferedImage rawFrame, int frameIndex
  ) throws Exception {
    var metadata = reader.getImageMetadata(frameIndex);
    var tree = metadata.getAsTree(IMAGEIO_TREE_NAME);
    var children = tree.getChildNodes();
    for (var i = 0; i < children.getLength(); i++) {
      createImageFrame(precedingFrame, rawFrame, frameIndex, children.item(i));
    }
  }

  private static final String IMAGE_DESCRIPTOR = "ImageDescriptor";

  private void createImageFrame(
    AtomicReference<BufferedImage> precedingFrame, BufferedImage rawFrame,
    int frameIndex, Node item
  ) {
    if(!item.getNodeName().equals(IMAGE_DESCRIPTOR)) {
      return;
    }
    var attributes = findImageAttributes(item);
    if (frameIndex == 0) {
      precedingFrame.set(new BufferedImage(attributes.get("imageWidth"),
        attributes.get("imageHeight"), BufferedImage.TYPE_INT_ARGB));
    }
    patchImageFrame(precedingFrame.get(), rawFrame, attributes);
  }

  private static final String[] IMAGE_ATTRIBUTES = new String[] {
    "imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"
  };

  private Map<String, Integer> findImageAttributes(Node nodeItem) {
    var imageAttributes = Maps.<String, Integer>newHashMap();
    for (var i = 0; i < IMAGE_ATTRIBUTES.length; i++) {
      var attributes = nodeItem.getAttributes();
      var attributeNote = attributes.getNamedItem(IMAGE_ATTRIBUTES[i]);
      imageAttributes.put(IMAGE_ATTRIBUTES[i], Integer.valueOf(attributeNote.getNodeValue()));
    }
    return imageAttributes;
  }

  private void patchImageFrame(
    BufferedImage precedingFrame, BufferedImage rawFrame, Map<String, Integer> attributes
  ) {
    precedingFrame.getGraphics().drawImage(rawFrame, attributes.get("imageLeftPosition"),
      attributes.get("imageTopPosition"), null);
  }
}

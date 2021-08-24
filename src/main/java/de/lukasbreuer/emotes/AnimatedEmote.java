package de.lukasbreuer.emotes;

import java.util.List;

import com.google.common.collect.Lists;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class AnimatedEmote extends RisingEmote {
  public static AnimatedEmote create(
    Plugin plugin, EmoteDescription description, Player owner
  ) {
    var location = owner.getEyeLocation();
    location.setPitch(0);
    return new AnimatedEmote(plugin, description.getName(), owner,
      location.add(location.getDirection().setY(0).multiply(0.5)),
      loadIllustrations(description));
  }

  private final List<Illustration> illustrations;
  private int currentIllustration;

  private AnimatedEmote(
    Plugin plugin, String name, Player owner, Location location,
    List<Illustration> illustrations
  ) {
    super(plugin, name, owner, location);
    this.illustrations = illustrations;
  }

  @Override
  public void draw() {
    super.draw();
    currentIllustration++;
    if (currentIllustration >= illustrations.size()) {
      currentIllustration = 0;
    }
    drawIllustration(illustrations.get(currentIllustration));
  }

  private static final int ILLUSTRATION_DIMENSION = 40;

  private static List<Illustration> loadIllustrations(EmoteDescription description) {
    var illustrations = Lists.<Illustration>newArrayList();
    var frames = description.emoteGif();
    for (var frame : frames) {
      illustrations.add(Illustration.create(frame, ILLUSTRATION_DIMENSION,
        ILLUSTRATION_DIMENSION));
    }
    return illustrations;
  }
}

package de.lukasbreuer.emotes;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class StaticEmote extends RisingEmote {
  public static StaticEmote create(
    Plugin plugin, EmoteDescription description, Player owner
  ) {
    var location = owner.getEyeLocation();
    location.setPitch(0);
    return new StaticEmote(plugin, description.getName(), owner,
      location.add(location.getDirection().setY(0).multiply(0.5)),
      loadIllustration(description));
  }

  private final Illustration illustration;

  private StaticEmote(
    Plugin plugin, String name, Player owner, Location location,
    Illustration illustration
  ) {
    super(plugin, name, owner, location);
    this.illustration = illustration;
  }

  @Override
  public void draw() {
    super.draw();
    drawIllustration(illustration);
  }

  private static final int ILLUSTRATION_DIMENSION = 30;

  private static Illustration loadIllustration(EmoteDescription description) {
    return Illustration.create(description.emoteImage(),
      ILLUSTRATION_DIMENSION, ILLUSTRATION_DIMENSION);
  }
}

package de.lukasbreuer.emotes;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RisingEmote extends Emote {
  protected RisingEmote(
    Plugin plugin, String name, Player owner, Location location
  ) {
    super(plugin, name, owner, location);
  }

  private static final float GRADIENT = 0.03f;

  @Override
  public void draw() {
    location(location().add(0, GRADIENT, 0));
  }
}

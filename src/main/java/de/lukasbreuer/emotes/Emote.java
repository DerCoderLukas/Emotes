package de.lukasbreuer.emotes;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

@Accessors(fluent = true)
public abstract class Emote {
  private final Plugin plugin;
  @Getter
  private final String name;
  @Getter
  private final Player owner;
  @Getter
  @Setter
  private Location location;
  private BukkitTask drawTask;
  private int drawTime;

  protected Emote(
    Plugin plugin, String name, Player owner, Location location
  ) {
    this.plugin = plugin;
    this.name = name;
    this.owner = owner;
    this.location = location;
  }

  private static final int DRAW_DELAY = 2;

  public void spawn() {
    drawTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
      this::drawTick, 0, DRAW_DELAY);
  }

  private static final int TIME_TO_LIVE = 40;

  private void drawTick() {
    drawTime += DRAW_DELAY;
    if (drawTime >= TIME_TO_LIVE) {
      drawTask.cancel();
      return;
    }
    draw();
  }

  public abstract void draw();

  protected void drawIllustration(Illustration illustration) {
    illustration.display(Bukkit.getOnlinePlayers(), location);
  }

  public void remove() {
    if (drawTask == null) {
      return;
    }
    drawTask.cancel();
  }
}

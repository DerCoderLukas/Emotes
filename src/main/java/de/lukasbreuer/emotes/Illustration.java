package de.lukasbreuer.emotes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(staticName = "create")
public final class Illustration {
  private final BufferedImage image;
  private final int width;
  private final int height;

  private static final float PARTICLE_SIZE = 0.17f;
  private static final int PARTICLE_COUNT = 2;

  public void display(Collection<? extends Player> players, Location location) {
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        var color = averageColor(x, y);
        var dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(color.getRed(),
          color.getGreen(), color.getBlue()), PARTICLE_SIZE);
        if (isTransparent(color)) {
          continue;
        }
        spawnColoredParticles(players, particleLocation(location, x, y),
          PARTICLE_COUNT, dustOptions);
      }
    }
  }

  private Color averageColor(int x, int y) {
    var xStart = (int) (((float) x / width) * image.getWidth());
    var yStart = (int) (((float) y / height) * image.getHeight());
    return countColorAccumulation(xStart, yStart);
  }

  private Color countColorAccumulation(int xStart, int yStart) {
    var accumulatedAlpha = 0;
    var accumulatedRed = 0;
    var accumulatedGreen = 0;
    var accumulatedBlue = 0;
    var pixelCount = 0;
    for (var y = 0; y < image.getHeight() / height; y++) {
      for (var x = 0; x < image.getWidth() / width; x++) {
        var color = new Color(image.getRGB(xStart + x, yStart + y), true);
        accumulatedAlpha += color.getAlpha();
        accumulatedRed += color.getRed();
        accumulatedGreen += color.getGreen();
        accumulatedBlue += color.getBlue();
        pixelCount++;
      }
    }
    return new Color(((float) accumulatedRed / pixelCount / 255),
      (float) accumulatedGreen / pixelCount / 255,
      (float) accumulatedBlue / pixelCount / 255,
      (float) accumulatedAlpha / pixelCount / 255);
  }

  private boolean isTransparent(Color color) {
    return color.getAlpha() < 100;
  }

  private static final float ILLUSTRATION_SIZE_MULTIPLIER = 0.02f;

  private Location particleLocation(Location location, int x, int y) {
    var directionLocation = location.clone();
    directionLocation.setYaw(location.getYaw() + 90);
    directionLocation.setPitch(0);
    var directionVector = directionLocation.getDirection().normalize();
    return new Location(location.getWorld(),
      location.getX() + x * (directionVector.getX()) * ILLUSTRATION_SIZE_MULTIPLIER -
        (width / 2f) * ILLUSTRATION_SIZE_MULTIPLIER * (directionVector.getX()),
      location.getY() - y * ILLUSTRATION_SIZE_MULTIPLIER,
      location.getZ() + x * (directionVector.getZ()) * ILLUSTRATION_SIZE_MULTIPLIER -
        (height / 2f) * ILLUSTRATION_SIZE_MULTIPLIER * (directionVector.getZ()));
  }

  private void spawnColoredParticles(
    Collection<? extends Player> players, Location location, int amount,
    Particle.DustOptions dustOptions
  ) {
    for (var player : players) {
      player.spawnParticle(Particle.REDSTONE, location, amount, dustOptions);
    }
  }
}

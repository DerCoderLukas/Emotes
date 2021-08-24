package de.lukasbreuer.emotes;

import com.google.inject.Guice;
import com.google.inject.Inject;

import org.bukkit.plugin.java.JavaPlugin;

public final class EmotesPlugin extends JavaPlugin {
  @Inject
  private EmoteCommand emoteCommand;

  @Override
  public void onEnable() {
    saveResource("emotes.yml", false);
    var injector = Guice.createInjector(EmoteModule.create(this));
    injector.injectMembers(this);
    registerEmoteCommand();
  }

  private void registerEmoteCommand() {
    getCommand("emote").setExecutor(emoteCommand);
  }
}

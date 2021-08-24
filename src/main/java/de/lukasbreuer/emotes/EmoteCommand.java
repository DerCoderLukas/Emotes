package de.lukasbreuer.emotes;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__({@Inject}))
public final class EmoteCommand implements CommandExecutor {
  private final Plugin plugin;
  private final EmoteConfiguration emoteConfiguration;

  private static final String EMOTE_COMMAND_PERMISSION = "emote.command";

  @Override
  public boolean onCommand(
    CommandSender sender, Command command, String label, String[] arguments
  ) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You need to be a player");
      return false;
    }
    if (!sender.hasPermission(EMOTE_COMMAND_PERMISSION)) {
      sender.sendMessage(command.getPermissionMessage());
      return false;
    }
    if (arguments.length != 1) {
      sender.sendMessage("Syntax: /emote <name>");
      return false;
    }
    return playEmote((Player) sender, arguments);
  }

  private boolean playEmote(Player sender, String[] arguments) {
    var emoteName = arguments[0];
    var emoteDescriptionOptional = emoteConfiguration.findByName(emoteName);
    if (emoteDescriptionOptional.isEmpty()) {
      sender.sendMessage("Cant find Emote with name " + emoteName);
      return false;
    }
    sender.swingMainHand();
    var emoteDescription = emoteDescriptionOptional.get();
    if (emoteDescription.isStatic()) {
      StaticEmote.create(plugin, emoteDescription, sender).spawn();
      return true;
    }
    AnimatedEmote.create(plugin, emoteDescription, sender).spawn();
    return true;
  }
}

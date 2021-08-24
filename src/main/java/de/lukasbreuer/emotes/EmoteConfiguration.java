package de.lukasbreuer.emotes;


import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class EmoteConfiguration {
  private List<EmoteDescription> emotes;

  public Optional<EmoteDescription> findByName(String emoteName) {
    return emotes.stream()
      .filter(emote -> emote.getName().equalsIgnoreCase(emoteName))
      .findFirst();
  }
}

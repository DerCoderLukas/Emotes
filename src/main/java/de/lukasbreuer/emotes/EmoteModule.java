package de.lukasbreuer.emotes;

import java.nio.file.Path;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(staticName = "create")
public final class EmoteModule extends AbstractModule {
  private final Plugin plugin;

  @Provides
  @Singleton
  Plugin providePlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  YAMLFactory provideYamlFactory() {
    return YAMLFactory.builder()
      .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
      .build();
  }

  @Provides
  @Singleton
  ObjectMapper provideObjectMapper(YAMLFactory yamlFactory) {
    return new ObjectMapper(yamlFactory);
  }

  private static final String EMOTES_CONFIG_FILE_NAME = "emotes.yml";

  @Provides
  @Singleton
  EmoteConfiguration provideEmoteConfiguration(ObjectMapper objectMapper) throws Exception {
    return objectMapper.readValue(Path.of(plugin.getDataFolder().getPath(),
      EMOTES_CONFIG_FILE_NAME).toFile(), EmoteConfiguration.class);
  }
}

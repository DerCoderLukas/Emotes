# Emotes

Emotes is an easily customizable plugin that allows you to express emotions within Minecraft. The emotes appear in the form of particle images in front of the player. For copyright reasons, I am not allowed to use images not created by me here, so you have to download them yourself and set them up later in the config.

## Build Status
|             | Build Status                                                                                                            |
|-------------|-------------------------------------------------------------------------------------------------------------------------|
| Master      | ![Java CI with Gradle](https://github.com/DerCoderLukas/Emotes/workflows/Java%20CI%20with%20Gradle/badge.svg) |

## Requirements

• Java 16 \
• Spigot 1.17.1

## Installation

The installation of the plugin is very simple and can be done via the following steps.

• Download the jar from the release area.

• Put the jar in your plugins folder of your server and start it.

• Download funny, cool or well expressing emotes in form of images (png and jpg) or as gif.
Unfortunately I can't offer them directly, because I don't have the skills to
create my own emotes and don't have licenses for emotes from other artists.

• Configure your emotes in the emotes.yml config.

## Config

A fully configured config might look something like this

``` yaml
emotes:
  - name: "Happy"
    path: "path/plugins/Emotes/happy.png"
    type: STATIC
  - name: "Laugh"
    path: "path/plugins/Emotes/laugh.gif"
    type: ANIMATED
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://github.com/DerCoderLukas/Emotes/blob/master/LICENSE)
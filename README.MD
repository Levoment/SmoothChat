# Smooth Chat

# ⚠ Warning - this mod adds animation to the chat of the game which may potentially trigger seizures for people with photosensitive epilepsy. Usage discretion is advised. Read the license/unlicense.

## About

Mod for making the chat smoothly move upwards whenever sending/receiving a message.
The mod is available in Fabric and Forge.

https://user-images.githubusercontent.com/30224138/181398036-bd8b44a8-52ae-4ec1-8ca5-501ff1046f89.mp4


## Requirements

### Fabric:

[![Requires Fabric API](./resources/requires-fabric-api.svg)](https://github.com/FabricMC/fabric)

[![Requires Architectury](./resources/requires-architectury.svg)](https://github.com/architectury/architectury-api)

### Forge:

[![Requires Architectury](./resources/requires-architectury.svg)](https://github.com/architectury/architectury-api)

## Commands

- `/smoothchat reset` - resets all the values to default
- `/smoothchat true/false` - activates or deactivates the smooth chat
- `/smoothchat speed <float-seconds>` - sets the time in seconds or fractions of a second for the chat transition
- `/smoothchat config reload` - loads the values set in the configuration file for the mod

## Configuration

The mod creates a file named `smoothchat-config.json` in the config directory of the game instance.
This instance looks like

```json
{
  "SmoothChat": true,
  "TransitionTimeFloat": 0.5
}
```

- `SmoothChat` - a boolean that indicates whether the smooth chat is enabled or not
- `TransitionTimeFloat` - a float specifying the time for the transition in seconds or fractions of a second


## License

This mod was made using various libraries. They have their own licenses:

- Architectury API - https://github.com/architectury/architectury-api 
- Fabric API - https://github.com/FabricMC/fabric

This mod itself is available under [The Unlicense](https://unlicense.org/)

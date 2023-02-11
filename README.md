# Minecraft Hue Ambiance

A plugin to reflect the minecraft world in Philips Hue color lighting.

This plugin aims to update Philips Hue color lights according to in-game events. Currently the following things have an
effect on the light color:

- Current biome (colors synthesised by Chat-GPT!)
- Taking damage
- Standing nearby fire
- Sleeping
- Thunder lightning strike

More are being added...

The plugin implements a priority system to give certain effects priority over others (temporary effects generally have a
high priority, while background effects generally have a low priority).

Note: The plugin currently only supports one hardcoded player and hue room name. I'll be adding support for multiple
players later (something like a command to add light identifiers, eg. hue room names/light names per player).

# Connecting to hue bridge

You can use the `/connecthue [address]` command to automatically connect to a Philips Hue bridge. If no address is
provided, it will search for an available hub on the network.

You can also manually configure the hub address and api key in the config using the fields `hue.address`
and `hue.api-key`.

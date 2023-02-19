# Minecraft Hue Ambiance

A plugin to reflect the minecraft world in Philips Hue color lighting.

This plugin aims to update Philips Hue color lights according to in-game events. Currently, the following things have an
effect on the light color:

- Current biome (colors synthesised by Chat-GPT!)
- Taking damage
- Standing nearby fire
- Sleeping
- Thunder lightning strike

More are being added... If you want a specific color effect added, feel free to implement it and open a pull-request!

The plugin implements a priority system to give certain effects priority over others (temporary effects generally have a
high priority, while background effects generally have a low priority).

The plugin supports multiple players, and the hue ambiance feature can be enabled/disabled per player. Each player needs
their own Hue "room" assigned.

# Connecting to hue bridge

You can use the `/connecthue [address]` command to automatically connect to a Philips Hue bridge. If no address is
provided, it will search for an available hub on the network.

You can also manually configure the hub address and api key in the config using the fields `hue.address`
and `hue.api-key`.

# Commands

The plugin exposes 3 commands to configure the global and per-player functionality of the plugin.

Note: The plugin currently does not check any permissions, because the plugin is intended to be used
in a trusted environment.

`/hueconnect [address]` - Connect to a Hue bridge.

`/hueenable <enable | disable>` - Enable or disable the Hue ambiance functionality.

`/hueroom <room>` - Set the linked room (autocomplete available).

# The future

In the future I would like to add/change the following features. If you need any of these features, or if you need a
feature that's not listed here, feel free to implement it and open a pull-request!

- Remote Hue bridge support (through tunneling or the Hue API)
- Add permissions
- Save and restore the state of the lamps

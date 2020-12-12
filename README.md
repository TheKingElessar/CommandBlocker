# Command Blocker

This plugin was made to aid with setting up command-block/datapack minigames designed for Vanilla Minecraft. When you set these up on a server, you often run into a lot of permission problems. I've found that I've had to give normal (default) players access to commands such as `/tp` or `/kill` because of how the minigame was designed for Vanilla.

This plugin allows you to block certain commands coming from player clients. So, if they need the `/kill` command to activate a sign, they can use the sign, but you can use this plugin to block them from sending the command themselves.

I'm not an expert; this possibly could be bypassed or exploited if someone tried hard enough. If you have significant security concerns for your server, maybe think twice about entrusting it to this plugin. But if you have permissions per-server and you only have this on the applicable vanilla minigame server, it should be alright.

Drop [this bad boy](https://github.com/TheKingElessar/CommandBlocker/releases) in your `plugins` folder. Start the server, and the `config.yml` file will be generated. You can see it below.

### Commands:
`/blocker add <command> <permission | "none"> <group>`: Set how many seconds we should wait before MOLE MODE after it's down to the final two.

`/blocker reload`: Reloads the config file.

### Permissions:
`blocker.add`: Allows adding of more blocked commands.

`blocker.reload`: Allows reloading of the config.

### Config
```
# These are examples to show how the configuration works. Chances are they won't mess with any of your commands.
blocked_commands:

  # The player must have the permission commandblocker.kill to use this command.
  - command: givemediamonds
    permission: commandblocker.givemediamonds

  # The player must have the permission group operator to use this command.
  - command: setoperator
    group: operator

  # The player must have EITHER the permission commandblocker.toggledownfall OR the permission group raingod to use this command.
  - command: changeweather
    permission: other.plugin.toggledownfall
    group: raingod

  # The player can never use this command.
  - command: changegamemode
```
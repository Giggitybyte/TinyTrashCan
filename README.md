# Tiny Trash Can 
[![Fabric Mod](https://img.shields.io/badge/modloader-fabric-informational)](https://fabricmc.net/use/)
[![GitHub Release](https://img.shields.io/github/v/release/Giggitybyte/TinyTrashCan?include_prereleases)](https://github.com/Giggitybyte/TinyTrashCan/releases)
[![Curseforge Download](http://cf.way2muchnoise.eu/full_442850_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/tiny-trash-can/files/all)
[![Modrinth Download](https://is.gd/rBCHTP)](https://modrinth.com/mod/tiny-trash-can/versions)
[![Discord Server](https://img.shields.io/discord/385375030755983372.svg?label=discord)](https://discord.gg/UPKuVWgU4G)

![Sunrise](https://s.thegiggitybyte.me/74o7J.png)

![GUI](https://s.thegiggitybyte.me/0KkhK.png)

## Origin
A while ago, a friend of mine wanted to add [Trash It](https://github.com/Draylar/trash-it) to our friends-only 1.16.4 modpack. After giving it a test, I discovered that *Trash It* was effectively locked to 1.16.1 because the GUI library used ([Spinnery](https://github.com/vini2003/Spinnery)) would call a method which did not exist on newer versions and cause the game to crash. Bumping to the latest version for *Spinnery* did not solve the issue, and no other updates were available as *Spinnery* had been abandoned.

Since my buddy was adamant that a trash can mod be included the pack, I decided to repurpose the model from *Trash It* and quickly write up a new 1.16.4 project which utilizes an actively developed GUI library (ended up being [LibGui](https://github.com/CottonMC/LibGui)). I eventually decided to publicize this project, so I cleaned things up a bit and updated it to 1.16.5.

I'll be releasing updates for new versions of Minecraft as time permits.

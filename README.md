# FixAAC

An Orion mod to fix [AAC](https://www.spigotmc.org/resources/aac-advanced-anti-cheat-hack-kill-aura-blocker.6442/) plugin startup

## What?

Server owners get this message when they try to use AAC with Orion: https://paste.wut.ee/cikehasogu.md  
And problem disappears when Orion is removed

This mod fixes given issue, by defining correct package in PluginClassLoader

AAC checks jar metadata and class implementation version, if neither of them doesn't work then plugin bails out.

## License
WTFPL
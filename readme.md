# Ionicity

**Ionicity is currently alpha software. Use with caution.**

Ionicity is a [Velocity](https://velocitypowered.com/) plugin for sharing chat across multiple servers in a network. 
It works by intercepting chat packets and broadcasting them to every player on every server individually. Ionicity also
includes support for [MiniMessage](https://docs.adventure.kyori.net/minimessage) placeholders and basic support 
for [LuckPerms](https://luckperms.net/).

## Building
Ionicity is a Gradle project. To build it, you will need an up-to-date build of JDK 17 installed
on your machine. To get started, download the source code (either by downloading the ZIP file or 
`git clone`-ing it). Then open the folder with the source code in a terminal or command prompt 
and run `./gradlew shadowJar`. You will find the plugin's JAR file in `./build/libs`.

## Other Projects
Also check out our other projects:
- [Ferocity](https://github.com/OrbisMinecraft/ferocity), a Velocity plugin for sharing the tab list across multiple servers
- [Plurality](https://github.com/OrbisMinecraft/plurality), a Velocity plugin which remembers the server a player logs off in
- [Tenacity](https://github.com/OrbisMinecraft/tenacity), a Paper plugin which saves player's inventories in a database so they can be shared across servers

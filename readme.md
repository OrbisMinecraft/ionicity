# Ionicity

**Ionicity is currently alpha software. Use with caution.**

Ionicity is a [Velocity](https://velocitypowered.com/) plugin for sharing chat and the player list across multiple
servers in a network. It works by intercepting chat packets and broadcasting them to every player on every server
individually.

## Building
Ionicity is a Gradle project. To build it, you will need an up-to-date build of JDK 17 installed
on your machine. To get started, download the source code (either by downloading the ZIP file or 
`git clone`-ing it). Then open the folder with the source code in a terminal or command prompt 
and run `./gradlew build`. You will find the plugin's JAR file in `./build/libs`.

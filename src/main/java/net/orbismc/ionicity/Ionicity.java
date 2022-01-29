package net.orbismc.ionicity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

@Plugin(
		id = "ionicity",
		name = "ionicity",
		version = BuildConstants.VERSION,
		description = "A Velocity plugin for chat and player list synchronization across multiple servers.",
		url = "https://plugins.orbismc.net"
)
public class Ionicity {
	public HashMap<UUID, String> playerLastServers = new HashMap<>();

	@Inject
	private Logger logger;

	@Inject
	private ProxyServer server;

	@Inject
	@DataDirectory
	private Path dataDirectory;

	public ProxyServer getServer() {
		return server;
	}

	public Logger getLogger() {
		return logger;
	}

	@Subscribe
	public void onProxyInitialization(final @NonNull ProxyInitializeEvent event) {
		if (!dataDirectory.toFile().exists()) {
			dataDirectory.toFile().mkdirs();
		}

		server.getEventManager().register(this, new IonicityEventListener(this));

		try {
			final var target = dataDirectory.resolve("players.yml").toFile();

			if (target.exists()) {
				final var x = YAMLConfigurationLoader
						.builder()
						.setSource(() -> new BufferedReader(new FileReader(target)))
						.build()
						.load();

				for (final var v : x.getChildrenMap().entrySet()) {
					this.playerLastServers.put((UUID) v.getKey(), v.getValue().getString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	public void onProxyShutdown(final @NonNull ProxyShutdownEvent event) {
		try {
			final var target = dataDirectory.resolve("players.yml").toFile();
			final var f = ConfigurationNode.root();
			f.setValue(this.playerLastServers);

			YAMLConfigurationLoader
					.builder()
					.setSink(() -> new BufferedWriter(new FileWriter(target)))
					.build()
					.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

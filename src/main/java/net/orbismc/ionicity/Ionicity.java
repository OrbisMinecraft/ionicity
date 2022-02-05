/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.luckperms.api.LuckPermsProvider;
import net.orbismc.ionicity.format.LuckPermsFormatter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
		id = "ionicity",
		name = "Ionicity",
		version = BuildConstants.VERSION,
		description = "A Velocity plugin for chat and player list synchronization across multiple servers."
)
public class Ionicity {
	@Inject
	private Logger logger;

	@Inject
	private ProxyServer server;

	@Inject
	@DataDirectory
	private Path configurationDirectory;

	public ProxyServer getServer() {
		return server;
	}

	@Subscribe
	public void onProxyInitialization(final @NonNull ProxyInitializeEvent event) {
		final var config = IonicityConfig.load(configurationDirectory.resolve("config.yml").toFile());
		final var formatter = new IonicityChatFormatter(config.getNode("format").getString("<%username%> %message%"));

		// If LuckPerms is installed, add its formatter
		if (server.getPluginManager().isLoaded("luckperms")) {
			formatter.addFormatter(new LuckPermsFormatter(LuckPermsProvider.get()));
		}

		server.getEventManager().register(this, new IonicityEventListener(this, formatter));
	}
}

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
import net.orbismc.ionicity.command.IonicityCommand;
import net.orbismc.ionicity.command.MessageCommand;
import net.orbismc.ionicity.format.TemplateProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
		id = "ionicity",
		name = "Ionicity",
		version = BuildConstants.VERSION,
		authors = {"lmichaelis"},
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

	private String format;

	public ProxyServer getServer() {
		return server;
	}

	public String getFormat() {
		return this.format;
	}

	@Subscribe
	public void onProxyInitialization(final @NonNull ProxyInitializeEvent event) {
		final var commands = server.getCommandManager();

		this.reload();
		TemplateProvider.addAvailableProviders(server.getPluginManager());

		server.getEventManager().register(this, new IonicityEventListener(this));
		commands.register(commands.metaBuilder("ionicity").build(), new IonicityCommand(this));
		commands.register(commands.metaBuilder("m").build(), new MessageCommand(this));
	}

	/**
	 * Reloads the plugin's configuration.
	 */
	public void reload() {
		final var config = IonicityConfig.load(configurationDirectory.resolve("config.yml").toFile());
		this.format = config.getNode("format").getString("<<player-name>> <message>");
	}
}

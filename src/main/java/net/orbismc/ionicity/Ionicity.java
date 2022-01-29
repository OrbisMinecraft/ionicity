/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

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

	public ProxyServer getServer() {
		return server;
	}

	@Subscribe
	public void onProxyInitialization(final @NonNull ProxyInitializeEvent event) {
		server.getEventManager().register(this, new IonicityEventListener(this));
	}
}

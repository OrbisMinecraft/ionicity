/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.format;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A basic formatter which allows for embedding generic player information.
 */
public class BasicPlayerFormatter extends Formatter {
	@Override
	public String format(@NotNull String format, @NotNull Player player) {
		return format.replace("%player_name%", player.getUsername())
				.replace("%player_ping%", Long.toString(player.getPing()))
				.replace("%player_server%", player.getCurrentServer().get().getServerInfo().getName());
	}
}

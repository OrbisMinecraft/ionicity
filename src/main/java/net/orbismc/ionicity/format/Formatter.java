/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.format;

import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPermsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class Formatter {
	private static final ArrayList<Formatter> CHAIN = new ArrayList<>();

	/**
	 * Adds formatters for all available and supported plugins.
	 *
	 * @param pluginManager Velocity's plugin manager.
	 */
	public static void addAvailableFormatters(final @NotNull PluginManager pluginManager) {
		if (pluginManager.isLoaded("luckperms")) addFormatter(new LuckPermsFormatter(LuckPermsProvider.get()));
		addFormatter(new BasicPlayerFormatter());
	}

	/**
	 * Adds a new formatter to the global formatter chain. It will be applied whenever {@link #formatString} is called.
	 *
	 * @param formatter The formatter to add.
	 */
	public static void addFormatter(final @NotNull Formatter formatter) {
		CHAIN.add(formatter);
	}

	/**
	 * Formats the given string, applying all formatters previously added through {@link #addFormatter(Formatter)}.
	 *
	 * @param format The string to format.
	 * @param player The player to use when formatting.
	 * @return The formatted string.
	 */
	public static String formatString(@NotNull String format, final @NotNull Player player) {
		for (final var formatter : CHAIN)
			format = formatter.format(format, player);

		return format;
	}

	/**
	 * Replaces the placeholders in the given format string with their proper value according to the given player.
	 *
	 * @param format The string in which to replace the placeholders.
	 * @param player The player to insert values for.
	 * @return The new, formatted, string.
	 */
	public abstract String format(final @NotNull String format, final @NotNull Player player);
}

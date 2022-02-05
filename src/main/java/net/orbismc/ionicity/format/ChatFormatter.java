/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.format;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

public interface ChatFormatter {
	/**
	 * Replaces the placeholders in the given format string with their proper value according to the given player.
	 *
	 * @param format The string in which to replace the placeholders.
	 * @param player The player to insert values for.
	 * @return The new, formatted, string.
	 */
	String format(final @NotNull String format, final @NotNull Player player);
}

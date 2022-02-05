/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.orbismc.ionicity.format.ChatFormatter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

public final class IonicityChatFormatter {
	private static final Pattern COLOR_PATTERN = Pattern.compile("&([0-9a-fk-or])");
	private final ArrayList<ChatFormatter> formatters = new ArrayList<>();
	private final String format;

	public IonicityChatFormatter(final @NotNull String format) {
		this.format = format;
	}

	public void addFormatter(final @NotNull ChatFormatter formatter) {
		this.formatters.add(formatter);
	}

	@Contract("_, _ -> new")
	public @NotNull Component format(final @NotNull Player player, final @NotNull String message) {
		// replace format/color codes
		var formatted = COLOR_PATTERN.matcher(this.format).replaceAll(match -> "§" + match.group(1));

		// replace username and message
		formatted = formatted.replace("%username%", player.getUsername()).replace("%message%", message);

		// apply sub-formatters
		for (final var formatter : this.formatters)
			formatted = formatter.format(formatted, player);

		return Component.text(formatted);
	}
}

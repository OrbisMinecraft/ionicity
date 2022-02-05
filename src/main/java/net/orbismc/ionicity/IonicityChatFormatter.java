/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.orbismc.ionicity.format.Formatter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public final class IonicityChatFormatter {
	private static final Pattern COLOR_PATTERN = Pattern.compile("&([0-9a-fk-or])");
	private final String format;

	public IonicityChatFormatter(final @NotNull String format) {
		this.format = format;
	}

	@Contract("_, _ -> new")
	public @NotNull Component format(final @NotNull Player player, final @NotNull String message) {
		// replace format/color codes
		var formatted = COLOR_PATTERN.matcher(this.format).replaceAll(match -> "§" + match.group(1));

		// replace username and message
		formatted = formatted.replace("%message%", message);

		// apply sub-formatters
		formatted = Formatter.formatString(formatted, player);

		return Component.text(formatted);
	}
}

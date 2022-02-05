/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.format;

import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.platform.PlayerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Formats a message with LuckPerms placeholders.
 */
public class LuckPermsFormatter implements ChatFormatter {
	private static final Pattern META_PATTERN = Pattern.compile("%luckperms_meta_([a-zA-Z0-9_.:/*+#-])%");
	private final PlayerAdapter<Player> adapter;

	public LuckPermsFormatter(final @NotNull LuckPerms api) {
		this.adapter = api.getPlayerAdapter(Player.class);
	}

	@Override
	public String format(@NotNull String format, @NotNull Player player) {
		final var metadata = this.adapter.getMetaData(player);

		// replace prefix and suffix
		format = format.replace("%luckperms_prefix%", Objects.requireNonNullElse(metadata.getPrefix(), ""))
				.replace("%luckperms_suffix%", Objects.requireNonNullElse(metadata.getSuffix(), ""));

		// then replace all other metadata placeholders
		return META_PATTERN.matcher(format).replaceAll(match -> Objects.requireNonNullElse(metadata.getMetaValue(match.group(1)), ""));
	}
}

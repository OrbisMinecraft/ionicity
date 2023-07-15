/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.orbismc.ionicity.Ionicity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MessageCommand implements SimpleCommand {
	public static final LegacyComponentSerializer SERIAL = LegacyComponentSerializer.legacyAmpersand();
	private final Ionicity plugin;

	public MessageCommand(final @NotNull Ionicity plugin) {
		this.plugin = plugin;
	}

	@Override
	public void execute(final @NotNull Invocation invocation) {
		final var args = invocation.arguments();
		final var source = invocation.source();

		if (!(source instanceof final Player sourcePlayer)) return;

		if (args.length < 2) {
			source.sendMessage(Component.text("Usage: /m <player> <message>", NamedTextColor.RED));
			return;
		}

		String playerName = args[0];
		String message = String.join(" ", Arrays.stream(args).skip(1).toList());

		final var player = this.plugin.getServer().getPlayer(playerName);
		if (player.isEmpty()) {
			source.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
		} else {
			final var comp = SERIAL.deserialize(message);

			source.sendMessage(
					Component.text("← to ", NamedTextColor.GRAY, TextDecoration.ITALIC)
							.append(Component.text(playerName, NamedTextColor.GRAY))
							.append(Component.text(" » ", NamedTextColor.GRAY))
							.append(comp));
			player.get().sendMessage(
					Component.text("→ from ", NamedTextColor.GRAY, TextDecoration.ITALIC)
							.append(Component.text(sourcePlayer.getUsername(), NamedTextColor.GRAY))
							.append(Component.text(" » ", NamedTextColor.GRAY))
							.append(comp));
		}
	}

	@Override
	public List<String> suggest(final Invocation invocation) {
		return this.plugin.getServer().getAllPlayers().stream().map(Player::getUsername).toList();
	}

	@Override
	public boolean hasPermission(final @NotNull Invocation invocation) {
		return true;
	}
}

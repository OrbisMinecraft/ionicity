/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * The main velocity event listener class of <i>ionicity</i>. Handles and implements all handlers necessary for
 * sending cross-server messages.
 */
public class IonicityEventListener {
	private final IonicityChatFormatter formatter;
	private final Ionicity plugin;

	public IonicityEventListener(Ionicity plugin, IonicityChatFormatter formatter) {
		this.plugin = plugin;
		this.formatter = formatter;
		this.plugin.getServer().getScheduler().buildTask(plugin, this::update).repeat(1000, TimeUnit.MILLISECONDS).schedule();
	}

	@Subscribe
	public void onPlayerChat(final @NotNull PlayerChatEvent event) {
		// Deny sending the original message at all
		event.setResult(PlayerChatEvent.ChatResult.denied());

		// Broadcast message to all servers
		for (final var target : plugin.getServer().getAllPlayers()) {
			target.sendMessage(this.formatter.format(event.getPlayer(), event.getMessage()));
		}
	}

	@Subscribe(order = PostOrder.LAST)
	public void connect(final @NotNull ServerConnectedEvent event) {
		update();
	}

	@Subscribe(order = PostOrder.LAST)
	public void disconnect(final @NotNull DisconnectEvent event) {
		update();
	}

	public void update() {
		for (Player player : this.plugin.getServer().getAllPlayers()) {
			for (Player player1 : this.plugin.getServer().getAllPlayers()) {
				if (!player.getTabList().containsEntry(player1.getUniqueId())) {
					player.getTabList().addEntry(
							TabListEntry.builder()
									.displayName(Component.text(player1.getUsername()))
									.profile(player1.getGameProfile())
									.gameMode(0)
									.latency(30)
									.tabList(player.getTabList())
									.build()
					);
				}
			}

			player.sendPlayerListHeaderAndFooter(Component.text("Welcome"), Component.text("orbismc.net"));
		}
	}
}

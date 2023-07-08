/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.orbismc.ionicity.format.TemplateProvider;
import org.jetbrains.annotations.NotNull;

/**
 * The main velocity event listener class of <i>ionicity</i>. Handles and implements all handlers necessary for
 * sending cross-server messages.
 */
public class IonicityEventListener {
	private final MiniMessage message;
	private final Ionicity plugin;

	public IonicityEventListener(Ionicity plugin) {
		this.plugin = plugin;
		this.message = MiniMessage.builder().build();
	}

	@Subscribe
	public void onPlayerChat(final @NotNull PlayerChatEvent event) {
		// Deny sending the original message at all
		event.setResult(PlayerChatEvent.ChatResult.denied());

		final var templates = TemplateProvider.getAllTemplates(event.getPlayer());
		templates.add(TagResolver.resolver("message", Tag.inserting(Component.text(event.getMessage()))));

		final var message = this.message.deserialize(plugin.getFormat(), TagResolver.resolver(templates));

		// Broadcast message to all servers
		for (final var target : plugin.getServer().getAllPlayers()) {
			target.sendMessage(message);
		}
	}
}

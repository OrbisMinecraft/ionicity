/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity.format;

import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.minimessage.Template;
import net.luckperms.api.LuckPermsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class TemplateProvider {
	private static final ArrayList<TemplateProvider> CHAIN = new ArrayList<>();

	/**
	 * Adds formatters for all available and supported plugins.
	 *
	 * @param pluginManager Velocity's plugin manager.
	 */
	public static void addAvailableProviders(final @NotNull PluginManager pluginManager) {
		if (pluginManager.isLoaded("luckperms"))
			addFormatter(new LuckPermsTemplateProvider(LuckPermsProvider.get()));
		addFormatter(new BasicPlayerTemplateProvider());
	}

	public static void addFormatter(final @NotNull TemplateProvider provider) {
		CHAIN.add(provider);
	}

	public static List<Template> getAllTemplates(final @NotNull Player player) {
		final List<Template> all = new ArrayList<>();

		for (final var formatter : CHAIN)
			all.addAll(formatter.getTemplates(player));

		return all;
	}

	public abstract List<Template> getTemplates(final @NotNull Player player);
}

/*
 * Copyright © 2022 Luis Michaelis
 * SPDX-License-Identifier: LGPL-3.0-only
 */
package net.orbismc.ionicity;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class IonicityChatFormatter {
	@Contract("_, _ -> new")
	public static @NotNull Component format(final @NotNull Player player, final @NotNull String message) {
		return Component.text(player.getUsername() + " §7»§r " + message);
	}
}

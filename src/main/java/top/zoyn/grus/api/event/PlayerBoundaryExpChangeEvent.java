package top.zoyn.grus.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerBoundaryExpChangeEvent extends Event implements Cancellable {

    private double from;
    private double to;
    private Player player;
    private boolean cancel = false;

    private static final HandlerList handlers = new HandlerList();

    public PlayerBoundaryExpChangeEvent(double from, double to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}

package fr.cactt4ck.cacplugin;

import java.util.UUID;

@SuppressWarnings("all")
public class NotEnoughMoneyException extends Exception {

	private static final long serialVersionUID = -6436132599941789167L;

	public NotEnoughMoneyException(UUID uuid, int money) {
        this("The player " + CacUtils.getPlayerName(uuid) + " can't have " + money + " PO ! Operation cancelled !");
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughMoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
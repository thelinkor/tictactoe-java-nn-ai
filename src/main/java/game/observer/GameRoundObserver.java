package game.observer;

import game.Game;

public interface GameRoundObserver {
    void notify(Game gameToObserve);
}

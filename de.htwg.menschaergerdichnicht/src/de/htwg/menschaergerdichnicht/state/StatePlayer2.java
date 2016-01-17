package de.htwg.menschaergerdichnicht.state;

import de.htwg.menschaergerdichnicht.model.Player;

public class StatePlayer2 implements IState{

	@Override
	public Player currentPlayer(Player player) {
		player = new Player(2, "YELLOW", 'Y');
		player.setState(this);
 		return player;
	}
	@Override
	public String toString() {
		return "YELLOW";
	}
}

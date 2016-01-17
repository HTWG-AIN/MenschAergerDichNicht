package de.htwg.menschaergerdichnicht.state;

import de.htwg.menschaergerdichnicht.model.Player;

public class StatePlayer1 implements IState {

	@Override
	public Player currentPlayer(Player player) {
		Player p = new Player(1, "BLUE", 'B');
		p.setState(this);
 		return p;

	}

	@Override
	public String toString() {
		return "BLUE";
	}
}

package controller;

import model.Country;
import model.Player;

import java.util.ArrayList;

public interface IPersistor 
{
	public void write(ArrayList<Player> dataModel);

	public ArrayList<Player> read();

}

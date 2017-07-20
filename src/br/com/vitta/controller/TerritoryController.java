package br.com.vitta.controller;

import java.util.ArrayList;

import br.com.vitta.dao.TerritoryDAO;
import br.com.vitta.model.Territory;
import br.com.vitta.model.Territory.Square;

public class TerritoryController {
	public ArrayList<Territory> listarTodos() {
		return TerritoryDAO.getInstance().listarTodos();
	}
	
	public Territory addTerritory(Territory territory) {
		return TerritoryDAO.getInstance().addTerritory(territory);
	}
	
	public boolean removeTerritory(int id) {
		return TerritoryDAO.getInstance().removeTerritory(id);
	}
	
	public Territory getTerritory(int id, boolean withpainted) {
		return TerritoryDAO.getInstance().getTerritory(id, withpainted);
	}
	
	public boolean isTerritoryOverlay(Territory territory) {
		return TerritoryDAO.getInstance().isTerritoryOverlay(territory);
	}
	
	public Square getSquare(int x, int y) {
		return TerritoryDAO.getInstance().getSquare(x, y);
	}
	
	public Square paintSquare(int x, int y) {
		return TerritoryDAO.getInstance().paintSquare(x, y);
	}
}

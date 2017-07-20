package br.com.vitta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.vitta.factory.ConnectionFactory;
import br.com.vitta.model.Territory;
import br.com.vitta.model.Territory.Position;
import br.com.vitta.model.Territory.Square;

public class TerritoryDAO extends ConnectionFactory {

	private static TerritoryDAO instance;

	public static TerritoryDAO getInstance() {
		if (instance == null) {
			instance = new TerritoryDAO();
		}

		return instance;
	}

	public ArrayList<Territory> listarTodos() {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Territory> territories = null;

		conexao = criarConexao();
		territories = new ArrayList<Territory>();

		try {
			pstmt = conexao
					.prepareStatement("select * from territory order by id");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Territory territory = new Territory();
				territory.setId(rs.getInt("id"));
				territory.setName(rs.getString("name"));
				territory.setArea(rs.getInt("area"));
				territory.setPainted_area(rs.getInt("paintedarea"));
				territory.setEnd(new Position(rs.getInt("endx"), rs.getInt("endy")));
				territory.setStart(new Position(rs.getInt("startx"), rs.getInt("starty")));

				territories.add(territory);
			}
		} catch (Exception e) {
			System.out.println("Erro ao listar todos os territórios: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}

		return territories;
	}

	public Territory getTerritory(int id, boolean withpainted) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conexao = criarConexao();
		Territory territory = null;

		try {
			pstmt = conexao.prepareStatement("select * from territory where id = " + id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				territory = new Territory();
				territory.setId(rs.getInt("id"));
				territory.setName(rs.getString("name"));
				territory.setArea(rs.getInt("area"));
				territory.setPainted_area(rs.getInt("paintedarea"));
				territory.setEnd(new Position(rs.getInt("endx"), rs.getInt("endy")));
				territory.setStart(new Position(rs.getInt("startx"), rs.getInt("starty")));
				
				if (withpainted) {
					pstmt = conexao.prepareStatement("select x, y from square where painted = true and idterritory = " + id);
					
					ResultSet rsPaintedSquare = pstmt.executeQuery();
					
					ArrayList<Position> arrPaintedSquare = new ArrayList<Territory.Position>();
					
					while(rsPaintedSquare.next()) {
						Position pos = new Position(rsPaintedSquare.getInt("x"), rsPaintedSquare.getInt("y"));
						arrPaintedSquare.add(pos);
					}
					
					if (arrPaintedSquare.size() > 0) {
						territory.setPainted_squares(arrPaintedSquare);
					}
					
					rsPaintedSquare.close();
				}
			}

		} catch (Exception e) {
			System.out.println("Erro ao buscar território: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		
		return territory;
	}
	
	public boolean removeTerritory(int id) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		conexao = criarConexao();

		boolean erro = false;
		
		try {
			pstmt = conexao.prepareStatement("delete from territory where id = " + id);
			
			if (pstmt.executeUpdate() == 0) {
				erro = true;
			}

		} catch (Exception e) {
			System.out.println("Erro ao remover território: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, null);
		}
		
		return erro;
	}
	
	public Territory addTerritory(Territory territory) {
		Connection conexao = null;
		PreparedStatement pstmt = null;
		conexao = criarConexao();
		ResultSet rsKeys = null;

		try {
			pstmt = conexao.prepareStatement("insert into territory(name, startx, starty, endx, endy) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, territory.getName());
			pstmt.setInt(2, territory.getStart().getX());
			pstmt.setInt(3, territory.getStart().getY());
			pstmt.setInt(4, territory.getEnd().getX());
			pstmt.setInt(5, territory.getEnd().getY());

			if (pstmt.executeUpdate() == 0) {
				territory.setId(-1);
			} else {
				rsKeys = pstmt.getGeneratedKeys();

				if (rsKeys.next()) {
					territory.setId(rsKeys.getInt(1));
					territory.setArea(2500);
					territory.setPainted_area(0);
				}
			}

		} catch (Exception e) {
			System.out.println("Erro ao gravar território: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rsKeys);
		}

		return territory;
	}
	
	public boolean isTerritoryOverlay(Territory territory) {
		boolean isOverlay = false;
		
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conexao = criarConexao();

		try {
			StringBuffer queryBuf = new StringBuffer();
			queryBuf.append(" SELECT * FROM territory "); 
			queryBuf.append(" WHERE (? BETWEEN startx AND endx) "); 
			queryBuf.append("    OR (? BETWEEN startx AND endx) "); 
			queryBuf.append("    OR (? BETWEEN starty AND endy) "); 
			queryBuf.append("    OR (? BETWEEN starty AND endy) ");
			
			pstmt = conexao.prepareStatement(queryBuf.toString());
			pstmt.setInt(1, territory.getStart().getX());
			pstmt.setInt(2, territory.getEnd().getX());
			pstmt.setInt(3, territory.getStart().getY());
			pstmt.setInt(4, territory.getEnd().getY());
			
			rs = pstmt.executeQuery();
			
			isOverlay = rs.next();
			
		} catch (Exception e) {
			System.out.println("Erro ao verificar sobreposição: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		
		return isOverlay;
	}
	
	public Square getSquare(int x, int y) {
		Square square = null;
		
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conexao = criarConexao();

		try {
			StringBuffer queryBuf = new StringBuffer();
			queryBuf.append(" SELECT 1 FROM territory "); 
			queryBuf.append("  WHERE (? BETWEEN startx AND endx) "); 
			queryBuf.append("    AND (? BETWEEN starty AND endy) "); 
			
			
			pstmt = conexao.prepareStatement(queryBuf.toString());
			pstmt.setInt(1, x);
			pstmt.setInt(2, y);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				square = new Square(x, y);
				square.setPainted(false);
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao buscar quadrado: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		
		return square;
	}
	
	public Square paintSquare(int x, int y) {
		Square square = null;
		
		Connection conexao = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conexao = criarConexao();

		try {
			StringBuffer queFindTerritory = new StringBuffer();
			queFindTerritory.append(" SELECT id, paintedarea FROM territory "); 
			queFindTerritory.append("  WHERE (? BETWEEN startx AND endx) "); 
			queFindTerritory.append("    AND (? BETWEEN starty AND endy) "); 
			
			
			pstmt = conexao.prepareStatement(queFindTerritory.toString());
			pstmt.setInt(1, x);
			pstmt.setInt(2, y);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pstmt = conexao.prepareStatement("INSERT INTO square(idterritory, x, y, painted) values(?,?,?,?)");
				pstmt.setInt(1, rs.getInt("id"));
				pstmt.setInt(2, x);
				pstmt.setInt(3, y);
				pstmt.setBoolean(4, true);
				pstmt.executeUpdate();
				
				pstmt = conexao.prepareStatement("UPDATE territory SET paintedarea = (paintedarea + 1) WHERE id = ? ");
				pstmt.setInt(1, rs.getInt("id"));
				pstmt.executeUpdate();
				
				square = new Square(x, y);
				square.setPainted(true);
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao adicionar quadrado: " + e);
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, pstmt, rs);
		}
		
		return square;
	}
}

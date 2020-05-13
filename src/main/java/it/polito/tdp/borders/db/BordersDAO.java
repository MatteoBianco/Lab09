package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("ccode"))) {
					Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					idMap.put(rs.getInt("ccode"), c);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public Set<Country> getCountryWithBorder(int year, Map<Integer, Country> idMap) {
		
		String sql = "SELECT state1no FROM contiguity WHERE year <= ?";
		Set<Country> result = new HashSet<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = idMap.get(rs.getInt("state1no"));
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}	
	}
	
	public List<Border> getCountryPairs(int year, Map<Integer, Country> idMap) {

		String sql = "SELECT state1no, state2no, year FROM contiguity WHERE conttype = 1 AND year <= ? ORDER BY year";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border b = new Border(idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no")), rs.getInt("year"));
				result.add(b);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}	
	}
}

package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		Model model = new Model();

		System.out.println("Lista di tutte le nazioni:");
		dao.loadAllCountries(model.getCountries());
		System.out.println(model.getCountries());
		System.out.println(model.getCountries().values().size());

	}
}

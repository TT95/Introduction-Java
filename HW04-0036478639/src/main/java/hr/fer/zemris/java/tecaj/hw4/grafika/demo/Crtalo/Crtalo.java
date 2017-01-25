package hr.fer.zemris.java.tecaj.hw4.grafika.demo.Crtalo;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.*;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa namjenjena za crtanje klasa likova tj. demonstraturu njihovog crtanja na rastoru.
 * Unos likova koji se iscrtavaju i njihovi parametri su proslijedeni kroz tekstualnu datoteku.
 * @author Teo Toplak
 */
public class Crtalo {

	/**
	 * Metoda koja se poziva prilikom izvrsavanja programa
	 * @param args redom: path do text dokumenta za unos, sirina, visina rastera
	 */
	public static void main(String[] args) {
		
		SimpleHashtable stvaratelji = podesi(Linija.class, Pravokutnik.class,Kvadrat.class,
					Elipsa.class,Kruznica.class);
		String[] definicije=new String[0];
		try{
			definicije = Files.readAllLines (
					Paths.get( args[0]), StandardCharsets.UTF_8).toArray(new String[0]);
		}catch(Exception e){
			System.out.println("Failed to set path to document");
			System.exit(-1);
		}
		
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		int index=0;
		for(String d : definicije) {
			String string[] = d.split(" ", 2);
			String lik = string[0];
			String parametri = string[1];
			StvarateljLika stvaratelj = (StvarateljLika)stvaratelji.get(lik);
			try{
				likovi[index++] = stvaratelj.stvoriIzStringa(parametri);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("Krivi unos argumenata! Greska u redku: " + d );
				System.err.println("Slika nece biti iscrtana!");
				System.exit(1); 
			}
			
		}
		Slika slika = new Slika(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		for(GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		PrikaznikSlike.prikaziSliku(slika);
	}
	
	/**
	 * Klasa namjenjena za popunjavanje hash tablice 
	 * @param razredi varijabilan broj argumenata (razreda likova)
	 * @return niz stvaratelja likova
	 */
	private static SimpleHashtable podesi(Class<?> ... razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for(Class<?> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika)field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch(Exception ex) {
				throw new RuntimeException(
						"Nije moguće doći do stvaratelja za razred "+
								razred.getName()+".", ex);
			}
		}
		return stvaratelji;
	}

}

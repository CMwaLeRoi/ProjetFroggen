package environment;

import java.util.ArrayList;
import java.util.Random;


import util.Case; 
import gameCommons.Game;


public class Lane  {
	
	//attributs
	protected Game game;
	protected int speed;
	protected int ord;
	protected ArrayList<Car> cars = new ArrayList<>();
	protected boolean leftToRight;
	protected double density;
	protected int tic;
	
	//constructeur
	public Lane(Game g, int ord, double density) {
		this.game = new Game(g.getGraphic(), g.width,g.height,g.minSpeedInTimerLoops,g.defaultDensity);
		this.cars = new ArrayList<Car>();
		this.ord = ord;
		tic = 0;
		this.leftToRight = game.randomGen.nextBoolean();
		
		
		//On a d�cid� de remplir la densit� de cette mani�re 
		//Si on regarde mayAddCar, on voit que la condition d'ajout d'une voiture est " < density "
		//Or on ne veut pas avoir de voiture dans la premiere et la derniere ligne, nous avons donc intialiser la densit� de ces lignes � -1
		if(ord == 0 || ord == g.height - 1) {
			this.density = -1;
		}
		else this.density = density;
		
		this.speed = g.randomGen.nextInt(g.minSpeedInTimerLoops) ;
		
		//On remplie this.cars de voitures
		int i =0;   
		while(i < game.width) {
			i = i + this.mayAddCarInt();
			this.moveCars();   
	        }
	
		}
	

		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e
		 	
		
		


	// TODO : ajout de methodes

	public void removeCars() {
		 final ArrayList<Car> toRemove = new ArrayList<Car>();
		 for(Car cr : this.cars) {
			 if (!cr.visible()) {
				 toRemove.add(cr);
			 }
		 }
		 for (Car c : toRemove) {
			 toRemove.remove(c);
		 }
	}
	

	
	
	public void moveCars() {
		for (Car c : this.cars) {
			c.move();
		}
		this.removeCars();
	}
	
	//La methode updateLane est appell� � chaque tic 
	//Si 
	public void updateLane() {
		tic = tic + 4;
		if (tic > speed) {
			this.moveCars();
			tic = 0;
		}
		this.mayAddCar();
		
		
	}
	
	
	
	
	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	
	
	protected boolean isSafe(Case c) {
		for (Car cr : this.cars) {
			if (cr.exists(c)) {
				return false;
			}
		}
		return true;
	}
	

	
	

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}
	
	/**On a modifi� cette la methode mayAddCar pour qu'elle renvoie :
	 * la taille de la voiture cr�e s'il y a cr�ation de voiture
	 * un nombre al�atoire entre 1 et 3 si aucune voiture n'est cr�e
	 */
	protected int mayAddCarInt() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
				return cars.get(cars.size()-1).length;
			}
		}
		//Attribuer une taille aleatoirement entre 1 et 3 
		int rand = 0;
		Random random = new Random();
		rand= random.nextInt(3) + 1;
		return rand;
	}

		

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

}

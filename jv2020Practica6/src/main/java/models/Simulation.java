package models;
import java.time.LocalDate;
import java.util.Arrays;

import utils.EasyDate;

public class Simulation {

	public enum StateSimulation {PREPARED, RUNNING, FINISHED};
	public enum GridType {EDGES, CYCLIC, UNLIMITED};

	private User user;
	private EasyDate date;
	private byte[][] grid;
	private GridType gridType;
	private StateSimulation state;

	//Reglas simulación
	private int[] constantSurvive;
	private int[] constantReborn;

	private static final int DEFAULT_GRID_SIZE = 18;
	private static final int SIMULATION_CYCLES = 20;

	public Simulation(User user, EasyDate date, byte[][] grid) {
		this.setUser(user);
		this.setDate(date);
		this.setGrid(grid);
		this.applyStandardLaws();
		this.gridType = GridType.EDGES;
		this.state = StateSimulation.PREPARED;
	}

	private void applyStandardLaws() {
		this.constantSurvive = new int[] {2, 3};
		this.constantReborn = new int[] {3};
	}

	public Simulation() {
		this(new User(), 
				EasyDate.now(),
				new byte[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE]
				);
	}

	public Simulation(Simulation simulation) {
		assert simulation != null;
		
		this.user = simulation.user;
		this.date =	EasyDate.today();
		this.grid =	cloneGrid(simulation.grid);
		this.constantSurvive = Arrays.copyOf(simulation.constantSurvive, simulation.constantSurvive.length);
		this.constantReborn = Arrays.copyOf(simulation.constantReborn, simulation.constantReborn.length);
		this.gridType = simulation.gridType;
		this.state = simulation.state;
	}

	private byte[][] cloneGrid(byte[][] grid) {
		byte[][] clon = new byte[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			clon[i] = Arrays.copyOf(grid[i], grid[i].length);
		}
		return clon;
	}

	public String getId() { 
		return this.user.getId() + ":" + this.date.toStringTimeStamp();
	}
	
	public User getUser() {
		return this.user;
	}

	public EasyDate getDate() {
		return this.date;
	}

	public byte[][] getGrid() {
		return this.grid;
	}

	public GridType getGridType() {
		return this.gridType;
	}

	public StateSimulation getState() {
		return this.state;
	}

	public void setUser(User user) {
		assert user != null;
		this.user = user;
	}

	public void setDate(EasyDate date) {
		assert date != null;	
		if (isValidDate(date)) {
			this.date = date;
		}
	}

	private boolean isValidDate(EasyDate date) {	
		return !date.isAfter(EasyDate.now());
	}

	public void setGrid(byte[][] grid) {
		assert grid != null;
		this.grid = grid;
	}

	public void setGridType(GridType gridType) {
		assert gridType != null;
		this.gridType = gridType;
	}

	@Override
	public String toString() {
		return String.format(			
				"%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n",
						"user:", this.user.getName(), 
						"date:", this.date, 
						"gridType:", this.gridType, 
						"state:", this.state
				);
	}

	@Override
	public Simulation clone() {
		return new Simulation(this);
	}

	public void runDemo() {
		this.loadDemoGrid();
		int generation = 0; 
		do {
			System.out.println("\nGeneración: " + generation);
			this.showGrid();
			this.updateGrid();
			generation++;
		}
		while (generation < Simulation.SIMULATION_CYCLES);
	}

	/**
	 * Carga datos demo en la matriz que representa el mundo. 
	 */
	private void loadDemoGrid() {
		// En este array los 0 indican celdas con células muertas y los 1 vivas.
		grid = new byte[][] { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Still Life
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  //
		};
	}

	/**
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	public void showGrid() {

		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {
			for (int j = 0; j < DEFAULT_GRID_SIZE; j++) {		
				System.out.print((grid[i][j] == 1) ? "|o" : "| ");
			}
			System.out.println("|");
		}
	}

	/**
	 * Actualiza el estado del Juego de la Vida.
	 * Actualiza según la configuración establecida para la forma del espacio.
	 */
	private void updateGrid() {
		if (gridType == GridType.EDGES) {
			this.updateGridEdges();
		}
		if (gridType == GridType.CYCLIC) {
			this.updateGridCyclic();
		}
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son adyacentes con las del lado opuesto.
	 * El mundo representado sería esférico cerrado sin límites para células de dos dimensiones.
	 */
	private void updateGridCyclic()  {     					
		// TO-DO pendiente de implementar.
	}

	/**
	 * Actualiza el estado de cada celda almacenada del Juego de la Vida.
	 * Las celdas de los bordes son los límites absolutos.
	 * El mundo representado sería plano, cerrado y con límites para células de dos dimensiones.
	 */
	private void updateGridEdges()  {     					
		byte[][] newGrid = new byte[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];

		for (int i = 0; i < DEFAULT_GRID_SIZE; i++) {		
			for (int j = 0; j < DEFAULT_GRID_SIZE; j++) {
				int neighborsCount = 0;							
				neighborsCount += this.obtainNorthwestCell(i, j);		
				neighborsCount += this.obtainNorthCell(i, j);			// 		NO | N | NE
				neighborsCount += this.obtainNortheastCell(i, j);		//    	-----------
				neighborsCount += this.obtainEastCell(i, j);			// 		 O | * | E
				neighborsCount += this.obtainSoutheastCell(i, j);		// 	  	----------- 
				neighborsCount += this.obtainSouthCell(i, j); 			// 		SO | S | SE
				neighborsCount += this.obtainSouthwestCell(i, j); 	  
				neighborsCount += this.obtaintWestCell(i, j);		          			                                     	

				updateCell(newGrid, i, j, neighborsCount);
			}
		}
		this.grid = newGrid;
	}

	/**
	 * Aplica las leyes del mundo a la celda indicada dada la cantidad de células adyacentes vivas.
	 * @param newGrid
	 * @param row
	 * @param column
	 * @param neighborsCount
	 */
	private void updateCell(byte[][] newGrid, int row, int column, int neighborsCount) {	

		for (int value : this.constantReborn) {
			if (neighborsCount == value) {									// Pasa a estar viva.
				newGrid[row][column] = 1;
				return;
			}
		}

		for (int value : constantSurvive) {
			if (neighborsCount == value && grid[row][column] == 1) {		// Permanece viva, si lo estaba.
				newGrid[row][column] = 1;
				return;
			}
		}
	}

	private byte obtaintWestCell(int row, int column) {
		if (column-1 >= 0) {
			return this.grid[row][column-1]; 		// Celda O.
		}
		return 0;
	}

	private byte obtainSouthwestCell(int row, int column) {
		if (row+1 < Simulation.DEFAULT_GRID_SIZE && column-1 >= 0) {
			return this.grid[row+1][column-1]; 		// Celda SO.
		}
		return 0;
	}

	private byte obtainSouthCell(int row, int column) {
		if (row+1 < Simulation.DEFAULT_GRID_SIZE) {
			return this.grid[row+1][column]; 		// Celda S.
		}
		return 0;
	}

	private byte obtainSoutheastCell(int row, int column) {
		if (row+1 < Simulation.DEFAULT_GRID_SIZE && column+1 < Simulation.DEFAULT_GRID_SIZE) {
			return this.grid[row+1][column+1]; 		// Celda SE.
		}
		return 0;
	}

	private byte obtainEastCell(int row, int column) {
		if (column+1 < Simulation.DEFAULT_GRID_SIZE) {
			return this.grid[row][column+1]; 		// Celda E.
		}
		return 0;
	}

	private byte obtainNortheastCell(int row, int column) {
		if (row-1 >= 0 && column+1 < Simulation.DEFAULT_GRID_SIZE) {
			return this.grid[row-1][column+1]; 		// Celda NE.
		}
		return 0;
	}

	private byte obtainNorthCell(int row, int column) {
		if (row-1 >= 0) {
			return this.grid[row-1][column]; 		// Celda N.
		}
		return 0;
	}

	private byte obtainNorthwestCell(int row, int column) {
		if (row-1 >= 0 && column-1 >= 0) {
			return this.grid[row-1][column-1]; 		// Celda NO.
		}
		return 0;
	}

} 

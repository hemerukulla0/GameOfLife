import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double YERSINIA_ALIVE_PROB = 0.45;
    private static final double CARSONELLA_ALIVE_PROB = 0.60;
    private static final double DETERMINISTICA_ALIVE_PROB = 0.70;
    
    private static final double INFECTION_PROB = 0.15;
    private List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        Random rand = Randomizer.getRandom();
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            if (cell.isInfected()){
                cell.onInfection();
            }else{
               cell.act(); 
            }
            
        }

        for (Cell cell : cells) {

            cell.updateState();

        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);

                double doubleVal = rand.nextDouble();
                
                if (doubleVal <= MYCOPLASMA_ALIVE_PROB){
                    Mycoplasma myco = new Mycoplasma(field, location, Color.BLUE);
                    cells.add(myco);
                    
                    infectCell(myco);
                } else if (doubleVal > MYCOPLASMA_ALIVE_PROB && doubleVal <= YERSINIA_ALIVE_PROB){

                    Random randInt = Randomizer.getRandom();
                    Yersinia yers = new Yersinia(field, location, Color.YELLOW);

                    switch(randInt.nextInt(3)){
                        case 0:
                            yers.setColor(Color.CRIMSON);
                            break;
                        case 1:
                            yers.setColor(Color.YELLOW);
                            break;
                        case 2:
                            yers.setColor(Color.DARKGOLDENROD);
                            break;

                    }

                    cells.add(yers);
                    infectCell(yers);
                    
                } else if (doubleVal > YERSINIA_ALIVE_PROB && doubleVal <= CARSONELLA_ALIVE_PROB ){
                    Carsonella cars = new Carsonella(field, location, Color.GREENYELLOW);
                    cells.add(cars);
                    infectCell(cars);
                } else if (doubleVal > CARSONELLA_ALIVE_PROB && doubleVal <= DETERMINISTICA_ALIVE_PROB){
                    Deterministica det = new Deterministica(field, location, Color.BLUEVIOLET);
                    cells.add(det);
                    //Deterministica cannot get infected due to its non-deterministic properties.
                }
                else{
                    int num = rand.nextInt(4);
                    if(num == 0){
                        Mycoplasma myco = new Mycoplasma(field, location, Color.BLUE);
                        myco.setDead();
                        cells.add(myco);
                    }else if (num == 1){
                        Yersinia yers = new Yersinia(field, location, Color.YELLOW);
                        yers.setDead();
                        cells.add(yers); 
                    }else if (num == 2){
                        Carsonella cars = new Carsonella(field, location, Color.GREENYELLOW);
                        cars.setDead();
                        cells.add(cars);

                    }else{
                        Deterministica det = new Deterministica(field, location, Color.BLUEVIOLET);
                        det.setDead();
                        cells.add(det);
                    }
                }

        
            }
        }
    }


    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public Field getField() {
        return field;
    }
    
    public void infectCell( Cell cell){
        
        Random rand = new Random();
        double infectVal = rand.nextDouble();
        if (infectVal <= INFECTION_PROB){
            cell.infect();
        }
        
    }

    public int getGeneration() {
        return generation;
    }
}

import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Hemchandra Erukulla
 * @version 2022.01.06
 */

public class Deterministica extends Cell {

    /**
     * Create a new Deterministica.
     * 
     * Deterministica cells act in a non-deterministic way, that is, given a set of rules, the cell will execute a rule, r, with probability, p.
     *  
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deterministica(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * 
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        List<Cell> deadNeighbours = getField().getDeadNeighbours(getLocation());
        setNextState(false);

        Random rand = Randomizer.getRandom();
        double doubleVal = rand.nextDouble();

        if (doubleVal >= 0 && doubleVal <= 0.25){ //25% chance
            //Behaviour 1: If less than three alive neighbours, replaces all dead neighbours with new deterministica cells and current cell dies

            if (neighbours.size() < 3){
                for (Cell cell : deadNeighbours){
                    Deterministica newCell = new Deterministica(cell.getField(), cell.getLocation(), Color.BLUEVIOLET);
                    newCell.setLocation(cell.getLocation());
                    cell = newCell;
                }
            }else{
                setNextState(true);
            }

        }else if (doubleVal > 0.26 && doubleVal <= 0.6){ //35% chance
            //Behaviour 2: Count the frequency of lifeform types of surrounding cells, most common lifeform will "take over" the deterministica cell
            int mycoCount = 0;
            int carCount = 0;
            int yersCount = 0;
            int detCount = 0;
            for (Cell cell : neighbours){
                if (cell instanceof Mycoplasma){
                    mycoCount++;
                }else if (cell instanceof Carsonella){
                    carCount++;
                } else if (cell instanceof Yersinia){
                    yersCount++;
                } else if (cell instanceof Deterministica){
                    detCount++;
                }
            }

            ArrayList<Integer> cellCount = new ArrayList<Integer>();
            cellCount.add(mycoCount);
            cellCount.add(yersCount);
            cellCount.add(detCount);
            cellCount.add(carCount);

            Integer maxValue = Collections.max(cellCount);

            if (maxValue == mycoCount){
                Mycoplasma myco = new Mycoplasma(getField(), getLocation(), Color.BLUE);
                myco.setLocation(getLocation());
            } else if (maxValue == yersCount){
                Yersinia yers = new Yersinia(getField(), getLocation(), Color.YELLOW);
                yers.setLocation(getLocation());
            } else if (maxValue == detCount){
                //If most common neighbour is deterministica then current cell lives as is
                setNextState(true);
            } else if (maxValue == carCount){
                Carsonella cars = new Carsonella (getField(), getLocation(), Color.GREENYELLOW);
                cars.setLocation(getLocation());
            }

            
        }else if (doubleVal > 0.6 && doubleVal <= 0.65){ //5% chance
            //Behaviour 3: Cell does nothing
            setNextState(true);
        }else{ //35% 
            //Behaviour 4: Current cell dies randomly
            setNextState(false);
        }

    }
    
    public void onInfection(){
        act();
    }

    public int getAge(){
        return 0;
    }
}

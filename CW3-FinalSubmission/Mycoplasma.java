import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the Mycoplasma cell
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not (and whether it will pass onto the next generation or not)
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        //if cell is alive + has 2/3 neighbours, it will live on to next generation
        if (isAlive()) {
            boolean hasInfectedCarsonellaNeighbour = false;
            for (Cell cell : neighbours){
                if (cell.isAlive() && cell.getColor() == Color.DARKGREEN){
                    hasInfectedCarsonellaNeighbour = true;
                    break;
                }
            }
            
            //when a Mycoplasm cell has an infected Carsonella Neighbour, the rule set changes with the addition where if it has exactly FOUR neighbours (example of mycoplasma gaining as the parasite)
            //it will live onto the next generation
            if (hasInfectedCarsonellaNeighbour) {
                switch(neighbours.size()) {
                    case 2:
                        setNextState(true);
                        break;
                    case 3:
                        setNextState(true);
                        break;
                    case 4:
                        setNextState(true);
                        break;
                    default:
                        setNextState(false);
                }
            }
            else {
                switch(neighbours.size()) {
                    case 2:
                        setNextState(true);
                        break;
                    case 3:
                        setNextState(true);
                        break;
                    default:
                        setNextState(false);
                }
            }
            
            
        }
        // if cell is dead + has exactly 3 neighbours, that cell will become alive
        else {
            switch(neighbours.size()) {
                case 3:
                    setNextState(true);
                    break;
                default:
                    setNextState(false);
            }
        }
    }
    
    protected void onInfection() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        //if cell is alive + has 4/5 neighbours, it will live on to next generation
        if (isAlive()) {
            switch(neighbours.size()) {
                case 4:
                    setNextState(true);
                    break;
                case 5:
                    setNextState(true);
                    break;
                default:
                    setNextState(false);
            }
            
            spreadDisease();
        }
        // if cell is dead + has exactly 2 neighbours, that cell will become alive
        else {
            switch(neighbours.size()) {
                case 2:
                    setNextState(true);
                    break;
                default:
                    setNextState(false);
            }
        }
        
    }

    public int getAge(){
        return 0;
    }
}

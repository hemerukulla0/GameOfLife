import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Write a description of class Yersinia here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Yersinia extends Cell
{

    /**
     * Create a new Yersinia.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the Mycoplasma cell
     * 
     */
    public Yersinia(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Yersinia decides if it's alive or not (and whether it will pass onto the next generation or not)
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        //If 2 or more neighbours have the same colour, then the yersinia dies, else it lives and its colour changes randomly
        if (isAlive()) {

            int counter = 0;
            for (Cell cell : neighbours){
                if (cell.getColor() == getColor()){
                    counter++;
                }
            }

            if (counter >= 2){
                setNextState(false);
            }else{
                setNextState(true);
                Random randInt = new Random();
                switch(randInt.nextInt(3)){
                    case 0:
                        setColor(Color.CRIMSON);
                        break;
                    case 1:
                        setColor(Color.YELLOW);
                        break;
                    case 2:
                        setColor(Color.DARKGOLDENROD);
                        break;

                }
            }
            
        }
        // if cell is dead + has exactly 2 neighbours, that cell will become alive with a random colour
        else {
            Random randInt = new Random();
            switch(neighbours.size()) {
                case 2:
                    setNextState(true);
                    switch(randInt.nextInt(3)){
                        case 0:
                            setColor(Color.CRIMSON);
                            break;
                        case 1:
                            setColor(Color.YELLOW);
                            break;
                        case 2:
                            setColor(Color.DARKGOLDENROD);
                            break;

                    }
                    break;
                default:
                    setNextState(false);
            }
        }
    }
    
    protected void onInfection() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        if (isAlive()) {

            int counter = 0;
            for (Cell cell : neighbours){
                if (cell.getColor() == this.getColor()){
                    counter++;
                }
            }

            //If there's no neighbours having the same colour as the current cell, then the yersinia dies
            if (counter == 0){
                setNextState(false);
            }else{
                setNextState(true);
            }
            
            spreadDisease();
        }
        // if cell is dead + has exactly 4 neighbours, that cell will become alive
        else {
            Random randInt = new Random();
            switch(neighbours.size()) {
                case 4:
                    setNextState(true);
                    switch(randInt.nextInt(3)){
                        case 0:
                            setColor(Color.CRIMSON);
                            break;
                        case 1:
                            setColor(Color.YELLOW);
                            break;
                        case 2:
                            setColor(Color.DARKGOLDENROD);
                            break;

                    }
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

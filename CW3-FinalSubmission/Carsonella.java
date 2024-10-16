import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * 
 * Carsonella has a parasitic relationship with Mycoplasm (CHALLENGE TASK), where Mycoplasm is the parasite harms the host (Carsonella)
 * 
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael (used parts of code from Mycoplasm class)
 * @version 2022.01.06
 * 
 * @Hemchandra Erukulla, Ching Lok Tsang (modified the code)
 * @21/02/2024
 */
public class Carsonella extends Cell
{  
    private int age;
    private static final float initialDeathProb  = 0.1f;
    private float currentDeathProb;
    //when parasite (Mycoplasma) infects the host species (Carsonella), the parasite transmits pathogens to host
    private boolean hasPathogens;
    private static final double getPathogensProb = 0.2;
    //chances for the host (Carsonella) to die is low because parasites need their host for survival 
    //parasites don't try to kill the host, but the diseases parasites carry may lead to their host's death
    private static final double deathFromPathogensProb = 0.1;

    /**
     * Create a new Carsonella.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the Mycoplasma cell
     */
    public Carsonella(Field field, Location location, Color col) {
        super(field, location, col);
        age = 0;
        currentDeathProb = initialDeathProb;
        //a Carsonella cell would have no pathogens in the very beginning
        hasPathogens = false;
    }

    /**
     * This is how the Carsonella decides if it's alive or not (and whether it will pass onto the next generation or not)
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        //When the cell is first "born" it has a death probability of 10%. As it's age increases, the probability of death increments by 10% per generation
        if (isAlive()) {
            age++;
            Random randomNum = new Random();

            double doubleVal1 = randomNum.nextDouble();
            currentDeathProb = initialDeathProb * age;

            boolean cellDead = false;

            if (doubleVal1 <= currentDeathProb){
                setNextState(false);
                age = 0;
                hasPathogens = false;
                setColor(Color.GREENYELLOW);
                cellDead = true;
            }else{
                setNextState(true);
            }

            //if a Carsonella cell harbours pathogens, it has 10% chance to die from diseases caused by the pathogens
            if (!cellDead && hasPathogens) {
                double doubleVal2 = randomNum.nextDouble();
                if (doubleVal2 <= deathFromPathogensProb) {
                    setNextState(false);
                    //cell dies and "resets"
                    age = 0;
                    hasPathogens = false;
                    setColor(Color.GREENYELLOW);
                }
            }
            //if a Carsonella cell is has a Mycoplasm neighbour, it has 20% chance to get infected by Mycoplasm
            else if (!cellDead && !hasPathogens) {
                boolean hasMycoplasmNeighbour = false;
                for (Cell cell : neighbours){
                    if (cell.isAlive() && cell.getColor() == Color.BLUE){
                        hasMycoplasmNeighbour = true;
                        break;
                    }
                }

                if (hasMycoplasmNeighbour) {
                    double doubleVal3 = randomNum.nextDouble();
                    if (doubleVal3 <= getPathogensProb) { //20% chance for the cell to become infected
                        hasPathogens = true;
                        //cell changes colour when INFECTED
                        setColor(Color.DARKGREEN);
                    }
                }
            }

        }
        // if cell is dead + has neighbours, whose combined age is less than 30, cell comes back to life. Or if all the cell's neighbours are dead then it comes back to life
        else {

            int combAge = 0;
            for (Cell cell : neighbours){
                if (cell.isAlive() && cell.getColor() == Color.BLUE){
                    combAge += cell.getAge();
                }
            }

            if (combAge < 30 || neighbours.size() == 0){
                setNextState(true);
            }else{
                setNextState(false);
            }

        }
    }

    public void onInfection() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        //When the cell is first "born" it has a death probability of 10%. As it's age increases, the death probability of death increments by 20% (because of the infection) per generation
        if (isAlive()) {
            age++;
            Random randomNum = new Random();

            double doubleVal = randomNum.nextDouble();
            currentDeathProb = initialDeathProb * age * 2; // *2 because of the infection

            if (doubleVal <= currentDeathProb){
                setNextState(false);
                age = 0;
            }else{
                setNextState(true);
            }
            spreadDisease();

        }
        // if cell is dead + has neighbours, whose combined age is less than 20 (because of infection), cell comes back to life. Or if all the cell's neighbours are dead then it comes back to life
        else {

            int combAge = 0;
            for (Cell cell : neighbours){
                if (cell.isAlive() && cell.getColor() == Color.BLUE){
                    combAge += cell.getAge();
                }
            }

            if (combAge < 20 || neighbours.size() == 0){
                setNextState(true);
            }else{
                setNextState(false);
            }

        }
    }

    public int getAge(){
        return age;
    }
}

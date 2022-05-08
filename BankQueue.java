import java.util.*;
import java.io.*;

class BankQueue {

    //creating variables
    public static int timeCounter = 0;
    public static int numOfTellers = 0;
    public static int availability = 0;
    public static ArrayList<Integer> remainingDurations = new ArrayList<Integer>();
    public static Queue<Integer> arrivalQueue = new LinkedList<Integer>();
    public static Queue<Integer> durationQueue = new LinkedList<Integer>();

    //Filling queues with values from files
    public static void fillQueues() throws FileNotFoundException {
        File file = new File("arrivals.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            arrivalQueue.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();
        file = new File("durations.txt");
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            durationQueue.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();
    }
    
    //setting up variables for use with each number of tellers
    public static void prep(int num) throws IOException {
        timeCounter = 0;
        availability = num;
        numOfTellers = num;
        arrivalQueue.clear();
        durationQueue.clear();
        remainingDurations.clear();
        for (int i = 0; i < num; i++) {
            remainingDurations.add(0);
        }
        fillQueues();
    }

    //method to execute a day with the given num of tellers
    public static void execDay() {
        while (!arrivalQueue.isEmpty()) {
            if (availability > 0) {
                if (timeCounter>=arrivalQueue.peek()) {
                    availability--;
                    arrivalQueue.remove();
                    for (int i = 0; i < numOfTellers; i++) {
                        if (remainingDurations.get(i) == 0) {
                            remainingDurations.set(i, durationQueue.poll());
                            i = numOfTellers;
                        }
                    }
                } else {
                    incTimeCounter();
                }
            } else {
                incTimeCounter();
            }
        }
        System.out.println("Number of Tellers: " + numOfTellers + "\n" + " Total Time: " + timeCounter + "\n");
    }

    //method to increase timeCounter and perform checks on availability
    public static void incTimeCounter() {
        timeCounter++;
        for (int i = 0; i < numOfTellers; i++) {
            remainingDurations.set(i, remainingDurations.get(i) - 1);
            if (remainingDurations.get(i) == 0) {
                availability++;
            }
        }
    }

    //main method
    public static void main(String[] args) throws IOException {
        for (int i = 2; i <= 10; i++) {
            prep(i);
            execDay();
        }
    }
}
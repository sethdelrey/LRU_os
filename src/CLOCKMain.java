import java.io.File;
import java.util.Scanner;

public class CLOCKMain {
    public static void main(String[] args) {
        Scanner consoleIn = new Scanner(System.in);
        System.out.print("Enter the number of free physical page frames: ");
        int pageFrames = consoleIn.nextInt();
        System.out.print("Enter the input file name: ");
        String fileName = consoleIn.next();
        System.out.print("Enter the hit cost: ");
        int hitCost = consoleIn.nextInt();
        System.out.print("Enter teh swap-in cost: ");
        int swapInCost = consoleIn.nextInt();
        System.out.print("Enter teh swap-in cost: ");
        int swapOutCost = consoleIn.nextInt();

        try (Scanner fin = new Scanner(new File(fileName))) {
            int[] pages = new int[pageFrames];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = -1;
            }
            boolean[] statusBits = new boolean[pageFrames];
            int index = 0;
            int faultsRead = 0;
            int faultsWrite = 0;
            int totalRefs = 0;
            int misses = 0;
            String line = "";
            while (fin.hasNextLine()) {
                line = fin.nextLine();
                int value = Integer.parseInt(line.split(" ")[1]);
                if (line.contains("R")) {
                    if (!isInCircle(pages, statusBits, value)) {
                        index = replace(pages, statusBits, value, index);
                        faultsRead++;
                    }

                }
                else {
                    if (!isInCircle(pages, statusBits, value)) {
                        index = replace(pages, statusBits, value, index);
                        faultsWrite++;
                    }
                }
                totalRefs++;
            }
            System.out.println("Total number of page reference: " + totalRefs);
            System.out.println("Total number of page faults on READ: " + faultsRead);
            System.out.println("Total number of page faults on WRITE: " + faultsWrite);
            System.out.println("Total number of time units for accessing pages in memory: " + (totalRefs - faultsRead - faultsWrite) * hitCost);
            int swaps = faultsRead + faultsWrite;
            System.out.println("Total number of time units for swapping in pages: " + (swaps) * swapInCost);
            System.out.println("Total number of time units for swapping out pages: " + (swaps) * swapOutCost);
        }
        catch (Exception e) {
            System.err.print(e);
        }
    }

    public static boolean isInCircle(int[] pages, boolean[] statusBits, int value) {
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] == value) {
                statusBits[i] = true;
                return true;
            }
        }

        return false;
    }

    public static int replace(int[] pages, boolean[] statusBits, int value, int index) {
        while (true) {
            if (!statusBits[index]) {
                pages[index] = value;
                return (index + 1) % pages.length;
            }

            statusBits[index] = false;

            index = (index + 1) % pages.length;
        }
    }
}

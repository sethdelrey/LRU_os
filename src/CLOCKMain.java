import java.util.Scanner;

public class CLOCKMain {
    public static void main(String[] args) {
        String input = "9 0 9 0 9 0 9";
        Scanner cin = new Scanner(input);
        int[] inputArray = new int[(int) Math.round(input.length() / 2.0)];
        int count = 0;
        while (cin.hasNext()) {
            inputArray[count] = Integer.parseInt(cin.next());
            count++;
        }

        System.out.print(count);
    }
}

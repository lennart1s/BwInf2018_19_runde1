import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter file name:");
            Scanner scan = new Scanner(System.in);
            args = new String[1];
            args[0] = scan.nextLine();
        }
        int showSolutions = 10;
        if (args.length == 2) {
            try {
                showSolutions = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) { }
        }
        String file_string = args[0];
        File f = new File(file_string);
        System.out.println(f.getAbsolutePath());
        List<Garden> gardenList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            if(line == null) {
                System.out.println("File is empty.");
                return;
            }
            String[] s = line.replaceAll("\\s+", "").split(",");

            for (String garden : s){
                String[] integers = garden.split("x");
                if (integers.length < 2) {
                    continue;
                } else {
                    try {
                        gardenList.add(new Garden(Integer.parseInt(integers[0]), Integer.parseInt(integers[1])));
                    } catch (NumberFormatException e) {
                        System.out.println("Unsupported format (" + garden + ")");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        } catch (IOException e) {
            System.out.println("Not allowed to read file!");
            return;
        }

        List<GardenInstance> instances;

        if (!gardenList.isEmpty()) {
            instances = new Calculator(gardenList).calculate();
        } else {
            System.out.println("No gardens found in file.");
            return;
        }

        if (instances.isEmpty()) {
            System.out.println("Unexpected error, please try again.");
            return;
        }

        int lowest = 0;

        List<GardenInstance> toDisplay = new ArrayList<>();

        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).getArea() < instances.get(lowest).getArea()) {
                lowest = i;
                toDisplay.clear();
                toDisplay.add(instances.get(i));
            } else
            if (instances.get(i).getArea() == instances.get(lowest).getArea()) {
                toDisplay.add(instances.get(i));
            }

        }

        System.out.println("Lowest area: " + instances.get(lowest).getArea());

        while (toDisplay.size() > showSolutions) {
            toDisplay.remove(toDisplay.size() - 1);
        }

        int i = 1;
        for (GardenInstance instance : toDisplay) {
            new Display("#" + i).display(instance);
            ++i;
        }

    }

}

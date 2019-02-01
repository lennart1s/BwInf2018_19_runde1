import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Group {

    private HashMap<String, List<String>> members;
    private int numRequests = 0;

    public Group(String dataFilePath) {
        this.members = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFilePath));

            for (String memberName : br.readLine().split(" ")) {
                this.members.put(memberName, new LinkedList<>());
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] parts = line.split(" ");
                this.members.get(parts[0]).add(parts[1]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("No file or directory '" + dataFilePath + "'!");
        } catch (IOException e) {
            System.err.println("Error reading file '" + dataFilePath + "'!");
            e.printStackTrace();
        }

    }

    public boolean isXFollowingY(String x, String y) {
        boolean follows = this.members.get(x).contains(y);
        System.out.println("Request: "+ x + " -> " + y + ": " + follows);
        this.numRequests++;
        return follows;
    }

    public List<String> getMembers() {
        return new LinkedList<>(this.members.keySet());
    }

    public int getNumRequests() {
        return this.numRequests;
    }

}

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Superstar {

    public static void main(String[] args) {
        Group group = new Group(args[0]);

        String[] superstars = searchSuperstar(group);

        System.out.println("\nResults: ");
        System.out.println(Arrays.toString(superstars) + "   " + group.getNumRequests() + " requests with " + group.getMembers().size() + " members");
    }

    private static String[] searchSuperstar(Group group) {
        List<String> members = new LinkedList<>(group.getMembers());
        List<String> superstars = new LinkedList<>(members);
        Map<String, LinkedList<String>> checkedOn = new HashMap<>();

        for (String member : members) {
            checkedOn.putIfAbsent(member, new LinkedList<>());
            for (String superstar : new LinkedList<>(superstars)) {
                if (!member.equals(superstar)) {
                    checkedOn.get(member).add(superstar);
                    if (group.isXFollowingY(member, superstar)) {
                        superstars.remove(member);
                        break;
                    } else {
                        superstars.remove(superstar);
                    }
                }
            }
        }

        for (String superstar : new LinkedList<>(superstars)) {
            for(String member : members) {
                if(!superstar.equals(member) && !checkedOn.get(superstar).contains(member)) {
                    if(group.isXFollowingY(superstar, member)) {
                        superstars.remove(superstar);
                        break;
                    }
                }
            }
        }

        return superstars.toArray(new String[]{});
    }

}

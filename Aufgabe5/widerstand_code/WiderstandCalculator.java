public class WiderstandCalculator {

    Solution finalSol = new Solution();
    int[] resistors;
    double goal;

    //Ohm-Zeichen(Omega)
    char omega = 0x03A9;

    public WiderstandCalculator(int[] resistors, double goal){
        this.resistors = resistors;
        this.goal = goal;
        start();
    }

    public void start(){
        if(!exists(goal)){
            finalSol = calc();
            printSolution(finalSol);
        }else{
            System.out.println("The resistor exists");
        }
    }

    public Solution calc(){
        finalSol.dist = lowestDistOfExisting(goal);
        finalSol.nearest = nearestExisting(goal);
        finalSol.values = new int[]{nearestExistingIndex(goal)};

        switch (resistors.length){
            case 1:
                break;

            case 2:
                if(getLowest2Values().dist < finalSol.dist) finalSol = getLowest2Values();
                break;

            case 3:
                if(getLowest2Values().dist < finalSol.dist) finalSol = getLowest2Values();
                if(getLowest3Values().dist < finalSol.dist) finalSol = getLowest3Values();
                break;

            default:
                if(getLowest2Values().dist < finalSol.dist) finalSol = getLowest2Values();
                if(getLowest3Values().dist < finalSol.dist) finalSol = getLowest3Values();
                if(getLowest4Values().dist < finalSol.dist) finalSol = getLowest4Values();
                break;
        }
        return finalSol;
    }

    public void printSolution(Solution s){
        System.out.println("Goal: " + goal);
        System.out.println("Offset: " + s.dist);
        System.out.println("Value: " + s.nearest);
        String temp = "";
        System.out.println("");
        String currentVal = String.valueOf(s.values[s.values.length-1]);
        for(int i = s.conditions.length; i > 0; i--){
            if(s.conditions[i-1] == 1){
                temp = "parallel(" + currentVal + "," + s.values[i-1] + ")";
            }else{
                temp = "reihe(" + currentVal + "," + s.values[i-1] + ")";
            }
            currentVal = temp;
        }
        System.out.println(temp);
        System.out.println("");
        temp = "";
        currentVal = String.valueOf(resistors[s.values[s.values.length-1]]) + omega;
        for(int i = s.conditions.length; i > 0; i--){
            if(s.conditions[i-1] == 1){
                temp = "parallel(" + currentVal + "," + resistors[s.values[i-1]] + omega + ")";
            }else{
                temp = "reihe(" + currentVal + "," + resistors[s.values[i-1]] + omega + ")";
            }
            currentVal = temp;
        }
        System.out.println(temp);
    }

    public Solution getLowest4Values(){
        Solution s = new Solution();

        double dist = 0;
        double nearest = 0;
        int[] conditions = {0,0,0};
        int[] values = {0,0,0,0};

        boolean firstOp = true;

        outer: for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    for(int l = 0; l < resistors.length; l++){
                        for(int m = 0; m < resistors.length; m++){
                            for(int n = 0; n < resistors.length; n++){
                                for(int o = 0; o < resistors.length; o++){
                                    if(l!=o && m!=o && n!=o && l!=m && l!=n && m!=n) {
                                        double value = combination(i, j, k, resistors[l], resistors[m], resistors[n], resistors[o]);
                                        if(firstOp){
                                            firstOp = false;
                                            dist = Math.abs(value-goal);
                                            nearest = value;

                                            conditions = new int[]{i,j,k};
                                            values = new int[]{l,m,n,o};

                                            if(value == goal) break outer;
                                        }else{
                                            if (Math.abs(value - goal) < dist){
                                                dist = Math.abs(value - goal);
                                                nearest = value;

                                                conditions = new int[]{i,j,k};
                                                values = new int[]{l,m,n,o};

                                                if(value == goal) break outer;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        s.dist = dist;
        s.conditions = conditions;
        s.values = values;
        s.nearest = nearest;
        return s;
    }

    public Solution getLowest3Values(){
        Solution s = new Solution();

        double dist = 0;
        double nearest = 0;
        int[] conditions = {0,0,0};
        int[] values = {0,0,0,0};

        boolean firstOp = true;

        outer: for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int l = 0; l < resistors.length; l++){
                    for(int m = 0; m < resistors.length; m++){
                        for(int n = 0; n < resistors.length; n++){
                            if(l!=m && l!=n && m!=n) {
                                double value = combination(i, j, resistors[l], resistors[m], resistors[n]);
                                if(firstOp){
                                    firstOp = false;
                                    dist = Math.abs(value-goal);
                                    nearest = value;

                                    conditions = new int[]{i,j};
                                    values = new int[]{l,m,n};

                                    if(value == goal) break outer;
                                }else{
                                    if (Math.abs(value - goal) < dist){
                                        dist = Math.abs(value - goal);
                                        nearest = value;

                                        conditions = new int[]{i,j};
                                        values = new int[]{l,m,n};

                                        if(value == goal) break outer;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        s.dist = dist;
        s.conditions = conditions;
        s.values = values;
        s.nearest = nearest;
        return s;
    }

    public Solution getLowest2Values(){
        Solution s = new Solution();

        double dist = 0;
        double nearest = 0;
        int[] conditions = {0,0,0};
        int[] values = {0,0,0,0};

        boolean firstOp = true;

        outer: for(int i = 0; i < 2; i++){
            for(int l = 0; l < resistors.length; l++){
                for(int m = 0; m < resistors.length; m++){
                    if(l!=m) {
                        double value = combination(i, resistors[l], resistors[m]);
                        if(firstOp){
                            firstOp = false;
                            dist = Math.abs(value-goal);
                            nearest = value;

                            conditions = new int[]{i};
                            values = new int[]{l,m};

                            if(value == goal) break outer;
                        }else{
                            if (Math.abs(value - goal) < dist){
                                dist = Math.abs(value - goal);
                                nearest = value;

                                conditions = new int[]{i};
                                values = new int[]{l,m};

                                if(value == goal) break outer;
                            }
                        }
                    }
                }
            }
        }
        s.dist = dist;
        s.conditions = conditions;
        s.values = values;
        s.nearest = nearest;
        return s;
    }

    public double parallel(double value1, int value2){
        return (1/(((double)1/value1)+((double)1/value2)));
    }

    public double reihe(double value1, int value2){ return value1 + value2;}

    public double combination(int condition1, int condition2, int condition3, int in1, int in2, int in3, int in4){
        double firstVal;
        double secondVal;
        double thirdVal;
        if(condition3 == 1)
        {
            firstVal = parallel(in4, in3);
        }else{
            firstVal = reihe(in4, in3);
        }
        if(condition2 == 1){
            secondVal = parallel(firstVal, in2);
        }else{
            secondVal = reihe(firstVal, in2);
        }
        if(condition1 == 1){
            thirdVal = parallel(secondVal, in1);
        }else{
            thirdVal = reihe(secondVal, in1);
        }
        return thirdVal;
    }

    public double combination(int condition1, int condition2, int in1, int in2, int in3){
        double firstVal;
        double secondVal;
        if(condition2 == 1){
            firstVal = parallel(in3, in2);
        }else{
            firstVal = reihe(in3, in2);
        }
        if(condition1 == 1){
            secondVal = parallel(firstVal, in1);
        }else{
            secondVal = reihe(firstVal, in1);
        }
        return secondVal;
    }

    public double combination(int condition1, int in1, int in2){
        double firstVal;
        if(condition1 == 1)
        {
            firstVal = parallel(in2, in1);
        }else{
            firstVal = reihe(in1, in2);
        }
        return firstVal;
    }

    //Funktion zur Überprüfung ob der Widerstand bereits ohne Kombination existiert
    public boolean exists(double resistor){
        for(int i = 0; i < resistors.length; i++){
            if(resistors[i] == resistor) return true;
        }
        return false;
    }

    public double lowestDistOfExisting(double goal){
        boolean firstIteration = true;
        double lowestDist = 0;
        for(int i = 0; i < resistors.length; i++){
            double dist = Math.abs(resistors[i] - goal);
            if(firstIteration){
                lowestDist = dist;
                firstIteration = false;
            }else if(dist < lowestDist){
                lowestDist = dist;
            }
        }
        return lowestDist;
    }

    public int nearestExisting(double goal){
        boolean firstIteration = true;
        double lowestDist = 0;
        int nearest = 0;

        for(int i = 0; i < resistors.length; i++){
            double dist = Math.abs(resistors[i] - goal);
            if(firstIteration){
                lowestDist = dist;
                nearest = resistors[i];
                firstIteration = false;
            }
            else if(dist < lowestDist) {
                lowestDist = dist;
                nearest = resistors[i];
            }
        }
        return nearest;
    }

    public int nearestExistingIndex(double goal){
        boolean firstIteration = true;
        double lowestDist = 0;
        int index = 0;

        for(int i = 0; i < resistors.length; i++){
            double dist = Math.abs(resistors[i] - goal);
            if(firstIteration) {
                lowestDist = dist;
                index = i;
                firstIteration = false;
            }
            else if(dist < lowestDist){
                lowestDist = dist; index = i;
            }
        }
        return index;
    }
}

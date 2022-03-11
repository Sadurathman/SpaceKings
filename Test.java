package AjiraRound1;
import java.util.*;

/*
Asha and Amar are playing SpaceKings a video game. It is a two player game where the second player is the helper. Asha needs your help minimizing her gold while playing her favorite game. Both are facing N aliens. Asha and Amar are both at a single location and the aliens are in lined up in front of them. Asha and Amar take turns shooting the aliens, and she goes first. During her turn, Asha may choose any alien to shoot at (this means Asha may choose to skip a turn). During his turn, Amar always shoots the alien closest to him to help Asha minimize her gold. Asha and Amar can not shoot dead alien.

If Asha shoots at an alien, its hit points are reduced by P. If Amar shoots at an alien, its hit points are reduced by Q. If an alien's hit points goes below 1, it is killed. The ith alien starts with Hi hit points. Asha is awarded Gi gold if her shot kills the ith alien, but none if Amar's shot kills it. What is the minimum amount of gold Asha can obtain?


Input

Each case begins with one line containing three space-separated integers representing P, Q and N. N lines then follow, with the ith line containing two space-separated integers representing Hi and Gi. The aliens are given in the order of their distance from Asha and Amar. In other words, Amar will shoot at the ith alien only if all aliens < i are dead.
Output

minimum amount of gold that Asha can get

Input

20 60 3

80 100

80 200

120 300
Output

500
Explanation Asha should give up the first alien. During her first two turns she should soften up the third alien bringing it down to 80 hp, allowing her to easily get the last shot on the second and the third aliens
Input

50 60 2
40 100
40 90


Output
100


Input

50 60 2
40 100
40 200


Output

200


Input

50 100 2
60 100
60 200


Output

200


Input

50 400 2
60 100
190 200

Output

0
 */

class Test {

    public static boolean isGameOver(int[] alienHealth){
        for(int i=0;i<alienHealth.length;i++){
            if(alienHealth[i] >= 1){
                return false;
            }
        }
        return true;
    }

    public static int minGold(int[] alienHealth, int[] goldReward, int ashaHP, int amarHP, int amarGold, int nearAlien, boolean ashaTurn){
        if(isGameOver(alienHealth) || nearAlien >= alienHealth.length) return amarGold;
        int min = 0;
        if(ashaTurn){
            min = minGold(alienHealth, goldReward, ashaHP, amarHP, amarGold, nearAlien, !ashaTurn);
            for(int i=nearAlien;i<alienHealth.length;i++){
                if(alienHealth[i] < 1) continue;
                alienHealth[i] -= ashaHP;

                int gold = minGold(alienHealth, goldReward, ashaHP, amarHP, amarGold, nearAlien, !ashaTurn);


                alienHealth[i] += ashaHP;

                if(gold < min) min = gold;
            }
            return min;
        }else {
            if(alienHealth[nearAlien] < 1)
                return minGold(alienHealth, goldReward, ashaHP, amarHP, amarGold, nearAlien + 1, ashaTurn);
            alienHealth[nearAlien] -= amarHP;
            if (alienHealth[nearAlien] < 1) {
                amarGold += goldReward[nearAlien];
            }
            int gold = minGold(alienHealth, goldReward, ashaHP, amarHP, amarGold, nearAlien, !ashaTurn);

            alienHealth[nearAlien] += amarHP;

            return gold;
        }
    }

    public static void input(){

    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String[] PQN = sc.nextLine().split(" ");

        int ashaHP = Integer.parseInt(PQN[0]);
        int amarHP = Integer.parseInt(PQN[1]);
        int alienCount = Integer.parseInt(PQN[2]);

        int alienHealth[] = new int[alienCount];
        int goldReward[] = new int[alienCount];
        int total = 0;
        for(int i=0;i<alienCount;i++){
            String[] HG = sc.nextLine().split(" ");
            alienHealth[i] = Integer.parseInt(HG[0]);
            goldReward[i] = Integer.parseInt(HG[1]);
            total+=goldReward[i];
        }

        int gold = minGold(alienHealth, goldReward, ashaHP, amarHP, 0, 0, true);
        System.out.println(total - gold);
    }
}
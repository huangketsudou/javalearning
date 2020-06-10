package chapter21;
// arrays/AlphabeticSearch.java
// Searching with a Comparator

import onjava.*;

import java.util.Arrays;

import static onjava.ArrayShow.*;

public class AlphabeticSearch {
    public static void main(String[] args) {
        String[] sa = new Rand.String().array(30);
        Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER);
        show(sa);
        int index = Arrays.binarySearch(sa, sa[10], String.CASE_INSENSITIVE_ORDER);
        System.out.println("Index: " + index + "\n" + sa[index]);
    }
}
/* Output:
[anmkkyh, bhmupju, btpenpc, cjwzmmr, cuxszgv, eloztdv, ewcippc,
ezdeklu, fcjpthl, fqmlgsh, gmeinne, hyoubzl, jbvlgwc, jlxpqds,
ljlbynx, mvducuj, qgekgly, skddcat, taprwxz, uybypgp, vjsszkn,
vniyapk, vqqakbm, vwodhcf, ydpulcq, ygpoalk, yskvett, zehpfmm,
zofmmvm, zrxmclh]
Index: 10 gmeinne
*/

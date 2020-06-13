package appendix.equalshashcode;
// equalshashcode/IndividualTest.java
import chapter11.MapOfList;
import typeinfo.pets.*;
import java.util.*;
public class IndividualTest {
    public static void main(String[] args) {
        Set<Individual> pets = new TreeSet<>();
        for(List<? extends Pet> lp :
                MapOfList.petPeople.values())
            for(Pet p : lp)
                pets.add(p);
        pets.forEach(System.out::println);
    }
}
/* Output:
Cat Elsie May
Cat Pinkola
Cat Shackleton
Cat Stanford
Cymric Molly
Dog Margrett
Mutt Spot
Pug Louie aka Louis Snorkelstein Dupree
Rat Fizzy
Rat Freckly
Rat Fuzzy
*/

package examples.easyrules;

import org.jeasy.rules.api.Facts;

public class HowToAccessFacts
{
    private final Facts myFacts;

    public HowToAccessFacts()
    {
        myFacts = new Facts();
        var factsIMade = new HowToDefineFacts();
        var firstFact = factsIMade.getMyFact();
        var secondFact = factsIMade.getMyOtherFact();
        myFacts.add(firstFact);
        myFacts.add(secondFact);
        // Oh no, wait! Made a mistake! Need 'value to square' to be 8!
        myFacts.put("Performing another example", 8);
    }

    public Facts getMyFacts() { return myFacts; }
}

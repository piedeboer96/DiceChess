package examples.easyrules;

import org.jeasy.rules.api.Fact;

public class HowToDefineFacts
{
    private final Fact<Boolean> myFact;
    private final Fact<Integer> myOtherFact;

    public HowToDefineFacts()
    {
        myFact = new Fact<>("Performing example", true);
        myOtherFact = new Fact<>("Performing another example", 2);
    }

    public Fact<Boolean> getMyFact() { return myFact; }

    public Fact<Integer> getMyOtherFact() {return myOtherFact; }
}

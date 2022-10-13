package examples.easyrules;

import org.jeasy.rules.api.Rules;

public class HowToAccessRules
{
    private final Rules myRules;

    public HowToAccessRules()
    {
        myRules = new Rules();
        var exampleRule = new HowToDefineRules();
        var exampleRule2 = new HowToDefineRules2();
        myRules.register(exampleRule);
        myRules.unregister(exampleRule);
        myRules.register(exampleRule);
        myRules.register(exampleRule2);
        for (var rule : myRules) { System.out.println(rule); }
    }

    public Rules getMyRules() { return myRules; }
}

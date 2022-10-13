package examples.easyrules;

import org.jeasy.rules.core.DefaultRulesEngine;

public class HowToUseRulesEngine
{
    public static void main(String[] args)
    {
        var exampleRuleAccessor = new HowToAccessRules();
        var exampleRules = exampleRuleAccessor.getMyRules();

        var exampleFactAccessor = new HowToAccessFacts();
        var exampleFacts = exampleFactAccessor.getMyFacts();

        var rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(exampleRules, exampleFacts);

        // If you have read everything, then you should understand why you are seeing console messages!
    }
}

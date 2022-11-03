package phase1version.examples.easyrules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "Name of the rule comes here.", description = "Answer the question 'What does this rule do?' | I show an example rule.")
public class HowToDefineRules
{
    @Condition public boolean isDefined(@Fact("Performing example") boolean ruleIsExample) { return ruleIsExample; }

    @Action public void onPerformingExample() { System.out.println("Hello, you have successfully accessed the action of the rule definition example."); }

    @Override public String toString() { return "Rule: This is an example on how to define a rule!"; }
}

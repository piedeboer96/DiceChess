package phase1version.examples.easyrules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "Example of a how to define a more complex rule", description = "If the proper value to square has been found, then displays the squared value")
public class HowToDefineRules2
{
    private final int targetValue = 8;
    @Condition public boolean foundTargetValue(@Fact("Performing another example") int valueToSquare) { return valueToSquare == targetValue; }

    @Action public void onRuleActive() { System.out.println("You have successfully accessed this square value rule! : " + (targetValue * targetValue)); }

    @Override public String toString() { return "Rule: This is an another example on how to define a rule!"; }
}

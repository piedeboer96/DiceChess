package phase1version.examples.easyrules;

public class HowToTestRules
{
    public static void main(String[] args)
    {
        // Remark: You are supposed to write these in our test folder!
        // If done well, no exceptions should be thrown.
        var exampleRule = new HowToDefineRules();
        assert exampleRule.isDefined(true);
        assert !exampleRule.isDefined(false);

        exampleRule.onPerformingExample(); // Just making it prints the message out. | This requires a manual check due to print statement.

        var exampleRule2 = new HowToDefineRules2();
        assert exampleRule2.foundTargetValue(8);
        assert !exampleRule2.foundTargetValue(4);

        exampleRule2.onRuleActive(); // Same story as the last print statement.
    }
}

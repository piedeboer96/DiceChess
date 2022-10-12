package ai.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import ai.test.t1.BishopMoveAndNotSuicideTest;
import ai.test.t2.PawnMoveAndWinTest;
import ai.test.t3.TestKingEatPawnTest;
import ai.test.t4.AnastasiaMateTest;
import ai.test.t5.EscapeFromEatRuleTest;

@Suite
@SelectClasses({ BishopMoveAndNotSuicideTest.class ,PawnMoveAndWinTest.class,TestKingEatPawnTest.class,AnastasiaMateTest.class,EscapeFromEatRuleTest.class})


public class AllTests {

}

 
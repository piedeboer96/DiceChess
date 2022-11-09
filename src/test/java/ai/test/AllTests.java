package ai.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import ai.test.t01.BishopMoveAndNotSuicideTest;
import ai.test.t02.PawnMoveAndWinTest;
import ai.test.t03.TestKingEatPawnTest;
import ai.test.t04.AnastasiaMateTest;
import ai.test.t05.EscapeFromEatRuleTest;
import ai.test.t06.EnPassantTest;
import ai.test.t07.PromotionPawnTest;
import ai.test.t08.RookNotMovingTest;
import ai.test.t10.LongCastleTest;
import ai.test.t11.ShortCastleTest;

@Suite
@SelectClasses({ 
	
	BishopMoveAndNotSuicideTest.class,
	PawnMoveAndWinTest.class,
	TestKingEatPawnTest.class,
	AnastasiaMateTest.class, 
	EscapeFromEatRuleTest.class,
	EnPassantTest.class,
	PromotionPawnTest.class,
	RookNotMovingTest.class,
	LongCastleTest.class,
	ShortCastleTest.class
})

public class AllTests {

}

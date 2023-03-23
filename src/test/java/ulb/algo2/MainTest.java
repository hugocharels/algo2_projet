package ulb.algo2;

// import static org.junit.Assert.assertTrue;
// import ulb.algo2.Main;
import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MainTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
	@Test
	public void testFibo()
	{
		assertTrue(Main.fibo(0) == 0);
		assertTrue(Main.fibo(1) == 1);
		assertTrue(Main.fibo(2) == 1);
		assertTrue(Main.fibo(3) == 2);
		assertTrue(Main.fibo(4) == 3);
		assertTrue(Main.fibo(5) == 5);
		assertTrue(Main.fibo(6) == 8);
	}
}
	

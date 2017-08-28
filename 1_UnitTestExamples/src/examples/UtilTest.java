package examples;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * The test class UtilTest.
 *
 * @author (Per LauvÃ¥s)
 * @version (0.2)
 */
public class UtilTest {
    Util util;

    /**
     * Default constructor for test class UtilTest
     */
    public UtilTest() {
    }

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        util = new Util();
    }



    /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }



    @Test
    public void testAvgNormal() {
        // Arrange
        int[] ints = new int[3];
        ints[0] = 1;
        ints[1] = 2;
        ints[2] = 3;
        // Act
        int result = util.avg(ints);
        // Assert
        assertTrue(result == 2);
    }

    @Test
    public void testAvgEmptyArray() {
        // Arrange
        int[] ints = new int[0];
        // Act
        int result = util.avg(ints);
        // Assert
        assertTrue(result == 0);
    }

    @Test
    public void testAvgNullArray() {
        // Arrange
        int[] ints = null;
        // Act
        int result = util.avg(ints);
        // Assert
        assertTrue(result == 0);
    }

    @Test
    public void testHandleSumNormal() {
        fail("Not implemented yet");
    }

    @Test
    public void testSumEmptyNumbersArray() {
        int[] ints = new int[0];
        int result = util.sum(ints);
        assertTrue(result==0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSumNullNumbersArray() {
        int[] ints = null;
        int result = util.sum(ints);
        assertTrue(result==0);
    }


    @Test
    public void testAvgPosAndMinNumbers() throws Exception {
        int[] ints = new int[4];
        ints[0] = -1;
        ints[1] = 2;
        ints[2] = 3;
        ints[3] = 4;
        assertTrue(util.avg(ints)==2);
    }
}

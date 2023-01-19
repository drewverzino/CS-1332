import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ArrayListCapacityTests {

    private static final int TIMEOUT = 200;

    private ArrayList<String> list;

    @Before

    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)

    public void fillToMaxCapacityUsingAddToFront() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToFront(String.format("%da", i));
        }

        Object[] expected = {"8a", "7a", "6a", "5a", "4a", "3a", "2a", "1a", "0a"};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)

    public void fillToMaxCapacityUsingAddToBack() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a"};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void fillToMaxCapacityUsingAddAtIndex() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            System.out.println(i);
            list.addAtIndex(i, String.format("%da", i));
        }

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a"};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)

    public void fillBeyondInitialCapacityUsingAddToFront() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addToFront("X");

        Object[] expected = {"X", "0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY + 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }
    @Test(timeout = TIMEOUT)

    public void fillBeyondInitialCapacityUsingAddToBack() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addToBack("X");

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "X", null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY + 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }

    @Test(timeout = TIMEOUT)

    public void fillBeyondInitialCapacityUsingAddAtIndex() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addAtIndex(2, "X");

        Object[] expected = {"0a", "1a", "X", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY + 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }

    @Test(timeout = TIMEOUT)

    public void removeAtIndexBeyondInitialCapacity() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addAtIndex(2, "X");

        list.removeAtIndex(2);

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null, null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)

    public void removeFromFrontBeyondInitialCapacity() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addToFront("X");

        list.removeFromFront();

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null, null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)

    public void removeFromBackBeyondInitialCapacity() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.addToBack("X");

        list.removeFromBack();

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null, null, null, null, null, null, null, null, null};

        assertEquals(ArrayList.INITIAL_CAPACITY, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }

    @Test(timeout = TIMEOUT)

    public void removeAtIndexMaxCapacity() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.removeAtIndex(2);

        Object[] expected = {"0a", "1a", "3a", "4a", "5a", "6a", "7a", "8a", null};

        assertEquals(ArrayList.INITIAL_CAPACITY - 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)

    public void removeFromFrontMaxCapacity() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.removeFromFront();

        Object[] expected = {"1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", null};

        assertEquals(ArrayList.INITIAL_CAPACITY - 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }

    @Test(timeout = TIMEOUT)

    public void removeFromBackMaxCapacity() {

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.format("%da", i));
        }

        list.removeFromBack();

        Object[] expected = {"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", null};

        assertEquals(ArrayList.INITIAL_CAPACITY - 1, list.size());
        assertArrayEquals(expected, list.getBackingArray());

    }



}
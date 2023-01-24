import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertThrows;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/Ian-Boraks
 * @version 1.0
 */
public class AWESOMETests {
    private static final int TIMEOUT = 200;
    private CircularSinglyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new CircularSinglyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackSizeOne() {
        list.addToFront("0a");

        assertEquals("0a", list.removeFromBack());
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontSizeOne() {
        list.addToFront("0a");

        assertEquals("0a", list.removeFromFront());
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexSizeOne() {
        list.addToFront("0a");

        assertEquals("0a", list.removeAtIndex(0));
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMultipleFromFront() {
        list.addToFront("0a");
        list.addToFront("1b");
        list.addToFront("2c");
        list.addToFront("3d");

        assertEquals("3d", list.removeFromFront());
        assertEquals("2c", list.removeFromFront());
        assertEquals("1b", list.removeFromFront());
        assertEquals("0a", list.removeFromFront());
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMultipleFromBack() {
        list.addToFront("0a");
        list.addToFront("1b");
        list.addToFront("2c");
        list.addToFront("3d");

        assertEquals("0a", list.removeFromBack());
        assertEquals("1b", list.removeFromBack());
        assertEquals("2c", list.removeFromBack());
        assertEquals("3d", list.removeFromBack());
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMultipleAtIndex() {
        list.addToFront("0a");
        list.addToFront("1b");
        list.addToFront("2c");
        list.addToFront("3d");

        assertEquals("0a", list.removeAtIndex(3));
        assertEquals("1b", list.removeAtIndex(2));
        assertEquals("2c", list.removeAtIndex(1));
        assertEquals("3d", list.removeAtIndex(0));
        assertNull(list.getHead());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testToArrayWhenEmpty() {
        list.addToFront("0a");

        assertEquals("0a", list.removeFromBack());
        list.toArray();
    }

    @Test(timeout = TIMEOUT)
    public void testAddExceptions() {
        //list.addAtIndex(-1, "a0");

        //list.addAtIndex(1, "a0");

        //list.addAtIndex(0, null);

        //list.addToFront(null);

        //list.addToBack(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveExceptions() {
        //list.removeAtIndex(-1);

        //list.removeAtIndex(0);

        //list.removeAtIndex(1);

        //list.removeFromBack();

        //list.removeFromFront();

        //list.removeLastOccurrence(null);

        /*for (int i = 0; i < 10; i++) {
            list.addToFront("a" + i);
        }

        list.removeLastOccurrence("b0");*/

        //list.clear();
    }

    @Test(timeout = TIMEOUT)
    public void testGetExceptions() {
        for (int i = 0; i < 10; i++) {
            list.addToFront("a" + i);
        }

        //list.get(12);

        //list.get(-1);
    }
}
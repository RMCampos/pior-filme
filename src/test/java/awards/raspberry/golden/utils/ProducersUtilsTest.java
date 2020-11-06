package awards.raspberry.golden.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProducersUtilsTest {

    @Test
    public void getNoneProducersListTest() {
        String none = "";

        List<String> producers = ProducersUtils.getProducersList(none);
        Assert.assertNotNull("Should not be null", producers);
        Assert.assertTrue("Should be empty", producers.isEmpty());
    }

    @Test
    public void getOneProducersListTest() {
        String one = "Gerald R. Molen";

        List<String> producers = ProducersUtils.getProducersList(one);
        Assert.assertNotNull("Should not be null", producers);
        Assert.assertFalse("Should not be empty", producers.isEmpty());
        Assert.assertEquals("Size should be 1", 1, producers.size());
        Assert.assertEquals("Producer 1 should be 'Gerald R. Molen'", "Gerald R. Molen", producers.get(0));
    }

    @Test
    public void getTwoProducersListTest() {
        String two = "Martin Brest and Casey Silver";

        List<String> producers = ProducersUtils.getProducersList(two);
        Assert.assertNotNull("Should not be null", producers);
        Assert.assertFalse("Should not be empty", producers.isEmpty());
        Assert.assertEquals("Size should be 2", 2, producers.size());
        Assert.assertEquals("Producer 1 should be 'Martin Brest'", "Martin Brest", producers.get(0));
        Assert.assertEquals("Producer 2 should be 'Casey Silver'", "Casey Silver", producers.get(1));
    }

    @Test
    public void getMoreProducersListTest() {
        String more = "Lorenzo di Bonaventura, Ian Bryce, Tom DeSanto and Don Murphy";

        List<String> producers = ProducersUtils.getProducersList(more);
        Assert.assertNotNull("Should not be null", producers);
        Assert.assertFalse("Should not be empty", producers.isEmpty());
        Assert.assertEquals("Size should be 4", 4, producers.size());
        Assert.assertEquals("Producer 1 should be 'Lorenzo di Bonaventura'", "Lorenzo di Bonaventura", producers.get(0));
        Assert.assertEquals("Producer 2 should be 'Ian Bryce'", "Ian Bryce", producers.get(1));
        Assert.assertEquals("Producer 3 should be 'Tom DeSanto'", "Tom DeSanto", producers.get(2));
        Assert.assertEquals("Producer 4 should be 'Don Murphy'", "Don Murphy", producers.get(3));
    }

    @Test
    public void getEvenMoreProducersListTest() {
        String evenMore = "John Mallory Asher, BJ Davis, Rod Hamilton, Kimberley Kates, Michael Manasseri, Jenny McCarthy and Trent Walford";

        List<String> producers = ProducersUtils.getProducersList(evenMore);
        Assert.assertNotNull("Should not be null", producers);
        Assert.assertFalse("Should not be empty", producers.isEmpty());
        Assert.assertEquals("Size should be 7", 7, producers.size());
        Assert.assertEquals("Producer 1 should be 'John Mallory Asher'", "John Mallory Asher", producers.get(0));
        Assert.assertEquals("Producer 2 should be 'BJ Davis'", "BJ Davis", producers.get(1));
        Assert.assertEquals("Producer 3 should be 'Rod Hamilton'", "Rod Hamilton", producers.get(2));
        Assert.assertEquals("Producer 4 should be 'Kimberley Kates'", "Kimberley Kates", producers.get(3));
        Assert.assertEquals("Producer 5 should be 'Michael Manasseri'", "Michael Manasseri", producers.get(4));
        Assert.assertEquals("Producer 6 should be 'Jenny McCarthy'", "Jenny McCarthy", producers.get(5));
        Assert.assertEquals("Producer 7 should be 'Trent Walford'", "Trent Walford", producers.get(6));
    }
}

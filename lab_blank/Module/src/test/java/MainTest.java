import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testConstruct() { assertThrows(IllegalArgumentException.class, () -> new Date(2026, 12, 32)); }
    @Test
    void testDayOfYear() { assertEquals(365, Date.dayOfYear(new Date(2026, 12, 31))); }
    @Test
    void testDaysInMonth() { assertEquals(28, Date.daysInMonth(2)); }
    @Test
    void testTomorrow() { assertEquals(new Date(2027, 1, 1),  Date.tomorrow(new Date(2026, 12, 31))); }
    @Test
    void testComesBefore() { assertTrue(Date.comesBefore(new Date(1, 1, 1), new Date(2, 1, 1)));}
    @Test
    void testIntervalDays() {
        var r1 = new DateInterval(new Date(2026, 1, 1), new Date(2026, 2, 15));
        assertEquals(45, DateInterval.dateIntervalDays(r1));
    }
    @Test
    void testDateOverlap() {
        var r1 = new DateInterval(new Date(2026, 1, 1), new Date(2026, 2, 15));
        var r2 = new DateInterval(new Date(2026, 2, 1), new Date(2026, 2, 15));
        assertTrue(DateInterval.dateOverlap(r2, r1));
    }
    @Test
    void testDateIntervalIntersect() {
        var r1 = new DateInterval(new Date(2026, 1, 1), new Date(2026, 2, 15));
        var r3 = new DateInterval(new Date(2026, 3, 15), new Date(2026, 4, 15));
        assertNull(DateInterval.dateIntervalIntersect(r1, r3));
    }

    @Test
    void testTomorrows() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));

        DateList<Date> db = new DateList<>();
        db.addLast(new Date(1, 1, 2));
        db.addLast(new Date(1, 1, 3));
        db.addLast(new Date(1, 1, 4));

        assertEquals(
                DateList.allTomorrows(dl).stream().map(Object::toString).collect(Collectors.joining(", ")),
                db.stream().map(Object::toString).collect(Collectors.joining(", "))
        );
    }

    @Test
    void testListLen() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));
        dl.addLast(new Date(1, 1, 4));
        assertEquals(4, DateList.listLen(dl));
    }

    @Test
    void testMinDate() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));
        dl.addLast(new Date(1, 1, 4));
        assertEquals(new Date(1, 1, 1), DateList.minDate(dl));
    }

    @Test
    void testMaxDate() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));
        dl.addLast(new Date(1, 1, 4));
        assertEquals(new Date(1, 1, 4), DateList.maxDate(dl));
    }

    @Test
    void testDateCover() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));
        dl.addLast(new Date(1, 1, 4));
        assertEquals(new DateInterval(new Date(1, 1, 1), new Date(1, 1, 4)), DateList.dateCover(dl));
    }

    @Test
    void testAddToEnd() {
        DateList<Date> dl = new DateList<>();
        dl.addLast(new Date(1, 1, 1));
        dl.addLast(new Date(1, 1, 2));
        dl.addLast(new Date(1, 1, 3));
        dl.addLast(new Date(1, 1, 4));

        DateList<Date> db = new DateList<>();
        db.addLast(new Date(1, 1, 1));
        db.addLast(new Date(1, 1, 2));
        db.addLast(new Date(1, 1, 3));
        db.addLast(new Date(1, 1, 4));
        db.addLast(new Date(1, 1, 5));

        DateList.addToEnd(dl, new Date(1, 1,  5));

        assertEquals(
                dl.stream().map(Object::toString).collect(Collectors.joining(", ")),
                db.stream().map(Object::toString).collect(Collectors.joining(", "))
        );
    }

    @Test
    void testAppend() {
        DateList<Date> db = new DateList<>();
        db.addLast(new Date(1, 1, 1));
        db.addLast(new Date(1, 1, 2));
        db.addLast(new Date(1, 1, 3));
        db.addLast(new Date(1, 1, 4));
        db.addLast(new Date(1, 1, 5));

        DateList<Date> l2 = new DateList<>();
        l2.addLast(new Date(10, 2, 1));
        l2.addLast(new Date(10, 2, 2));
        l2.addLast(new Date(10, 2, 3));
        l2.addLast(new Date(10, 2, 4));

        DateList<Date> df = new DateList<>();
        df.addLast(new Date(1, 1, 1));
        df.addLast(new Date(1, 1, 2));
        df.addLast(new Date(1, 1, 3));
        df.addLast(new Date(1, 1, 4));
        df.addLast(new Date(1, 1, 5));
        df.addLast(new Date(10, 2, 1));
        df.addLast(new Date(10, 2, 2));
        df.addLast(new Date(10, 2, 3));
        df.addLast(new Date(10, 2, 4));

        assertEquals(
                DateList.append(db, l2).stream().map(Object::toString).collect(Collectors.joining(", ")),
                df.stream().map(Object::toString).collect(Collectors.joining(", "))
        );
    }
}

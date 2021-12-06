package oleg.bryl.springbootweblibrary.paginator;

public class PageItem {
    private int number;
    private boolean actual;

    /**
     *
     * @param number
     * @param actual
     */
    public PageItem(int number, boolean actual) {
        this.number = number;
        this.actual = actual;
    }

    public int getNumber() {
        return number;
    }

    public boolean isActual() {
        return actual;
    }
}

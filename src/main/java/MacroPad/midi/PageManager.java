package MacroPad.midi;

public class PageManager {
    private int currentPage = 0;

    public void changePage(int page) {
        if (page < 0 || page > 7) {
            return;
        }

        currentPage = page;
        System.out.println("Page changed to " + currentPage);
    }
    public int getCurrentPage() {
        return currentPage;
    }
}

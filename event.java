public class event implements Comparable<event> {
    int time; //remaining Time
    String status; //either complete or arrive
    process process;

    public event(int time, String status, process process) {
        this.time = time;
        this.status = status;
        this.process = process;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int compareTo(event other) {
        return Integer.compare(this.time, other.time);
    }
}

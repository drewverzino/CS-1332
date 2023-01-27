public class Test {
    public static void main(String[] args) {
        ArrayQueue<String> q = new ArrayQueue<>();
        for (int i = 0; i < 11; i++) {
            System.out.println(q.size());
            q.enqueue(Integer.toString(i));
        }
    }
}

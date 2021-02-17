public class StackWithMax{

    private Integer maxvalue = 0;
    private final LinkedList<Integer> stack = new LinkedList<>();

    public void push(Integer item) {
        if (stack.isEmpty()) {
            maxvalue = item;
        } else {
            if (item.compareTo(maxvalue) <= 0) {
                stack.push(item);
            }
            else {
                stack.push(2 * item - maxvalue);
                maxvalue = item;
            }
        }
    }

    public Integer pop() {
        Integer item = stack.pop();
        if (item <= maxvalue)
            return item;
        else {
            maxvalue = 2 * maxvalue - item;
            return item;
        }
    }

    public Integer getMax() {
        return maxvalue;
    }

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

public class opg {
    public static int[][] opMatrix = {
            //+  *  (  )  i  #
            { 2, 1, 1, 2, 1, 2 }, // +
            { 2, 2, 1, 2, 1, 2 }, // *
            { 1, 1, 1, 3, 1, -1 }, // (
            { 2, 2, -1, 2, -1, 2 }, // )
            { 2, 2, -1, 2, -1, 2 }, // i
            { 1, 1, 1, -1, 1, 0 }  // #
    };

    public static void main(String[] args) {
        StringBuilder opString = new StringBuilder();
        try {
            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            String getInput = input.readLine();
            opString.append(getInput);
            opString.append('#');

        } catch (Exception e) {
            e.getStackTrace();
        }

        Stack<Character> charactersStack = new Stack<Character>();
        Stack<Character> operatorStack = new Stack<Character>();
        operatorStack.push('#');

        int i = 0;
        char cur, peek;
        while (true) {
            cur = opString.charAt(i);
            peek = operatorStack.peek();
            if (isOperator(cur) != -1) {
                int priority = priorityCompare(peek, cur);
                // less or equal, push
                if (priority == 1 || priority == 3) {
                    System.out.println("I" + cur);
                    operatorStack.push(cur);
                    i++;
                }
                // greater, cut
                else if (priority == 2){
                    // i->R, push R to charactersStack
                    if (peek == 'i') {
                        operatorStack.pop();
                        charactersStack.push('R');
                    }
                    // R+R|R*R->R, pop*2 & push*1 = pop
                    if (peek == '+' || peek == '*') {
                        operatorStack.pop();
                        charactersStack.pop();
                    }
                    // (R)->R, pop R & push R = 0
                    if (peek == '(') {
                        operatorStack.pop();
                    }
                    if (charactersStack.size() == 0 || operatorStack.size() == 0) {
                        System.out.println("RE");
                        return;
                    }
                    else {
                        System.out.println("R");
                        continue;
                    }
                }
                // can't adjacent
                else if (priority == -1){
                    System.out.println("E");
                    return;
                }
                // termination
                else break;
            } else {
                System.out.println("E");
                return;
            }
        }
    }

    // calculate priority based on opMatrix
    public static int priorityCompare(char inner, char outer) {
        int in = isOperator(inner);
        int out = isOperator(outer);

        return opMatrix[in][out];
    }

    // if is operator, return position in opMatrix
    // else return -1
    public static int isOperator(char op) {
        switch (op) {
            case '+':
                return 0;
            case '*':
                return 1;
            case '(':
                return 2;
            case ')':
                return 3;
            case 'i':
                return 4;
            case '#':
                return 5;
            default:
                return -1;
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Value w2= new Value(1.0f);
        Value x2=new Value(0f);
        Value w1=new Value(-3.0f);
        Value x1=new Value(2.0f);
        Value w1x1=w1.multiply(x1);
        
        Value w2x2=w2.multiply(x2);
        
        Value w1x1_w2x2=w1x1.add(w2x2);
        Value b = new Value(6.8814f);
        Value n=w1x1_w2x2.add(b);
        Value out=n.tanh();
        out.backward();
        System.out.println("x1.grad="+x1.grad);
        System.out.println("x2.grad="+x2.grad);
        System.out.println("w1.grad="+w1.grad);
        System.out.println("w2.grad="+w2.grad);
        System.out.println("b.grad="+b.grad);
        System.out.println("w1x1+w2x2 grad="+w1x1_w2x2.grad);
        
    }
}

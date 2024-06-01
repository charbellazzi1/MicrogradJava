public class Neuron {
    Value[] w;
    Value b;
    public Neuron(int nin){
        w=new Value[nin];
        for(int i=0;i<nin;i++){
            w[i]=new Value((float) Math.random(),"w"+(i+1));
        }
        b=new Value((float) Math.random(),"bias");
    }

public Value call(Value[] x){
        Value[] wx=new Value[w.length];
        Value n=b;
        for(int i=0;i<w.length;i++){
            //multiplying weights by inputs
            wx[i]=w[i].multiply(x[i]);
            //adding the result to the bias
            n=n.add(wx[i]);
        }
        
        Value out=n.tanh();
        return out;
}
public Value[] parameters(){
    Value[] w2=new Value[w.length+1];
        System.arraycopy(w, 0, w2, 0, w.length);
    w2[w.length]=b;
    return w2;
}
}
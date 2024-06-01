
public class Layer {
    Neuron[] neurons;
    public Layer(int nin,int nout){
        neurons=new Neuron[nout];
        for(int i=0;i<nout;i++){
            neurons[i]=new Neuron(nin);
        }
    }
    public Value[] call(Value[] x){
        Value[] out=new Value[neurons.length];
        for(int i=0;i<neurons.length;i++){
            out[i]=neurons[i].call(x);
        }
        return out;

    }
    public Value[] parameters(){
        Value[] out=new Value[0];
        for(Neuron i:neurons){
            Value[] p=i.parameters();
            Value[] out2=new Value[out.length+p.length];
            System.arraycopy(out, 0, out2, 0, out.length);
            System.arraycopy(p, 0, out2, out.length, p.length);
            out=out2;
        }
        return out;
    }
}

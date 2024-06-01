public class MLP {
    Layer [] layers;
    public MLP(int nin,int[] nouts){
        layers=new Layer[nouts.length];
        layers[0]=new Layer(nin,nouts[0]);
        for(int i=1;i<nouts.length;i++){
            layers[i]=new Layer(nouts[i-1],nouts[i]);
        }
    }
    public Value[] call(float[] x){
        Value[] out=new Value[x.length];
        for(int i=0;i<x.length;i++){
            out[i]=new Value(x[i]);
        }
        for(Layer i:layers){
            out=i.call(out);
        }
        return out;
    }
    public Value[] parameters(){
        Value[] out=new Value[0];
        for(Layer i:layers){
            Value[] p=i.parameters();
            Value[] out2=new Value[out.length+p.length];
            System.arraycopy(out, 0, out2, 0, out.length);
            System.arraycopy(p, 0, out2, out.length, p.length);
            out=out2;
        }
        return out;
    }
}

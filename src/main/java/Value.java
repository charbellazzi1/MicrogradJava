
import java.util.HashSet;
public class Value{
    float data;
    HashSet<Value> prev;
    String label;
    
    float grad;
    
    Runnable _backward=()->{};
    public Value(){
        data = 0;
        prev = new HashSet<Value>();
        grad=0;
    }
    public Value(float data, HashSet<Value> children, char op ,String label){
        this.data = data;
        prev=children;
        this.label=label;
    }
    public Value(float data, HashSet<Value> children, String label){
        this.data = data;
        prev=children;
        this.label=label;
    }
    public Value(float data, String label){
        this.data = data;
        prev = new HashSet<Value>();
        this.label=label;
    }
    public Value(float data, HashSet<Value> children,char op){
        this.data = data;
        prev=children;
    }
    public Value(float data){
        this.data = data;
        prev = new HashSet<Value>();
    }
    public Value(float data, HashSet<Value> children){
        this.data = data;
        prev=children;
    }
    
    public String toString(){
        return "Value="+ data+" grad="+this.grad;
    }
    public Value add(Object other){
        HashSet<Value> n= new HashSet<Value>();
        n.add(this);
        Value others;
        if (other instanceof Value) {
            others = (Value) other;
        } else if (other instanceof Number) {
            float t = ((Number) other).floatValue();
            others = new Value(t);
        } else {
            throw new IllegalArgumentException("Unsupported type for multiplication");
        }
        n.add(others);
        
        Value out =new Value(others.data+this.data,n,'+');
        out._backward=()->{
            this.grad+=1.0f*out.grad;
            others.grad+=1.0f*out.grad;
        };
        return out;
    }
    public Value multiply(Object other){
        HashSet<Value> n= new HashSet<Value>();
        n.add(this);
        Value others;
        if (other instanceof Value) {
            others = (Value) other;
        } else if (other instanceof Number) {
            float t = ((Number) other).floatValue();
            others = new Value(t);
        } else {
            throw new IllegalArgumentException("Unsupported type for multiplication");
        }
        n.add(others);
        Value out= new Value( this.data*others.data,n ,'*');
        out._backward=()->{
            this.grad+=others.data*out.grad;
            others.grad+=this.data*out.grad;
        };
        return out;
    }
    public Value exp(){
      float x =this.data;
        HashSet<Value> n= new HashSet<Value>();
        n.add(this);
        Value out= new Value((float)Math.exp(x),n,'e');
        out._backward=()->{
            this.grad+=out.grad*out.data;
        };
        return out;
    }
    public Value pow(Object other){
        HashSet<Value> n= new HashSet<Value>();
        n.add(this);
        Value others;
        if (other instanceof Value) {
            others = (Value) other;
        } else if (other instanceof Number) {
            float t = ((Number) other).floatValue();
            others = new Value(t);
        } else {
            throw new IllegalArgumentException("Unsupported type for multiplication");
        }
        n.add(others);
        Value out= new Value((float)Math.pow(this.data,others.data),n,'^');
        out._backward=()->{
            this.grad+=others.data*Math.pow(this.data,others.data-1)*out.grad;
            others.grad+=Math.pow(this.data,others.data)*Math.log(this.data)*out.grad;
        };
        return out;
    }
    public Value divide(Object other){
        HashSet<Value> n= new HashSet<Value>();
        n.add(this);
        Value others;
        if (other instanceof Value) {
            others = (Value) other;
        } else if (other instanceof Number) {
            float t = ((Number) other).floatValue();
            others = new Value(t);
        } else {
            throw new IllegalArgumentException("Unsupported type for multiplication");
        }
        n.add(others);
        Value exp=new Value(-1.0f);
        Value exp2=others.pow(exp);
        Value out=new Value(this.data * exp2.data,n,'/');
        out._backward=()->{
            this.grad+=exp2.data*out.grad;
            others.grad=(-1.0f*this.data/(others.data*others.data))*out.grad;
        
        };
        return out;
    }
    public Value tanh(){
        HashSet n= new HashSet<Value>();
        n.add(this);
        Value out= new Value((float)Math.tanh(this.data),n,'t');
        out._backward=()->{
            this.grad+=(1-out.data*out.data)*out.grad;
        };
        return out;

    }

    public void backward(){
        this.grad=1;
        HashSet<Value> visited = new HashSet<Value>();
        Value[] topo=new Value[0];
        topo=this.buildTopo(visited,topo);
        //System.out.println("length="+topo.length);
        for(int i=topo.length-1;i>=0;i--){
            topo[i]._backward.run();
        //     if(topo[i].label!=null){
        //     System.out.println("data="+topo[i].data +"grad="+topo[i].grad+"label="+topo[i].label);
        // }
    }
    }
    public Value[] buildTopo(HashSet<Value> visited,Value[] topo){
        if(visited.contains(this)){
            return topo;
        }
        visited.add(this);
        for(Value v:prev){
           topo= v.buildTopo(visited,topo);
        }
        Value[] newTopo = new Value[topo.length+1];
        System.arraycopy(topo, 0, newTopo, 0, topo.length);
        newTopo[topo.length]=this;
        topo=newTopo;
        return topo;
    }
    
    
}

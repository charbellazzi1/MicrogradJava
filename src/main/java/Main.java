
public class Main {
    public static void main(String[] args) {
       
        MLP n=new MLP(3,new int[]{4,4,1});
        
        float [][]xs={{2.0f,3.0f,-1.0f},{3.0f,-1.0f,0.5f},{0.5f,1.0f,1.0f},{1.0f,1.0f,-1.0f}};
       
        Value []ys={new Value(1.0f),new Value(-1.0f),new Value(-1.0f),new Value(1.0f)};
       
        Value y[]=new Value[xs.length];
        int j;
        //training
        Value loss[];
        for (int k = 0; k < 30; k++) {
            Value totalLoss=new Value(0.0f);
            j=0;
            for(float[]x :xs){
                y[j]=n.call(x)[0];
    
                j++;
            }
           
            
            loss=new Value[xs.length];
            for(int i=0;i<loss.length;i++){
                loss[i]=(ys[i].add(y[i].multiply(-1))).multiply((ys[i].add(y[i].multiply(-1))));
                totalLoss=totalLoss.add(loss[i]);
            }
           
            totalLoss.backward();
            System.out.println("total loss="+totalLoss);
            Value[] p=n.parameters();
            for(Value i:p){
                i.data+=-0.08*i.grad;
                i.grad=0.0f;
                
            }            
            
        }
        
    }
}

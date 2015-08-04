import java.util.concurrent.*;

public class CalculateWithOutReturn extends RecursiveAction{
    //thresold
    int seqThreshold;

    //array
    double[] data;
    
    //begin and end part of array
    int start, end;
    
    CalculateWithOutReturn(double[] data, int start, int end, int seqThreshold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.seqThreshold = seqThreshold;
    }
    
    //execute each element of array
    protected void compute(){
        //check thresold
        if((end - start) < seqThreshold){
            //each element from start to end
            for(int i = start; i < end; i++){
                data[i] = Math.sqrt(data[i]);
            }
        }
        else{
            //find miidle
            int middle = (start + end)/2;
            
            //new task
            invokeAll(new CalculateWithOutReturn(data, start, middle, seqThreshold),
                new CalculateWithOutReturn(data, middle, end, seqThreshold));
        }
    }
}
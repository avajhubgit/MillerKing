import java.util.concurrent.*;

public class CalculateWithReturn extends RecursiveTask<Double>{
    //thresold
    int seqThreshold;

    //array
    double[] data;
    
    //begin and end part of array
    int start, end;
    
    CalculateWithReturn(double[] data, int start, int end, int seqThreshold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.seqThreshold = seqThreshold;
    }
    
    //execute each element of array
    protected Double compute(){
        double sum = 0;
        
        //check thresold
        if((end - start) < seqThreshold){
            //each element from start to end
            for(int i = start; i < end; i++){
                sum += data[i];
            }
        }
        else{
            //find miidle
            int middle = (start + end)/2;
            
            //two new task
            CalculateWithReturn subTaskA = new CalculateWithReturn(data, start, middle, seqThreshold);
            CalculateWithReturn subTaskB = new CalculateWithReturn(data, middle, end, seqThreshold);
            
            //run subtask
            subTaskA.fork();
            subTaskB.fork();
            
            //wait
            sum = subTaskA.join() + subTaskB.join();
        }
        return sum;
    }
}
import java.util.concurrent.*;

public class MillerKingRun {

    public static void main(String[] args) {
        //handle on array item and return result
        withReturn();
        //handle on array item directly
        //withOutReturn();
        }
    
    //handle on array item and return result    
    private static void withReturn(){
        //time mark
        long beginT, endT, avgT = 0;
        //for statistical average
        final int repeatCount = 1000000;
        //sequential threshold
        final int seqThreshold = 3000;
        System.out.println("Sequential threshold: " + seqThreshold);
                
        //array
        double[] nums = new double[100000];
        //init elements of array
        for(int i = 0; i < nums.length; i++){
            nums[i] = (double)i;
        }
        //output array
        /*System.out.println("A portion of the original sequence:");
        for(int i = 0; i < 10; i++){
            System.out.print(nums[i] + " ");
        }
        System.out.println();*/

        //level of parallelism
        for(int pLevel = 1; pLevel <= 5; pLevel++){
            //create pool of tasks
            ForkJoinPool fjp = new ForkJoinPool(pLevel);
            //show targeted parallelism of computer
            System.out.println("Parallelism is: " + fjp.getParallelism());

            for(int j = 0; j < repeatCount; j++){
                CalculateWithReturn task = new CalculateWithReturn(nums, 0, nums.length, seqThreshold);
                //start chrono
                beginT = System.nanoTime();
                //run main task
                Double summation = fjp.invoke(task);
                //finish chrono
                endT = System.nanoTime();
                //show time
                //System.out.format("Elapsed time: %,09d ns%n", (endT - beginT));
                //calculate average time
                avgT += endT - beginT;
                //System.out.println("Summation is: " + summation);
            }
            System.out.format("Elapsed AVG time: %,09d ns%n", (avgT/repeatCount));
        }
        /*
        AVGRes, thousand ns
        500     1000    1500    2000    3000    4000    5000    6000    7000    8000    9000    10000   11000
    1)  205     174     172     155     154     147     146     146     141     141     143     142     142
    2)  326     282     279     253     251     235     234     236     224     222     224     224     223
    3)  470     401     398     359     357     336     334     336     320     314     317     316     317
    4)  637     549     548     497     492     475     471     477     460     447     453     452     454
    5)  846     740     739     674     670     655     650     654     649     626     632     636     637
        Mininum with plevel = 4 and seqThreshold = 8000
        */
    }
    
    //handle on array item directly
    private static void withOutReturn(){
        //sequential threshold
        final int seqThreshold = 2000;
        //for statistical average
        final int repeatCount = 1000000;
                
        //time mark
        long beginT, endT, avgT = 0;
        
        //array
        double[] nums = new double[100000];
        //init elements of array
        for(int i = 0; i < nums.length; i++){
            nums[i] = (double)i;
        }
        
        //output array
        /*System.out.println("A portion of the original sequence:");
        for(int i = 0; i < 10; i++){
            System.out.print(nums[i] + " ");
        }
        System.out.println();*/
        
        System.out.println("Sequential threshold: " + seqThreshold);
        
        //level of parallelism
        for(int pLevel = 1; pLevel <= 5; pLevel++){
            //create pool of tasks
            ForkJoinPool fjp = new ForkJoinPool(pLevel);
            //show targeted parallelism of computer
            System.out.println("Parallelism is: " + fjp.getParallelism());

            for(int j = 0; j < repeatCount; j++){
                CalculateWithOutReturn task = new CalculateWithOutReturn(nums, 0, nums.length, seqThreshold);
                //start chrono
                beginT = System.nanoTime();
                //run main task
                fjp.invoke(task);
                //finish chrono
                endT = System.nanoTime();
                //show time
                //System.out.format("Elapsed time: %,09d ns%n", (endT - beginT));
                //calculate average time
                avgT += endT - beginT;
            }
            System.out.format("Elapsed AVG time: %,09d ns%n", (avgT/repeatCount));
        }
        //output array
        /*System.out.println("A portion of the transormed sequence:");
        for(int i = 0; i < 10; i++){
            System.out.format("%.4f ", nums[i]);
        }
        System.out.println();*/
/*
        AVGRes, thousand ns
        500     1000    1500    2000    3000    4000    5000    6000
    1)  293     277     277     271     270     266     266     267
    2)  495     462     463     438     434     425     425     436
    3)  691     647     647     616     613     602     599     613
    4)  908     855     854     821     810     757     763     767
    5)  1143    1047    1077    982     978     880     889     891
        Mininum with plevel = 4 and seqThreshold = 4000
        */
        }
    }
public class process{

    int ID, arrivalTime, burstTime, remainingTime, completionTime, turnaroundTime, waitingTime;
    
   
    public process(int id, int arrivalTime,int burstTime){
    this.ID=id;
    this.arrivalTime=arrivalTime;
    this.burstTime=burstTime;
    this.remainingTime = burstTime;
    }
   
    public boolean isComplete(){
    if(remainingTime==0)
    return true;

    return false;
    }
    
   
   
   
   
   
   
   }
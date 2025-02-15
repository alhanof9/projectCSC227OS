public class process{

    int ID, arrivalTime ,burstTime;
    
   
    public process(int id, int arrivalTime,int burstTime){
    this.ID=id;
    this.arrivalTime=arrivalTime;
    this.burstTime=burstTime;
    
    }
   
   
    public boolean isComplete(){
    if(burstTime==0)
    return true;
    
    return false;
    }
    
   
   
   
   
   
   
   }
public class Process{

    int ID, arrivalTime, burstTime, remainingTime, completionTime, turnaroundTime, waitingTime;
    String status;
    // new -> when initiate the object
    // ready -> when burst time coming
    // running -> when the procces is proccessing
    // terminated -> when the procces finish
   
    public Process(int id, int arrivalTime, int burstTime){
    this.ID=id;
    this.arrivalTime=arrivalTime;
    this.burstTime=burstTime;
    this.remainingTime = burstTime;
    this.status = "new";
    }
   
    public void setStatus(String s){
    status = s;
    }
   
   
   
   
   
   
   }
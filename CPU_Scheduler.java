import java.util.*;

public class CPU_Scheduler{

static int Etime =0;
static int totalContextSwitch=0;
static int totalTurnaround = 0, totalWaiting = 0;
public static void main(String[]aa){
Scanner input = new Scanner(System.in);
 
 System.out.print("Number of processes= ");
 int size = input.nextInt();

 System.out.print("Processes ID (");
 for(int i=0; i<size; i++) {
  System.out.print("P"+(i+1));
  if(i+1 != size)
   System.out.print(", ");
 }
 System.out.println(")");

 Process processes[] = new Process[size];

 System.out.println("Arrival times and burst times in (ms) as follows:");
  System.out.println("-------------------------------------------------");

 for(int i=0; i<size; i++)
 {
  System.out.print("P"+(i+1)+":"+"\n"+"Arrival time = ");
  int Arrival = input.nextInt();
  System.out.print("Burst time = ");
  int Burst = input.nextInt();
  processes[i] = new Process(i+1, Arrival, Burst); //add to the array 
 }//endFor
 System.out.println("-------------------------------------------------");
 
 System.out.println("Scheduling Algorithm: Shortest remaining time first");
 System.out.print("Context	Switch: ");
 int SwitchTime=input.nextInt();
  System.out.println("-------------------------------------------------");
 
 System.out.println("Time      Process/CS");
 Scheduler(processes, SwitchTime);
 System.out.println("-------------------------------------------------");
 System.out.println("Performance Metrics");

 int totalTurnaround=0, totalWaiting =0;
 for(int i=0; i<processes.length; i++) {
 totalTurnaround += processes[i].turnaroundTime;
 totalWaiting += processes[i].waitingTime;
 }

 System.out.printf("Average Turnaround Time: %.0f\n", (double) totalTurnaround / processes.length);
System.out.printf("Average Waiting Time: %.1f\n", (double) totalWaiting / processes.length);
System.out.printf("CPU Utilization: %.2f\n", ((double) (Etime - (size*SwitchTime)) / Etime) * 100);
 

}//end the main

public static void Scheduler(Process[] processes, int SwitchTime){
  int Stime=0;
  int completed=0;
  Process current = processes[0];
  Stime = processes[0].arrivalTime;
  current.setStatus("running");
  
  while(completed != processes.length ) {
      current.remainingTime--;
      Etime++;
  
      for(int i=0; i<processes.length; i++)
        if(!processes[i].status.equals("terminated") && Etime==processes[i].arrivalTime)
          if(current.remainingTime > processes[i].remainingTime) {
            System.out.println(Stime + "-" + Etime + "       P" + current.ID);
            current.setStatus("ready");
            current = processes[i]; //switch
            current.setStatus("running");
            Stime = Etime ;
            Etime += SwitchTime;
            System.out.println(Stime + "-" + Etime + "       CS");
            totalContextSwitch += SwitchTime;
            Stime = Etime;
            break;
            } 
        
        if (current.remainingTime == 0) {
          completed++;
          current.setStatus("terminated");
          System.out.println(Stime + "-" + Etime + "       P" + current.ID);
          current.completionTime = Etime;
          current.turnaroundTime = current.completionTime - current.arrivalTime;
          current.waitingTime = current.turnaroundTime - current.burstTime;
          totalTurnaround += current.turnaroundTime;
          totalWaiting += current.waitingTime;

          if(completed != processes.length)
            current = processes[SwitchToMin(processes,Etime)];
          current.setStatus("terminated");
          Stime = Etime;

          if(completed != processes.length) {
            Etime += SwitchTime;
            System.out.println(Stime + "-" + Etime + "       CS");
            totalContextSwitch+= SwitchTime;
          }
          Stime = Etime;
          }
    }
  }
  
  public static int SwitchToMin(Process[] processes, int time) { //return the index for the smallest burst time
  int min = -1;
  for(int i=0; i<processes.length; i++) {
    if(!processes[i].status.equals("terminated") && time >= processes[i].arrivalTime)
      if(min== -1 || processes[i].remainingTime < processes[min].remainingTime)
        min = i;
        else if(processes[i].remainingTime == processes[min].remainingTime)
          if(processes[i].arrivalTime < processes[min].arrivalTime)
              min = i;
  }    
  return min;
}


}//endclass
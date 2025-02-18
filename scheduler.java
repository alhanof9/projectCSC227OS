import java.util.*;

public class scheduler{


  static int Etime =0;
  static int totalContextSwitch=0;
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

 process processes[] = new process[size];
 //PriorityQueue<event> eventQueue = new PriorityQueue<>();

 System.out.println("Arrival times and burst times in (ms) as follows:");
  System.out.println("-------------------------------------------------");

 for(int i=0; i<size; i++)
 {
  System.out.print("P"+(i+1)+":"+"\n"+"Arrival time = ");
  int Arrival = input.nextInt();
  System.out.print("Burst time = ");
  int Burst = input.nextInt();
  processes[i] = new process(i+1, Arrival, Burst); //add to the array 
  //eventQueue.add(new event(Arrival, "arrival", processes[i])); //add to the queue 
 }//endFor
 System.out.println("-------------------------------------------------");
 
 System.out.println("Scheduling Algorithm: Shortest remaining time first");
 System.out.print("Context	Switch: ");
 int SwitchTime=input.nextInt();
  System.out.println("-------------------------------------------------");
 
 System.out.println("Time      Process/CS");
 CPU(processes, SwitchTime);
 System.out.println("-------------------------------------------------");
 System.out.println("Performance Metrics");

 int totalTurnaround=0, totalWaiting =0;
 for(int i=0; i<processes.length; i++) {
 totalTurnaround += processes[i].turnaroundTime;
 totalWaiting += processes[i].waitingTime;
 }

 System.out.printf("Average Turnaround Time: %.2f ms\n", (double) totalTurnaround / processes.length);
System.out.printf("Average Waiting Time: %.2f ms\n", (double) totalWaiting / processes.length);
System.out.printf("CPU Utilization: %.2f%%\n", ((double) (Etime - (size*SwitchTime)) / Etime) * 100);
 

}//end the main

public static void CPU(process[] processes, int SwitchTime){
  int totalTurnaround = 0, totalWaiting = 0;
  int Stime=0;
  //int Etime=0;
  int completed=0;
  process current = processes[0];
  
  while(completed != processes.length ) {
      current.remainingTime--;
      Etime++;
  
      for(int i=0; i<processes.length; i++)
        if(!processes[i].isComplete() && Etime==processes[i].arrivalTime)
          if(current.remainingTime > processes[i].remainingTime) {
            System.out.println(Stime + "-" + Etime + "       P" + current.ID);
            current = processes[i];
            Stime = Etime ;
            Etime += SwitchTime;
            System.out.println(Stime + "-" + Etime + "       CS");
            totalContextSwitch+= SwitchTime;
            Stime = Etime;
            break;
            } 
        
        if (current.remainingTime == 0) {
          completed++;
          System.out.println(Stime + "-" + Etime + "       P" + current.ID);
          current.completionTime = Etime;
          current.turnaroundTime = current.completionTime - current.arrivalTime;
          current.waitingTime = current.turnaroundTime - current.burstTime;
          totalTurnaround += current.turnaroundTime;
          totalWaiting += current.waitingTime;
          if(completed != processes.length)
            current = processes[min(processes,Etime)];
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
  
  public static int min(process[] processes, int time) { //return the index for the smallest burst time
  int min = -1;
  for(int i=0; i<processes.length; i++) {
    if(processes[i].remainingTime != 0 && time >= processes[i].arrivalTime)
      if(min== -1 || processes[i].remainingTime < processes[min].remainingTime)
        min = i;
        else if(processes[i].remainingTime == processes[min].remainingTime)
          if(processes[i].arrivalTime < processes[min].arrivalTime)
              min = i;
  }    
  return min;
}


}//endclass
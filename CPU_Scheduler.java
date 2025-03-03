
import java.util.*;

public class CPU_Scheduler{

static int End_time =0;
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

 System.out.printf("Average Turnaround Time: %.1f\n", (double) totalTurnaround / processes.length);
System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaiting / processes.length);
System.out.printf("CPU Utilization: %.2f\n", ((double) (End_time - (totalContextSwitch)) / End_time) * 100);
 

}//end the main

public static void Scheduler(Process[] processes, int SwitchTime){
  int Start_time=0;
  int completed=0;
  Process current = processes[0]; // assume that the user will enter the process arrival time ascending
  Start_time = processes[0].arrivalTime;
  current.setStatus("running"); // the process have been edmitted then the scheduler dispatch the process
  End_time = Start_time; // in case if the arrival time for first process not 0
  
  while(completed != processes.length ) {

    current.remainingTime--; // decrement the process remaining time by one
    End_time++; // time will increase by one

      for(int i=0; i<processes.length; i++) { // to switch to the process with the min arrival time if exist
        if(!processes[i].status.equals("terminated") && End_time == processes[i].arrivalTime) {
          if(current.remainingTime > processes[i].remainingTime) { // switch if the current remaining time larger than the process in i
            System.out.println(Start_time + "-" + End_time + "       P" + current.ID);
            current.setStatus("ready"); // the switch make an interupt, so the current process will back to ready
            current = processes[i];
            current.setStatus("running"); // the new process will running 
            Start_time = End_time;
            End_time += SwitchTime;
            System.out.println(Start_time + "-" + End_time + "       CS");
            totalContextSwitch += SwitchTime;
            int index = SwitchToMin(processes, End_time); // check if there is an another process wil arrive in the time after adding the switch time
            if ( processes[index].ID != current.ID ) { // if  true, that mean there is min process than the current, then will switch
              System.out.println(End_time + "-" + End_time + "       P" + current.ID);
              current.setStatus("ready");
              current = processes[index];
              current.setStatus("running");
              Start_time = End_time ;
              End_time += SwitchTime;
              System.out.println(Start_time + "-" + End_time + "       CS");
              totalContextSwitch += SwitchTime;
            }
            Start_time = End_time;
            break;
          }
        }
      } 

    if (current.remainingTime == 0) {
      completed++;
      current.setStatus("terminated"); // the process exit
      System.out.println(Start_time + "-" + End_time + "       P" + current.ID);
      current.completionTime = End_time;
      current.turnaroundTime = current.completionTime - current.arrivalTime;
      current.waitingTime = current.turnaroundTime - current.burstTime;
      totalTurnaround += current.turnaroundTime;
      totalWaiting += current.waitingTime;

      if(completed != processes.length) { // handle errors if the current process is the last process in the array
        current = processes[SwitchToMin(processes, End_time)];
        current.setStatus("running");
        Start_time = End_time;
        End_time += SwitchTime;
        System.out.println(Start_time + "-" + End_time + "       CS");
        totalContextSwitch+= SwitchTime;
      }
      Start_time = End_time;
    }
  }
}
  
  public static int SwitchToMin(Process[] processes, int time) { //return the index for the smallest burst time
  int min = -1;
  for(int i=0; i<processes.length; i++) {
    if(!processes[i].status.equals("terminated") && time >= processes[i].arrivalTime)
      if(min == -1 || processes[i].remainingTime < processes[min].remainingTime)
        min = i;
        else if(processes[i].remainingTime == processes[min].remainingTime) // handle the case if the two process have the same burst time
          if(processes[i].arrivalTime < processes[min].arrivalTime)
              min = i;
  }    
  return min;
}


}//endclass
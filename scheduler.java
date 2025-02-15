import java.util.*;

public class scheduler{

static int size;

public static void main(String[]aa){
 Scanner input=new Scanner(System.in);
 
 System.out.print("Number	of	processes= ");
  size=input.nextInt();
 
 process processes[] = new process[size];
 
 System.out.println("Arrival	times	and	burst	times	as	follows: ");

 for(int i=0 ;i<size ;i++)
 {
 System.out.print("P"+(i+1)+"	Arrival	time	=	");
  int Arrival=input.nextInt();
 System.out.println(" , Burst	time	=");
  int Burst=input.nextInt();
  
  processes[i]=new process(i+1,Arrival,Burst);
   
 }//endFor
 
 System.out.println("Scheduling	Algorithm:	Shortest	remaining	time	first	");
  System.out.print("Context	Switch: ");
  int SwitchTime=input.nextInt();
  
  CPU(processes);
 
}//endm


public static void CPU( process[] processes){


int Stime=0;
int Etime=0;


 process x=processes[0];
 
 int com=size;
 while(com!=0){
 x.burstTime--;
 Etime++;
 
 
 for( int i=0 ; i<processes.length ;i++)
  if(! processes[i].isComplete() && Etime==processes[i].arrivalTime)
   if(x.burstTime>processes[i].burstTime){
     System.out.println(Stime+"_"+Etime+"      P"+processes[i].ID);
     x=processes[i];
     Stime=Etime;
     Etime++;
     System.out.println(Stime+"_"+Etime+"      CS");
     break;}
    
    
  
   if(x.burstTime==0){
    com--;
    System.out.println(Stime+"_"+Etime+"      P"+x.ID);
    
       x=processes[min(processes,Etime)];
       Stime=Etime;
        Etime++;
     System.out.println(Stime+"_"+Etime+"      CS");
}}
}
 
 
 
public static int min(process[] processes ,int time){


 int min=0;
 
 
 for( int i=1 ; i<processes.length ;i++)
  if(!processes[min].isComplete()&& time>=processes[min].arrivalTime && processes[i].burstTime<processes[min].burstTime )
    min=i;
    
  
return min;

}//endmin




 
  
  










}//endclsaa
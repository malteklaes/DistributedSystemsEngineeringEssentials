# Concurrency (as project from a university course (task 1))
## Task
Implement a multi-threaded Hamming number generation algorithm. Hamming numbers are numbers which follow 2^k*3^m*5^n, where k, m and n are non-negative integers. Example: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 16, 18, 20, 24, 25, 27, 30, 32, 36... While these numbers are quite common in the range from 1 to 60, their appearance becomes sparser the longer the series becomes. 

Use following basic design for your implementation. Don't be surprised by the "mult_by_n" (i.e., multiplication instead of exponentiation) some deviations from the mathematical formula were necessary to achieve the desired learning goal:

<img src="./c1_taskSolution.png" alt="BILD" style="width:200%; border:0; float:left, margin:5px" >

Each box represents a thread, and the lines between boxes are input and output queues respectively. Initially, all queues are empty. As soon as 1 is injected into copy's input queue the calculation starts. Further, mult_by_n multiplies its input queue by n individually and puts the result in its output queue. in_merge represents a distinct ordered merge operation. It waits for three distinct inputs being available, brings them in ascending order and then puts it in its output queue. copy replicates the data from its input queue into four output queues. Finally, print takes the data from its input queue and visualizes them using System.out.

All operations should block if the respective input queue runs out of data. Hence, for synchronisation purposes explore Java Features: such as, classic wait/notify/notifyAll, CountDownLatch, BlockingQueues, and the use of the ThreadLocal Type. Check out each of these, determine their differences, how and when they should be applied, and afterwards decide if and how they are applicable in the given task.

After finishing your implementation compare the generated output with the example output given at the top. Can you spot any differences? If yes, can you explain where those come from? Be aware that a correct implementation will always generate Hamming numbers. So if the spotted differences are that there are non Hamming numbers in your output than your implementation simply has a bug. Here we are interested in some other kind of difference like duplicates or unexpected orders. 
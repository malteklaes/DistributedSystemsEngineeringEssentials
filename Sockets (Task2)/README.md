# Sockets  (as project from a university course (task 2))
## Task



By tackling the following tasks you will gain experience in developing a simple distributed software. Perform the tasks step by step, as each one builds on the previous one. First, use TCP and subsequently UDP to tackle these tasks. While doing so compare the differences in performance and implementation and try to determine where these differences originate from (e.g., how the chosen protocol affects the performance and implementation). 

1) Create two programs, a client and its server. First, the client will send a single number to the server. This number will be sent back to the sending client by the server after incrementing it. The client then prints the received number to the console. As soon as this job was performed client and server terminate.

2) Extend the programs from before to exchange message objects instead of single numbers. A message object should contain the number, a sender name, such as, "client one" or "server”, and the timestamp in milliseconds when the message object was composed. Implement the functionality from 1) with this message object.

3) Extend the message object from before to contain a data blob with 1, 2, 4, 8, 16, and 128 KiB of dummy data (see, Arrays.fill()). Let the server return the message object to the client as soon as it has received it. Execute this step for 1000 iterations. Compute and output the average of the transmission times. What happens when you increase the dummy data amount step by step? Is it getting slower? Would you even need to adapt your implementation (this likely applies to one of the two socket types)? 

4) Implement multi-threading on the server and the client to handle multiple connections in parallel. Repeat task 3) with 1, 2, or 4 client threads that share the 1000 iterations between them (e.g., 2 threads with 500 iterations each). The server should use the same amount of threads as the client to process multiple incoming messages at the same time. Compare different implementation styles, e.g., creating new threads for each client vs. using ThreadPools.

<img src="./c2_schematerischer Ablauf.png" alt="BILD" style="width:200%; border:0; float:left, margin:5px" >
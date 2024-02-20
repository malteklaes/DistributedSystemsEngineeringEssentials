# Basic Remoting Patterns  (as project from a university course (task 4))
## Task


Patterns are a reusable solution to a commonly occurring problem within a given context. Likely you have already dealt with a number of patterns (e.g., Factory, Facade, Singleton, Strategy, etc.). Given that distributed systems struggle with their own unique set of problems they also were covered by a unique set of patterns.

One common issue for distributed systems is their transparent and flexible interconnection and data exchange. For this commonly a so-called middleware (a frequently discussed middleware standards you likely have already heard about is CORBA) is implemented and used. Here we will apply the knowledge gained throughout the previous lectures to create a minimal middleware by implementing a selected set of remoting patterns. 

For simplicity reasons, and as you should already be aware of RPC, we recommend to design a simple RPC like middleware. It should support the following use cases: a) support calling at least two different methods b) each method should have exactly one argument and send back a result. 

1) method "hello": Should take a name as an argument and construct a greeting message from the name. For example, given the name "DSE" the text "Hello, DSE!" should be generated and returned. 

2) method "goodbye": Should take a name as an argument and construct a farewell statement from it. For example, given the name "DSE" the text "DSE, I'm afraid this is a farewell." should be generated and returned. 

The logic for these functions should be implemented solely on the server. 

Given that interface descriptions and their exchange, definition, and parsing (e.g., check out SOAP's WSDL) can be quite complex, we will ignore this aspect for this exercise. Instead simply assume that the client is aware of the name of the methods and their arguments and can exploit this in its requestor and client request handler. Additional simplifications: a) we will, for now, not take concurrency into account at the server or client b) you can, for now, assume that only a single object exists on the server.

1) Create a minimal protocol: The first step is to define a minimal protocol (i.e., how the exchanged message/data is defined and used). This enables your client and the server to talk to each other as both speak the same "language". Take the use cases in mind while doing so, i.e., you will need to represent a method name and the parameters/returned values. 

2) Implement a Marshaller: Do not use the marshallers built into Java but create your own. A simple approach would be to create a string representation of the exchanged data and convert it into a byte array (for the server, the same steps are executed in reverse). Java provides the necessary functionality for this out of the box (see String's getBytes()). Don't forget to specify the character set for this operation if you want to mix different systems. Different elements with varying length in their string representation can be separated by special characters (delimiters) to separate them correctly at the receiving end. 

With regards to the latter the question arises how one can handle a situation in which the delimiter occurs already in the data that should be exchanged? For example you want to send "D;S;E" but selected ";" already as your delimiter. Counter this based on your exchange format ("bring up" a format that does not mix delimiters and the to be exchanged data) or apply so called escaping. 

3) Implement a Client Request Handler: The client request handler should initiate a simple socket to exchange binary data with the server request handler. Only use the basic input and output streams to transfer raw bytes (i.e., do not use an ObjectOutputStream). 

4) Implement Client Proxy & Requestor: Finally, finish the client by implementing the proxy and requestor. The latter interacts with the marshaller functionality.

5) Implement Server Request Handler and Invoker: Implement the server side of the project. The request handler will likely contain the server socket and should receive and send raw bytes. Forward these to the invoker to cover marshalling and forwarding to the correct remote object method.

6) Implement the Signaling of Errors and States: Extend the existing functionality (likely you will need to touch most of the existing code to a small extend) to exchange and use also error states. Add at least the functionality required to signal that the client has requested to call a method which does not exist on the server’s remote object. Simple error codes can be a sufficient starting point for this task. Your client should be made aware of such an error using exceptions.

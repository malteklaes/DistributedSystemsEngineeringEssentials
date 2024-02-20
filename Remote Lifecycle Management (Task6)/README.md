# Remote Lifecycle Management  (as project from a university course (task 6))


> [0] solution based on **gRPC** and **ROT13-Encryption**

> [1] gRPC
- please before running server and client, run `chatService.proto` beforehand in order to get both automated generated classes:
    - `io.grpc.chatService.ChatServiceGrpc.class`
    - `io.grpc.chatService.ChatServiceOuterClass.class`
- reminder: also please update Gradle beforehand, to properly build the proto-file with protoc


> [2] strategy/pattern-toggle
- In order to switch between both strategies **STATIC_INSTANCE** and **PER_REQUEST_INSTANCE_AND_POOLING**, please use the EPatternStrateg-toggle in `util.InteractionUtilService.class`


> Author:
Malte Klaes
01650623
December 2023

## Hint
unzip folders "build" and "lib"

## Task
Implement a simple Cipher service for e.g. a secure chat software. Use the Java javax.crypto.cipher library, which is described in Java API and a tutorial
Alternatively you can use a simpler Cipher, e.g. the ROT13 symmetric substitution cipher described here.

First, realize the Cipher service as a Static Instance which is able to encrypt and decrypt simple text messages. Considering that initializing a Cipher instance is an expensive operation and takes long time to start up and assuming it is used frequently as all chat messages shall be encrypted, apply the combination of Per-Request Instance and Pooling as an alternative implementation.
A test client shall request an instance for each message processed by the Cipher service. It shall test both implementations, the one based on a Static Instance and the one based on Per-Request Instance and Pooling.

Bonus: Measure execution time, resource consumption and compare performance characteristics of the two lifecycle management pattern implementations.

We suggest to use a Java-based remote object middleware to solve this task, such as Java gRPC, Apache CXF, or Java RSocket. You can use a Java library that realizes the Pooling pattern or implement your own Pooling solution.

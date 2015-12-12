#commons-j2ee-tools

Tools for Java EE

##Features

- [X] BTM XATerminator
- [X] Java EE Connector Architecture
- [X] Integration with genonimo Work Manager
- [X] Integration with geronimo Transaction manager
- [X] JCA Inbound
- [X] JCA Outbound
- [X] Spring integration 


## How to use JCA Outbound

1. Define your interface with the operations extending IManagedDriver
2. Define your interface of ConnectionFactory extending IConnectionFactory<Your Driver>
3. Define your implementation of ConectionFactory extending ConnectionFactorySPI<Your Driver> and implements "your ConectionFactory" interface.
4. Implements your ConnectionRequestInfo extending ConnectionRequestInfoSPI
5. Implements (if you need) the Your ManagedConnectionMetadata extending ManagedConnectionMetadataSPI
6. Implements your ManagedConnection extending ManagedConnectionSPI <your driver, your connection request info, your ManagedConnection Metadata>
 and implements your Driver
7. Implements your ManagedConnectionFactory extending ManagedConnectionFactorySPI<Your driver, Your connection request info, your Managed connection metaData>
		

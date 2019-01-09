# Digital Locker

## Introduction
Digital Locker provides RESTful API to store and share any file on a blockchain network. The applications of such a system includes any use case that requires user privacy, authenticity and immutability. One such use case is health record storage.

Following are the features that Digital Locker provides as a part of its offering:

* **Security**: Files are stored in an encrypted format so the control of who is able to view the data completely lies with the user. Even if the client interacts with the blockchain through a middleware (like an application server), the client can directly manage his/her private keys (thereby not compromising any security)
* **Immutability**: Since the storage is on a blockchain network so the authenticity and immutability of the data is guaranteed. Once the data is stored, its origin and state is unchangable and can be easily traced down.
* **Interoperability**: Based on the permissioning implemented in the blockchain network, it is very easy for new entities to join the network and access the already existing data without any complex setup process.
* **Easy Integration**: Since Docs provides RESTful API, so it is very easy to integrate with existing backend systems and leverage the power of blockchain. Besides, these APIs also make it possible for mobile devices to directly connect to the blockchain network without any knowledge of its underlying working.


## Technical Implementation Details

### Blockchain
Digital Locker leverages Multichain as the underlying blockchain technology.

### Cryptography
* Digital Locker uses "RSA public key - private key" cryptography to manage user accounts as well as for user signature and validation.
* Digital Locker uses "AES/CBC/PKCS5PADDING symmetric key" cryptography to encrypt the files stored on the blockchain.

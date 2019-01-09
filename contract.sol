pragma solidity ^0.4.24;
//pragma experimental ABIEncoderV2;
contract test {
    
   struct document{
       string ipfsHash;
       string documentName;
       bytes32 accessKey;
   }
   
    struct grantAccess{
       address user;
    }
    
    struct clientDocument{
        string ipfsHash;
        string documentName;
        Permission permission;
    }
    
    struct sharedDoc{
        address owner;
        string ipfsHash;
        string documentName;
    }
   
   enum Permission {READ, MODIFY}
   
   mapping (bytes32 => sharedDoc)  docInfoByHash;
   mapping (bytes32 => grantAccess[]) public getClientAddressOfSharedDocUsingHash;
   mapping (address => document[])  getOwnerDocumentsByDocId;
   mapping (address => clientDocument[])  getclientDocumentDetailsByDocId;
   mapping (address => uint256)  totalDocumentsUploadedByOwner;
   mapping (address => uint256)  totalDocGrantedtoUser;
   
 
   function addDocuments( string  _ipfsHash, string  _documentName) public {
       bytes32   _accessKey = sha256(bytes(_ipfsHash));
        
        var docByHash = docInfoByHash[_accessKey];
        docByHash.owner = msg.sender;
        docByHash.ipfsHash=_ipfsHash;
        docByHash.documentName =_documentName;
        
        uint256 newMaxPositionForAddress = getOwnerDocumentsByDocId[msg.sender].push(document(
            {
                ipfsHash:_ipfsHash,
                documentName:_documentName,
                accessKey:_accessKey
            }));
        totalDocumentsUploadedByOwner[msg.sender] = newMaxPositionForAddress;
      var docAccess =  getClientAddressOfSharedDocUsingHash[_accessKey];
       
   }

   function grantDocumentAccessToClient(address _userAddress, bytes32 _accessKey, Permission _permission) public {
        getClientAddressOfSharedDocUsingHash[_accessKey].push(grantAccess(
        _userAddress));
          var docByHash = docInfoByHash[_accessKey];
 
        uint256 newMaxPositionForAddress2 = getclientDocumentDetailsByDocId[_userAddress].push(clientDocument({
                    ipfsHash:docByHash.ipfsHash,
                    documentName:docByHash.documentName,
                    permission:_permission
        }
        ));
        totalDocGrantedtoUser[_userAddress] = newMaxPositionForAddress2;
   }
   
    function getTotalDocsUploadsByOwner()public view returns (uint256){
       return totalDocumentsUploadedByOwner[msg.sender];
    }
    
    function getTotalSharedDocsByOthers () public view returns (uint256){
        return totalDocGrantedtoUser[msg.sender];
    }

    
    function getOwnerDocInfoByDocId( uint256 _docId )public view returns (string,string,bytes32){
       var data =   getOwnerDocumentsByDocId[msg.sender][_docId];
       string memory ipfsHash = data.ipfsHash;
       string memory documentName = data.documentName;
       bytes32 accessKey = data.accessKey;
       return(ipfsHash, documentName, accessKey);
    }
    
    function getSharedDocByDocId( uint256 _docId )public view returns (string, string, Permission){
       var data =   getclientDocumentDetailsByDocId[msg.sender][_docId];
       string memory ipfsHash = data.ipfsHash;
       string memory documentName = data.documentName;
       Permission permission =data.permission;
       return(ipfsHash, documentName, permission);
    }
    
    function getTotalHashKeyShared(bytes32 _hash)public view returns (uint256){
        return getClientAddressOfSharedDocUsingHash[_hash].length;
    } 

}
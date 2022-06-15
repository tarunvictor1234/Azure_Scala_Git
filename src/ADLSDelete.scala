import com.azure.storage.blob.BlobServiceClientBuilder

object ADLSDelete {
  
  val sasToken = "sv=";
   val token = "https://" ;
 


   def main(args: Array[String]) {
  
   
   val blobServiceClient = new BlobServiceClientBuilder().endpoint(token).buildClient(); 
   
   val containerClient = blobServiceClient.getBlobContainerClient("tarun23")
   val blobClient = containerClient.getBlobClient("testing.txt")
   blobClient.deleteIfExists()
   println("done...")
  }
   
   
   //TODO to be tested
   def deleteasync(){
     
   val blobServiceClient = new BlobServiceClientBuilder().endpoint(token).buildAsyncClient(); 
   val containerClient = blobServiceClient.getBlobContainerAsyncClient("tarun23")
   val blobClient = containerClient.getBlobAsyncClient("testing.txt")
   blobClient.deleteIfExists();
   println("done...")
   
   
   } 
}



import com.azure.storage.blob.BlobServiceClientBuilder

object ADLSCopy {
   def main(args: Array[String]) {
   println("The empty list is: ") 
   val sasToken = "sv...";
   val token = "https://tstorage2345.blob.core.windows.net/?sv...." ;
   val blobServiceClient = new BlobServiceClientBuilder().endpoint(token).buildClient(); 
   
   val containerClient = blobServiceClient.getBlobContainerClient("tarun23")
   val blobClient = containerClient.getBlobClient("testing.txt")
   val newBlobClient = containerClient.getBlobClient("testingycx.txt")
   newBlobClient.copyFromUrl(blobClient.getBlobUrl() + "?" + sasToken);
 
  }
}
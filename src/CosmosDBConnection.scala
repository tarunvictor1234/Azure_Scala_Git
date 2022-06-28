import com.azure.cosmos.CosmosClientBuilder
import com.azure.cosmos.ConsistencyLevel
import com.azure.cosmos.util.CosmosPagedIterable
import com.azure.cosmos.models.CosmosQueryRequestOptions
import java.util.Iterator
import com.fasterxml.jackson.databind.JsonNode


class CosmosDBConnection {
  
  val cosmoCient  = new CosmosClientBuilder()
  .endpoint("https://localhost:8081").key("")
  .consistencyLevel(ConsistencyLevel.EVENTUAL)
  .contentResponseOnWriteEnabled(true).buildClient()
  
  
  def getRecord():Iterator[tcontainermodel]={
    val database = cosmoCient.getDatabase("tdatabase")
    val container = database.getContainer("tcontainer")
   
    val sql = "select * from c "
    val filter: CosmosPagedIterable[tcontainermodel] = 
      container.queryItems(sql, new CosmosQueryRequestOptions, classOf[tcontainermodel]) 
      val iterator = filter.iterator()
      iterator
  }
  
   def getRecordDetails():Iterator[JsonNode]={
    val database = cosmoCient.getDatabase("tdatabase")
    val container = database.getContainer("tcontainer")
   
    val sql = "select * from c "
    val filter: CosmosPagedIterable[JsonNode] = 
      container.queryItems(sql, new CosmosQueryRequestOptions, classOf[JsonNode]) 
      val iterator = filter.iterator()
      iterator
  }
}
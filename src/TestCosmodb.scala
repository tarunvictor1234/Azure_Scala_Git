


import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.JsonNode

object Test {

  def main(args: Array[String]) {

    val mapper = JsonMapper.builder()
      .addModule(DefaultScalaModule)
      .build()

    val dbconnection = new CosmosDBConnection();
    val it = dbconnection.getRecordDetails()
    println(it);
   while (it.hasNext){
         //println(it.next())
         //val jsonstring = it.next().toString()
         //println(">>"+jsonstring)
          val cm = mapper.readValue(it.next().toString(), classOf[tcontainermodel])
          println(cm)
      }
    
    

    /*
    
    case class Person(name: String, age: Int)
    val person = Person("fred", 25)
    val out = new StringWriter
    mapper.writeValue(out, person)
    val json = out.toString()
    val person2 = mapper.readValue(json, classOf[Person])
    println(person2)
    
    */

    // val instance1 = mapper.readValue(it, classOf[tcontainermodel])
    // val newText = mapper.writeValueAsString(instance1)

    /* while (it.hasNext){
         println(it.next())
         mapper.readValue(it, classOf[tcontainermodel])
      }
   mapper.readValue(it, classOf[tcontainermodel])*/
    
    
    System.exit(0)

  }
}
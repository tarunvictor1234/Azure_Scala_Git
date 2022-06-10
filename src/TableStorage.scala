

import com.azure.core.credential.AzureSasCredential
import com.azure.data.tables.TableClientBuilder
import com.azure.data.tables.models.TableEntity
import com.azure.data.tables.models.TableEntity
import com.azure.data.tables.models.ListEntitiesOptions

object TableStorage {
  def main(args: Array[String]) {

    var sasToken = "sv";
    var token = "https://";

    val credential = new AzureSasCredential(sasToken);

    val tableClient = new TableClientBuilder().credential(credential)
      .endpoint("https://tstorage2345.table.core.windows.net/")
      .tableName("Employees").buildClient();

    val options = new ListEntitiesOptions()
    //.setFilter("");

    tableClient.listEntities(options, null, null).forEach(tableEntity => {
      System.out.println(tableEntity.getProperty("email"));
    });

  }
}
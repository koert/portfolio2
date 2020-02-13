package zencode.portfolio.stock.portfoliodb

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.model.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

/**
 * Repository for portfolio positions.
 * @author Koert Zeilstra
 *
 * https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.01.html
 *
 * java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
 */
@ApplicationScoped
class PortfolioRepository {

  val dynamoClient: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
     .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-1"))
  .build();

  @PostConstruct
  fun initialize() {
    val dynamoDB = DynamoDB(dynamoClient);
    val existingTable : Table? = dynamoDB.getTable("stockposition");
    if (existingTable == null) {
      val schema: List<KeySchemaElement> = listOf(
         KeySchemaElement("userId", KeyType.HASH),
         KeySchemaElement("symbol", KeyType.RANGE));
//       KeySchemaElement("name", KeyType.RANGE),
//       KeySchemaElement("amount", KeyType.RANGE),
//       KeySchemaElement("buyPrice", KeyType.RANGE),
//       KeySchemaElement("buyDate", KeyType.RANGE),
//       KeySchemaElement("latestPrice", KeyType.RANGE),
//       KeySchemaElement("latestDate", KeyType.RANGE));
      val attributes = listOf(
         AttributeDefinition("userId", ScalarAttributeType.N),
         AttributeDefinition("symbol", ScalarAttributeType.S));
//       AttributeDefinition("name", ScalarAttributeType.S),
//       AttributeDefinition("amount", ScalarAttributeType.N),
//       AttributeDefinition("buyPrice", ScalarAttributeType.S),
//       AttributeDefinition("buyDate", ScalarAttributeType.S),
//       AttributeDefinition("latestPrice", ScalarAttributeType.S),
//       AttributeDefinition("latestDate", ScalarAttributeType.S));
      val table = dynamoDB.createTable("stockposition", schema, attributes,
         ProvisionedThroughput(10, 10));
      table.waitForActive();
      println("Success.  Table status: " + table.getDescription().getTableStatus());
    } else {
      println("Existing table: " + existingTable.toString());
    }
  }

  fun getPositions(userId: Long): List<StockPosition> {
    val dynamoDB = DynamoDB(dynamoClient);
    val table = dynamoDB.getTable("stockposition");
    val nameMap = mutableMapOf<String, String>("#userId" to "userId");
    val valueMap = mutableMapOf<String, Long>(":id" to userId);
    val querySpec = QuerySpec().withKeyConditionExpression("#userId = :id")
       .withNameMap(nameMap).withValueMap(valueMap as Map<String, Any>?);
    val items = table.query(querySpec);
    val dateFormat = SimpleDateFormat("yyyy-MM-dd");
    val positions = items.map { item ->
      StockPosition(item.getLong("userId"), item.getString("symbol"),
         item.getInt("amount"),
         BigDecimal(item.getString("buyPrice")), dateFormat.parse(item.getString("buyDate")),
         BigDecimal(item.getString("latestPrice")), dateFormat.parse(item.getString("latestDate")))
    };
    return positions;
  }

  fun savePosition(userId: Long, position: StockPosition) {
    println("savePosition: " + userId + " " + position.symbol);
    val dynamoDB = DynamoDB(dynamoClient);
    val table = dynamoDB.getTable("stockposition");
    val dateFormat = SimpleDateFormat("yyyy-MM-dd");
//    val valueMap = mapOf(
//       "amount" to position.amount,
//       "buyPrice" to position.buyPrice.toString(),
//       "buyDate" to dateFormat.format(position.buyDate),
//       "latestPrice" to position.latestPrice.toString(),
//       "latestDate" to dateFormat.format(position.latestDate)
//    );
    val item = Item().withPrimaryKey("userId", userId, "symbol", position.symbol)
       .with("amount", position.amount)
       .with("buyPrice", position.buyPrice.toString())
       .with("buyDate", dateFormat.format(position.buyDate))
       .with("latestPrice", position.latestPrice.toString())
       .with("latestDate", dateFormat.format(position.latestDate));
    table.putItem(item);
  }

  fun savePositions(userId: Long, positions: List<StockPosition>) {
    for (position in positions) {
      savePosition(userId, position);
    }
  }
}
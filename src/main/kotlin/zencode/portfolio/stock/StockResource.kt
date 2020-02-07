package zencode.portfolio.stock

import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.io.IOException
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Endpoint for stock data.
 * @author Koert Zeilstra
 */
@Path("/stocks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class StockResource {
  /**
   * Retrieve latest stock price (from Yahoo Finance).
   * @param symbol Stock symbol.
   * @return Response with StockLatestPriceResponse.
   * @throws IOException Failed to retrieve price.
   */
  @Path("/{symbol}/latestPrice")
  @GET
  @Throws(IOException::class)
  fun stock(@PathParam("symbol") symbol: String): Response {
    val stock: Stock? = YahooFinance.get(symbol)
    val response: Response = if (stock == null) {
      Response.status(Response.Status.NOT_FOUND).build()
    } else {
      val stockResponse = StockLatestPriceResponse(stock.symbol, stock.name, stock.quote.price)
      Response.ok(stockResponse).build()
    }
    return response
  }
}

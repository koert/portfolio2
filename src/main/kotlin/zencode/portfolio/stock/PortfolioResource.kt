package zencode.portfolio.stock

import zencode.portfolio.stock.portfoliodb.PortfolioRepository
import java.io.IOException
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Endpoint for stock data.
 * @author Koert Zeilstra
 */
@Path("/portfolios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PortfolioResource {

  @Inject
  @field: Default
  lateinit var portfolioRepository: PortfolioRepository;

  /**
   * Retrieve latest stock price (from Yahoo Finance).
   * @param symbol Stock symbol.
   * @return Response with StockLatestPriceResponse.
   * @throws IOException Failed to retrieve price.
   */
  @Path("/positions")
  @GET
  @Throws(IOException::class)
  fun portfolio(): Response {
    val positions = portfolioRepository.getPositions(1);
    val portfolioResponse = PortfolioResponse(positions);
    return Response.ok(portfolioResponse).build()
  }

  /**
   * Retrieve latest stock price (from Yahoo Finance).
   * @param symbol Stock symbol.
   * @return Response with StockLatestPriceResponse.
   * @throws IOException Failed to retrieve price.
   */
  @Path("/positions")
  @PUT
  @Throws(IOException::class)
  fun savePortfolio(portfolio: PortfolioResponse): Response {
    portfolioRepository.savePositions(1, portfolio.positions);
    return Response.ok().build()
  }
}

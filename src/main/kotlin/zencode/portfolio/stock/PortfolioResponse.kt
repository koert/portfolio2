package zencode.portfolio.stock

import io.quarkus.runtime.annotations.RegisterForReflection
import zencode.portfolio.stock.portfoliodb.StockPosition

/**
 * Response for stock latest price service.
 * @author Koert Zeilstra
 */
@RegisterForReflection
data class PortfolioResponse(var positions: List<StockPosition>? = null) {
//  override fun toString(): String {
//    return "PortfolioResponse(positions=$positions)"
//  }

}
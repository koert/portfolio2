package zencode.portfolio.stock

import io.quarkus.runtime.annotations.RegisterForReflection
import zencode.portfolio.stock.portfoliodb.StockPosition

/**
 * Response for stock latest price service.
 * @author Koert Zeilstra
 */
@RegisterForReflection
class PortfolioResponse(val positions: List<StockPosition>) {
  constructor() : this(listOf()) {}
}
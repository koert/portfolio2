package zencode.portfolio.stock

import io.quarkus.runtime.annotations.RegisterForReflection
import java.math.BigDecimal

/**
 * Response for stock latest price service.
 * @author Koert Zeilstra
 */
@RegisterForReflection
class StockLatestPriceResponse(val symbol: String, val name: String, val latestPrice: BigDecimal) {
}
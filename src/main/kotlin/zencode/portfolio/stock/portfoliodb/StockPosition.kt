package zencode.portfolio.stock.portfoliodb

import io.quarkus.runtime.annotations.RegisterForReflection
import java.math.BigDecimal
import java.util.*

@RegisterForReflection
class StockPosition(val userId: Long, val symbol: String, val amount: Int, val buyPrice: BigDecimal, val buyDate: Date,
                    val latestPrice: BigDecimal, val latestDate: Date) {
  constructor() : this(1, "", 0, BigDecimal.ZERO, Date(), BigDecimal.ZERO, Date()) {}
}

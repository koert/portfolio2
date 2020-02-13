package zencode.portfolio.stock.portfoliodb

import io.quarkus.runtime.annotations.RegisterForReflection
import java.math.BigDecimal
import java.util.*

@RegisterForReflection
data class StockPosition(var userId: Long? = null, var symbol: String? = null, var amount: Int? = null, var buyPrice: BigDecimal? = null, var buyDate: Date? = null,
                    var latestPrice: BigDecimal? = null, var latestDate: Date? = null) {
//  constructor() : this(1, "", 0, BigDecimal.ZERO, Date(), BigDecimal.ZERO, Date()) {}
//
//  override fun toString(): String {
//    return "StockPosition(userId=$userId, symbol='$symbol', amount=$amount, buyPrice=$buyPrice, buyDate=$buyDate, latestPrice=$latestPrice, latestDate=$latestDate)"
//  }


}

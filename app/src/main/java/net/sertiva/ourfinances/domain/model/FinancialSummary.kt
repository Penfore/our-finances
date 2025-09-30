package net.sertiva.ourfinances.domain.model

data class FinancialSummary(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val totalBalance: Double = totalIncome - totalExpense,
    val transactionCount: Int = 0
) {
    val netBalance: Double
        get() = totalIncome - totalExpense

    val isPositiveBalance: Boolean
        get() = netBalance >= 0
}

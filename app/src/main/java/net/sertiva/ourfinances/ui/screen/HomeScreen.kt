package net.sertiva.ourfinances.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.sertiva.ourfinances.domain.model.FinancialSummary
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import net.sertiva.ourfinances.ui.theme.ExpenseRed
import net.sertiva.ourfinances.ui.theme.IncomeGreen
import net.sertiva.ourfinances.ui.theme.OurFinancesTheme
import net.sertiva.ourfinances.ui.viewmodel.HomeViewModel
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddTransactionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    uiState.error?.let { error ->
        LaunchedEffect(error) {
            // Here you can show a SnackBar or other error component
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Finances") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransactionClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            FinancialSummaryCard(
                summary = uiState.financialSummary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                TransactionsList(
                    transactions = uiState.transactions,
                    onDeleteTransaction = { viewModel.deleteTransaction(it.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun FinancialSummaryCard(
    summary: FinancialSummary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = summary.totalBalance.formatCurrency(),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = if (summary.totalBalance >= 0) IncomeGreen else ExpenseRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = summary.totalIncome.formatCurrency(),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = IncomeGreen
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Expenses",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = summary.totalExpense.formatCurrency(),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = ExpenseRed
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionsList(
    transactions: List<Transaction>,
    onDeleteTransaction: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No transactions found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onDeleteClick = { onDeleteTransaction(transaction) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = transaction.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = transaction.formattedAmount.formatCurrency(),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (transaction.type == TransactionType.INCOME) IncomeGreen else ExpenseRed
                )

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete transaction",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private fun Double.formatCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    OurFinancesTheme {
        // Preview with mock data - doesn't include real ViewModel
    }
}
